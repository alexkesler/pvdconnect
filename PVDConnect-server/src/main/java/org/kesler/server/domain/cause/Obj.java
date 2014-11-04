package org.kesler.server.domain.cause;

public class Obj {
    private String fullAddress;

    public Obj() {}

    public Obj(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
