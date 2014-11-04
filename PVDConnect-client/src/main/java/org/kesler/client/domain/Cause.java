package org.kesler.client.domain;

import java.util.Collection;

public class Cause {
    private String id;
    private Record record;
    private Collection<String> applicators;
    private Collection<String> steps;
    private String obj;
    private String curStep;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Collection<String> getApplicators() {
        return applicators;
    }

    public void setApplicators(Collection<String> applicators) {
        this.applicators = applicators;
    }

    public Collection<String> getSteps() {
        return steps;
    }

    public void setSteps(Collection<String> steps) {
        this.steps = steps;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getCurStep() {
        return curStep;
    }

    public void setCurStep(String curStep) {
        this.curStep = curStep;
    }
}
