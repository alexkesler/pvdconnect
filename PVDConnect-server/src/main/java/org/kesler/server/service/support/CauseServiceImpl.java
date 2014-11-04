package org.kesler.server.service.support;

import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Cause;
import org.kesler.server.domain.Record;
import org.kesler.server.domain.cause.Obj;
import org.kesler.server.domain.cause.Step;
import org.kesler.server.service.CauseService;
import org.kesler.server.util.OracleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CauseServiceImpl implements CauseService {
    private static final Logger log = LoggerFactory.getLogger(CauseServiceImpl.class);

    @Override
    public Cause getCauseByRecord(Record record){

        Cause cause = new Cause();
        cause.setId(record.getCauseId());
        cause.setRecord(record);

        String objQuery = "SELECT O.FULLADDRESS " +
                "FROM DPS$D_CAUSE C " +
                "LEFT JOIN DPS$OBJ O ON O.ID_CAUSE=C.ID " +
                "WHERE C.ID='" + record.getCauseId() + "'";


        String applicatorsQuery = "";

        String stepsQuery = "SELECT S.RESOLUTION, S.ID_OPERATION, S.DATEBEGIN, S.DATEEND, S.STATE " +
                "FROM DPS$STEP S " +
                "WHERE S.ID_CAUSE='" + record.getCauseId() + "' " +
                "ORDER BY S.DATEBEGIN";

        Branch branch = record.getBranch();

        log.info("Reading cause from server " + branch.getPvdIp());

        log.debug("Connecting >>>");
        Connection conn = null;
        try {
            conn = OracleUtil.createConnection(branch.getPvdIp(), branch.getPvdUser(), branch.getPvdPassword());
        } catch (SQLException e) {
            log.error("Error connecting server " + branch.getPvdIp() + ": " + e, e);
            throw new RuntimeException("Error connecting PVD server " + branch.getName() ,e);
        } catch (ClassNotFoundException e) {
            log.error("Oracle driver not registered: " + e, e);
            throw new RuntimeException(e);
        }
        log.debug("Connected.");

        try {

            Statement stmt = conn.createStatement();

            try {
                ResultSet rs = stmt.executeQuery(objQuery);
                try {
                    log.debug("Processing obj >>>");
                    processObjRs(rs, cause);
                    log.debug("Processing obj complete");

                } finally {
                    rs.close();
                }
            } finally {
                stmt.close();
            }

            ///

            stmt = conn.createStatement();

            try {
                ResultSet rs = stmt.executeQuery(stepsQuery);
                try {
                    log.debug("Processing steps >>>");
                    processStepsRs(rs, cause);
                    log.debug("Processing steps complete");

                } finally {
                    rs.close();
                }
            } finally {
                stmt.close();
            }


            log.debug("Closing connection");
            OracleUtil.closeConnection(conn);
        } catch (SQLException sqle) {
            log.error("Error getting cause from server " + branch.getPvdIp() + ": " + sqle, sqle);
            throw new RuntimeException("Error getting cause from PVD server " + branch.getName() ,sqle);
        }

        return cause;
    }

    private void processObjRs(ResultSet rs, Cause cause) throws SQLException{

        if (rs.next()) {
            cause.setObj(new Obj(rs.getString("FULLADDRESS")));
        }

    }

    private void processApplicatorsRs(ResultSet rs, Cause cause) throws SQLException {

    }

    private void processStepsRs(ResultSet rs, Cause cause) throws SQLException {
        List<Step> steps = new ArrayList<Step>();
        while (rs.next()) {
            Step step = new Step();
            step.setOperation(rs.getString("ID_OPERATION"));
            step.setDateBegin(rs.getDate("DATEBEGIN"));
            step.setDateEnd(rs.getDate("DATEEND"));
            step.setState(rs.getInt("STATE"));
            step.setResolution(rs.getString("RESOLUTION"));
            steps.add(step);
        }
        cause.getSteps().addAll(steps);

        // setting current step
        if (steps.size()>0) {
            cause.setCurStep(steps.get(steps.size()-1));
        }
    }
}
