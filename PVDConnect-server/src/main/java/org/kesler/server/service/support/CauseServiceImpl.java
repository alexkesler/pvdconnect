package org.kesler.server.service.support;

import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Cause;
import org.kesler.server.domain.Record;
import org.kesler.server.domain.cause.Applicant;
import org.kesler.server.domain.cause.Obj;
import org.kesler.server.domain.cause.Step;
import org.kesler.server.domain.cause.applicant.ApplicantFL;
import org.kesler.server.domain.cause.applicant.ApplicantUL;
import org.kesler.server.domain.cause.applicant.FL;
import org.kesler.server.domain.cause.applicant.UL;
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
        cause.setCauseId(record.getCausePvdId());
        cause.setRecord(record);

        String objQuery = "SELECT O.FULLADDRESS, C.STATE, C.STATUSMD, C.PURPOSE, " +
                "C.STARTDATE, C.STATECHANGEDATE, C.ESTIMATEDATE " +
                "FROM DPS$D_CAUSE C " +
                "LEFT JOIN DPS$OBJ O ON O.ID_CAUSE=C.ID " +
                "WHERE C.ID='" + cause.getCauseId() + "'";


        String applicantsQuery = "SELECT SA.ID_CLSTYPE AS A_CLSTYPE, " +
                "SA.SURNAME AS A_SURNAME, SA.FIRSTNAME AS A_FIRSTNAME, SA.PATRONYMIC AS A_PATRONYMIC, SA.SHORTNAME AS A_SHORTNAME, " +
                "SR.ID AS R_ID, SR.SURNAME AS R_SURNAME, SR.FIRSTNAME AS R_FIRSTNAME, SR.PATRONYMIC AS R_PATRONYMIC " +
                "FROM DPS$D_CAUSE C " +
                "LEFT JOIN DPS$APPLICANT A ON A.ID_CAUSE=C.ID " +
                "LEFT JOIN DPS$SUBJECT SA ON SA.ID=A.ID_SUBJECT " +
                "LEFT JOIN DPS$SUBJECT SR ON SR.ID=A.ID_AGENT " +
                "WHERE C.ID='" + cause.getCauseId() + "'";

        String stepsQuery = "SELECT S.RESOLUTION, S.ID_OPERATION, S.DATEBEGIN, S.DATEEND, S.ESTIMATEDATE, S.STATE " +
                "FROM DPS$STEP S " +
                "WHERE S.ID_CAUSE='" + record.getCausePvdId() + "' " +
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

            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(objQuery)) {
                    log.debug("Processing obj >>>");
                    processObjRs(rs, cause);
                    log.debug("Processing obj complete");
                }
            }

            try (Statement stmt = conn.createStatement()){
                try (ResultSet rs = stmt.executeQuery(applicantsQuery)) {
                    log.debug("Processing applicants >>>");
                    processApplicantsRs(rs, cause);
                    log.debug("Processing applicants complete");
                }
            }

            try (Statement stmt = conn.createStatement()){
                try (ResultSet rs = stmt.executeQuery(stepsQuery)) {
                    log.debug("Processing steps >>>");
                    processStepsRs(rs, cause);
                    log.debug("Processing steps complete");
                }
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
            Obj obj = new Obj();
            obj.setFullAddress(rs.getString("FULLADDRESS"));
            cause.setObj(obj);
            cause.setPurpose(rs.getInt("PURPOSE"));
            cause.setState(rs.getInt("STATE"));
            cause.setStatusMd(rs.getString("STATUSMD"));
            cause.setStartDate(rs.getDate("STARTDATE"));
            cause.setStateChangeDate(rs.getDate("STATECHANGEDATE"));
            cause.setEstimateDate(rs.getDate("ESTIMATEDATE"));

        }

    }

    private void processApplicantsRs(ResultSet rs, Cause cause) throws SQLException {

        List<Applicant> applicants = new ArrayList<>();

        while (rs.next()) {
            String clstype = rs.getString("A_CLSTYPE");
            String cls = clstype.substring(0,3);

            Applicant applicant=null;

            FL repres = null;
            String represId = rs.getString("R_ID");
            if (represId != null) {
                repres = new FL();

                String represSurName = rs.getString("R_SURNAME");
                String represFirstName = rs.getString("R_FIRSTNAME");
                String represPatronymic = rs.getString("R_PATRONYMIC");

                repres.setSurName(represSurName);
                repres.setFirstName(represFirstName);
                repres.setParentName(represPatronymic);
            }


            if (cls.equals("7.3")) { // физ лицо
                ApplicantFL applicantFL = new ApplicantFL();
                FL subject = new FL();
                String subjectSurName = rs.getString("A_SURNAME");
                String subjectFirstName = rs.getString("A_FIRSTNAME");
                String subjectPatronymic = rs.getString("A_PATRONYMIC");

                subject.setFirstName(subjectFirstName);
                subject.setParentName(subjectPatronymic);
                subject.setSurName(subjectSurName);

                applicantFL.setFl(subject);

                applicantFL.setRepres(repres);

                applicant = applicantFL;
            } else if (cls.equals("7.1") || cls.equals("7.2")) { // юр лицо или огв
                ApplicantUL applicantUL = new ApplicantUL();

                UL subject = new UL();
                String subjectShortName = rs.getString("A_SHORTNAME");
                subject.setShortName(subjectShortName);

                applicantUL.setUl(subject);

                applicantUL.setRepres(repres);

                applicant = applicantUL;
            }


            applicants.add(applicant);
        }

        cause.getApplicants().addAll(applicants);
    }

    private void processStepsRs(ResultSet rs, Cause cause) throws SQLException {
        List<Step> steps = new ArrayList<Step>();
        while (rs.next()) {
            Step step = new Step();
            step.setOperation(rs.getString("ID_OPERATION"));
            step.setDateBegin(rs.getDate("DATEBEGIN"));
            step.setDateEnd(rs.getDate("DATEEND"));
            step.setEstimateDate(rs.getDate("ESTIMATEDATE"));
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
