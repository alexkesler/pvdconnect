package org.kesler.server.domain;


import java.text.SimpleDateFormat;
import java.util.*;

public class Check {

    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private Branch branch;
    private Integer recordsSize=0;
    private Date checkDate;
    public Boolean lastSuccess;

    protected Check() {}

    public Check(Branch branch) {
        this.branch = branch;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getBranchName() {
        return branch.getName();
    }

    public void setRecordsSize(Integer recordsSize) {
        this.recordsSize = recordsSize;
    }

    public Integer getRecordsSize() {
        return recordsSize;
    }


    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Boolean getLastSuccess() { return lastSuccess; }
    public void setLastSuccess(Boolean lastSuccess) { this.lastSuccess = lastSuccess; }

    public String getCheckDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return checkDate == null ? "" : simpleDateFormat.format(checkDate);
    }

}

