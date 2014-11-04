package org.kesler.server.domain;




import java.text.SimpleDateFormat;
import java.util.*;

public class Check {

    private Long id;
    private Branch branch;
    private Set<Record> records;
    private Date checkDate;

    protected Check() {}

    public Check(Branch branch) {
        this.branch = branch;
        records = new HashSet<Record>();
    }

    public Long getId() { return id; }

    public Branch getBranch() {
        return branch;
    }

    public String getBranchName() {
        return branch.getName();
    }

    public int getRecordsSize() {
        return records.size();
    }

    public Collection<Record> getRecords() {
        return records;
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

    public void addRecord(Record record) {
        records.add(record);
    }

}

