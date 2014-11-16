package org.kesler.server.domain;


import org.kesler.server.domain.cause.Applicant;
import org.kesler.server.domain.cause.Obj;
import org.kesler.server.domain.cause.Step;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class Cause {

    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private String causeId;
    private Record record;
    private Integer purpose;
    private String purposeString;
    private Collection<Applicant> applicants;
    private Obj obj;
    private Collection<Step> steps;
    private Step curStep;
    private Integer state;
    private String stateString;
    private String statusMd;
    private String statusMdString;
    private Date startDate;
    private Date stateChangeDate;
    private Date estimateDate;

    public Cause() {
        applicants = new ArrayList<Applicant>();
        steps = new ArrayList<Step>();
    }

    public String getUuid() { return uuid; }

    public String getCauseId() { return causeId; }
    public void setCauseId(String causeId) { this.causeId = causeId; }

    public Record getRecord() { return record; }
    public void setRecord(Record record) { this.record = record; }

    public Integer getPurpose() { return purpose; }
    public void setPurpose(Integer purpose) { this.purpose = purpose; }

    public Collection<Applicant> getApplicants() { return applicants; }

    public Obj getObj() { return obj; }
    public void setObj(Obj obj) { this.obj = obj; }

    public Collection<Step> getSteps() { return steps; }

    public Step getCurStep() { return curStep; }
    public void setCurStep(Step curStep) { this.curStep = curStep; }

    public Integer getState() { return state; }
    public void setState(Integer state) { this.state = state; }

    public String getStatusMd() { return statusMd; }
    public void setStatusMd(String statusMd) { this.statusMd = statusMd; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getStateChangeDate() { return stateChangeDate; }
    public void setStateChangeDate(Date stateChangeDate) { this.stateChangeDate = stateChangeDate; }

    public Date getEstimateDate() { return estimateDate; }
    public void setEstimateDate(Date estimateDate) { this.estimateDate = estimateDate; }

}
