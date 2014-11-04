package org.kesler.common;

import java.util.Date;

public class CheckDTO {

    private BranchDTO branchDTO;
    private Date checkDate;
    private int recordsSize;

    public CheckDTO() {}

    public BranchDTO getBranchDTO() {
        return branchDTO;
    }
    public void setBranchDTO(BranchDTO branchDTO) {
        this.branchDTO = branchDTO;
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




}

