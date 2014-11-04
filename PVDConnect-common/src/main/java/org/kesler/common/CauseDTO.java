package org.kesler.common;


import java.util.Collection;

public class CauseDTO {

    private String id;
    private RecordDTO recordDTO;
    private Collection<String> applcators;
    private Collection<String> steps;
    private String curStep;
    private String obj;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RecordDTO getRecordDTO() {
        return recordDTO;
    }

    public void setRecordDTO(RecordDTO recordDTO) {
        this.recordDTO = recordDTO;
    }

    public Collection<String> getApplcators() {
        return applcators;
    }

    public void setApplcators(Collection<String> applcators) {
        this.applcators = applcators;
    }

    public Collection<String> getSteps() {
        return steps;
    }

    public void setSteps(Collection<String> steps) {
        this.steps = steps;
    }

    public String getCurStep() {
        return curStep;
    }

    public void setCurStep(String curStep) {
        this.curStep = curStep;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
