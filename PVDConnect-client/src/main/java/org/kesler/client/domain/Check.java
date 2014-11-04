package org.kesler.client.domain;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Check {

    private Branch branch;
    private Date checkDate;
    private int recordsSize;

    public Check() {}

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getBranchName() {
        return branch.getName();
    }

    public int getRecordsSize() {
        return recordsSize;
    }

    public void setRecordsSize(int recordsSize) {
        this.recordsSize = recordsSize;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return checkDate == null ? "" : simpleDateFormat.format(checkDate);
    }


}

