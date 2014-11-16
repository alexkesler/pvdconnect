package org.kesler.server.service.support;

import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Check;
import org.kesler.server.domain.Record;
import org.kesler.server.repository.BranchRepository;
import org.kesler.server.repository.CheckRepository;
import org.kesler.server.service.CheckService;
import org.kesler.server.util.OracleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CheckServiceImpl implements CheckService {

    private static final Logger log = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CheckRepository checkRepository;

    private String message = "Готово";

    private long total;
    private long current;

    @Override
    public void doCheck(){
        log.info("Begin checks");
        for(Branch branch:branchRepository.getAllBranches()) {
            log.info("Checking branch: " + branch.getName());
            try {
                checkBranch(branch);
            } catch (SQLException sqle) {
                log.error("Error connecting PVD server: " + branch.getPvdIp() + " - " + sqle, sqle);
            }

        }
        log.info("Checks complete");
    }

    private void checkBranch(Branch branch) throws SQLException{
        if (branch==null) return;
        Check check = findCheckForBranch(branch);
        if (check==null) check = new Check(branch);
        String andDateSqlString = "";
        if (check.getCheckDate()!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
            andDateSqlString= " and RBI.REGDATE >= '" +  dateFormat.format(check.getCheckDate()) + "'";
        }
        String countQuery = "SELECT count(RBI.ID_CAUSE) AS cnt " +
                "from DPS$RECBOOKITEM RBI " +
                "where RBI.FROMCAUSE=1" + andDateSqlString;

        String query = "SELECT RBI.ID_CAUSE, RBI.REGNUM, RBI.REGDATE " +
                "from DPS$RECBOOKITEM RBI " +
                "where RBI.FROMCAUSE=1" + andDateSqlString;

        log.debug("Connecting branch: " + branch.getName());

        Connection conn = null;
        try {
            conn = OracleUtil.createConnection(branch.getPvdIp(), branch.getPvdUser(), branch.getPvdPassword());
        } catch (ClassNotFoundException e) {
            log.error("Oracle class not registered - " + e, e);
            throw new RuntimeException(e);
        }


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
                processRs(rs, check);
                log.debug("Processing complete");
                message = "Готово";
            }
        } finally {
            stmt.close();
        }

        OracleUtil.closeConnection(conn);
        log.info("Check branch " + branch.getName() + " complete: " + check.getRecords().size() + " cases.");
        checkRepository.addCheck(check);

        current = 0;
        total = 0;

    }

    private void processRs(ResultSet rs, Check check) throws SQLException {
        if (rs==null) return;
        current = 0;
        while (rs.next()) {

            Record record = new Record();
            record.setBranch(check.getBranch());
            record.setCausePvdId(rs.getString("ID_CAUSE"));
            record.setRegnum(rs.getString("REGNUM"));
            record.setRegdate(rs.getDate("REGDATE"));

            check.addRecord(record);
            current++;
        }
        check.setCheckDate(new Date());
    }

    private Check findCheckForBranch(Branch branch) {
        for (Check check:checkRepository.getAllChecks()) {
            if(branch.equals(check.getBranch())) return check;
        }
        return null;
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
