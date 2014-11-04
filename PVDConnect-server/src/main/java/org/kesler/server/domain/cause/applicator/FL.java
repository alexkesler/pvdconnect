package org.kesler.server.domain.cause.applicator;

public class FL {
    private String firstName;
    private String surName;
    private String parentName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFIO() {
        return surName + " " + firstName + " " + parentName;
    }
}
