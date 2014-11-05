package org.kesler.server.domain;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Record {
    private Long id;
    private Branch branch;
    private String causePvdId;
    private String regnum;
    private Date regdate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }

    public String getBranchName() {
        return branch ==null?"": branch.getName();
    }

    public String getCausePvdId() { return causePvdId; }
    public void setCausePvdId(String causePvdId) { this.causePvdId = causePvdId; }

    public String getRegnum() { return regnum; }
    public void setRegnum(String regnum) { this.regnum = regnum; }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getRegdateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return regdate==null?"":simpleDateFormat.format(regdate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record that = (Record) o;

        if (!branch.equals(that.branch)) return false;
        if (!causePvdId.equals(that.causePvdId)) return false;
        if (!regdate.equals(that.regdate)) return false;
        if (!regnum.equals(that.regnum)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = branch.hashCode();
        result = 31 * result + causePvdId.hashCode();
        result = 31 * result + regnum.hashCode();
        result = 31 * result + regdate.hashCode();
        return result;
    }
}
