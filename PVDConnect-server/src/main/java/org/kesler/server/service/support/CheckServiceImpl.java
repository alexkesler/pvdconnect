package org.kesler.server.service.support;

import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Check;
import org.kesler.server.domain.Record;
import org.kesler.server.repository.BranchRepository;
import org.kesler.server.repository.CheckRepository;
import org.kesler.server.repository.RecordRepository;
import org.kesler.server.service.CheckService;
import org.kesler.server.util.OracleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CheckServiceImpl implements CheckService {

    private static final Logger log = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private RecordRepository recordRepository;

    private String message = "Готово";

    private long total;
    private long current;

    @Override
    public void doCheck(){
        log.info("Begin checks");
        Collection<Branch> branches = branchRepository.getAllBranches();
        for(Branch branch:branches) {
            log.info("Checking branch: " + branch.getName());
            Check check = findCheckForBranch(branch);


            if (check==null) check = new Check(branch);
            check.setLastSuccess(false);
            checkRepository.saveCheck(check);

            try {
                recheck(check);
                check.setLastSuccess(true);
                checkRepository.saveCheck(check);

            } catch (SQLException sqle) {
                log.error("Error connecting PVD server: " + branch.getPvdIp() + " - " + sqle, sqle);

                message="Ошибка получения данных";
            }


        }
        log.info("Checks complete");
        message="Готово";

    }

    private void recheck(Check check) throws SQLException{
        Branch branch = check.getBranch();
        String andDateSqlString = "";
        if (check.getCheckDate()!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
            andDateSqlString= " and RBI.REGDATE >= '" +  dateFormat.format(check.getCheckDate()) + "'";
        }
        String countQuery = "SELECT count(RBI.ID_CAUSE) AS cnt " +
                "from DPS$RECBOOKITEM RBI " +
                "where RBI.FROMCAUSE=1" + andDateSqlString;

        String query = "SELECT RBI.ID_CAUSE, RBI.REGNUM, RBI.REGDATE, C.PREVCAUSE_NUM " +
                "from DPS$RECBOOKITEM RBI " +
                "join DPS$D_CAUSE C on C.ID = RBI.ID_CAUSE " +
                "where RBI.FROMCAUSE=1" + andDateSqlString;

        log.debug("Connecting branch: " + branch.getName());

        Connection conn = null;
        try {
            message="Подсоединяюсь..";
            conn = OracleUtil.createConnection(branch.getPvdIp(), branch.getPvdUser(), branch.getPvdPassword());
        } catch (ClassNotFoundException e) {
            log.error("Oracle class not registered - " + e, e);
            throw new RuntimeException(e);
        }

        Collection<Record> records = new ArrayList<>();

        Statement stmt = conn.createStatement();
        try {
            log.debug("Reading count");
            message = "Считаем кол-во работы по филиалу "+ branch.getName();
            try (ResultSet rs = stmt.executeQuery(countQuery)) {
                rs.next();
                total = rs.getLong("cnt");
                log.debug("In rs " + total + " records");

            }
        } finally {
            stmt.close();
        }

        stmt = conn.createStatement();
        try {
            message = "Получаем данные по филиалу " + branch.getName();
            try (ResultSet rs = stmt.executeQuery(query)) {
                log.debug("Processing >>>");
                processRs(rs, check, records);
                log.debug("Processing complete");
                message = "Готово";
            }
        } finally {
            stmt.close();
        }

        OracleUtil.closeConnection(conn);
        log.info("Check branch " + branch.getName() + " complete: " + check.getRecordsSize() + " cases.");

        message="Записываем в базу данных";
        log.info("Saving records to DB ...");
        recordRepository.saveRecords(records);
        log.info("Saving records to DB complete.");
        log.info("Counting total records..");
        check.setRecordsSize(recordRepository.getRecordsCount(branch));
        log.info("In check " + check.getRecordsSize() + " records");
        message="Готово";

        current = 0;
        total = 0;

    }

    private void processRs(ResultSet rs, Check check, Collection<Record> records) throws SQLException {
        if (rs==null) return;
        current = 0;
        while (rs.next()) {

            Record record = new Record();
            record.setBranch(check.getBranch());
            record.setCheck(check);
            record.setCausePvdId(rs.getString("ID_CAUSE"));
            record.setRegnum(rs.getString("REGNUM"));
            record.setRegdate(rs.getDate("REGDATE"));
            record.setPrevRegnum(rs.getString("PREVCAUSE_NUM"));

            records.add(record);
            current++;

        }
        check.setRecordsSize(check.getRecordsSize()+records.size());
        check.setCheckDate(new Date());
    }

    private Check findCheckForBranch(Branch branch) {

        return checkRepository.getCheckByBranch(branch);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public long getCurrent() {
        return current;
    }
}
