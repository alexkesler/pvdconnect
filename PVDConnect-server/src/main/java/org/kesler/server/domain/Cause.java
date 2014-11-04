package org.kesler.server.domain;


import org.kesler.server.domain.cause.Applicator;
import org.kesler.server.domain.cause.Obj;
import org.kesler.server.domain.cause.Step;

import java.util.ArrayList;
import java.util.Collection;

public class Cause {

    private String id;
    private Record record;
    private Collection<Applicator> applicators;
    private Obj obj;
    private Collection<Step> steps;
    private Step curStep;

    public Cause() {
        applicators = new ArrayList<Applicator>();
        steps = new ArrayList<Step>();
    }

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

    public Collection<Applicator> getApplicators() {
        return applicators;
    }

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public Collection<Step> getSteps() {
        return steps;
    }

    public Step getCurStep() {
        return curStep;
    }

    public void setCurStep(Step curStep) {
        this.curStep = curStep;
    }
}
