package org.kesler.server.domain.cause;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Step {
    private String operation;
    private Date dateBegin;
    private Date dateEnd;
    private Integer state;
    private String resolution;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCurName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return (dateBegin==null?"no-date":dateFormat.format(dateBegin)) +
                "-"+
                (dateEnd==null?"no-date":dateFormat.format(dateEnd)) +
                (operation==null?"":" (" + operation + ") - ") +
                (resolution==null?"":resolution);
    }

    public String getHistName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return (dateBegin==null?"no-date":dateFormat.format(dateBegin)) +
                "-"+
                (dateEnd==null?"no-date":dateFormat.format(dateEnd)) +
                (operation==null?"":" (" + operation + ") - ") +
                (resolution==null?"":resolution);
    }
}
