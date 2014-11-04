package org.kesler.common;

import java.util.Date;

public class RecordDTO  {
    private Long id;
    private BranchDTO branchDTO;
    private String causeId;
    private String regnum;
    private Date regdate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BranchDTO getBranchDTO() { return branchDTO; }
    public void setBranchDTO(BranchDTO branchDTO) { this.branchDTO = branchDTO; }

    public String getCauseId() { return causeId; }
    public void setCauseId(String causeId) { this.causeId = causeId; }

    public String getRegnum() { return regnum; }
    public void setRegnum(String regnum) { this.regnum = regnum; }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

}
