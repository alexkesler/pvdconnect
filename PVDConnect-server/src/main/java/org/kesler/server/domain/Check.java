package org.kesler.server.domain;


import java.text.SimpleDateFormat;
import java.util.*;

public class Check {

    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private Branch branch;
    private Set<Record> records;
    private Integer recordsSize=0;
    private Date checkDate;

    protected Check() {}

    public Check(Branch branch) {
        this.branch = branch;
        records = new HashSet<Record>();
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

    public Collection<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
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

