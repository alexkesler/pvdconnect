package org.kesler.client.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record implements Comparable<Record> {
    private Long id;
    private Branch branch;
    private String causeId;
    private String regnum;
    private Date regdate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }

    public String getBranchName() {
        return branch ==null?"": branch.getName();
    }

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

    public String getRegdateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return regdate==null?"":simpleDateFormat.format(regdate);
    }

    @Override
    public int compareTo(Record o) {
        if(o.getRegdate().before(regdate)) return 1;
        else if (o.getRegdate().after(regdate)) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record that = (Record) o;

        if (!branch.equals(that.branch)) return false;
        if (!causeId.equals(that.causeId)) return false;
        if (!regdate.equals(that.regdate)) return false;
        if (!regnum.equals(that.regnum)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = branch.hashCode();
        result = 31 * result + causeId.hashCode();
        result = 31 * result + regnum.hashCode();
        result = 31 * result + regdate.hashCode();
        return result;
    }

}
