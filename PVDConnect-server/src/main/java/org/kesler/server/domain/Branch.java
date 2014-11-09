package org.kesler.server.domain;

import java.util.UUID;

public class Branch {

    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private String code;
    private String name;
    private String pvdIp;
    private String pvdUser;
    private String pvdPassword;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) {this.uuid = uuid; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPvdIp() { return pvdIp; }
    public void setPvdIp(String pvdIp) { this.pvdIp = pvdIp; }

    public String getPvdUser() { return pvdUser; }
    public void setPvdUser(String pvdUser) { this.pvdUser = pvdUser; }

    public String getPvdPassword() { return pvdPassword; }
    public void setPvdPassword(String pvdPassword) { this.pvdPassword = pvdPassword; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Branch branch = (Branch) o;

        return this.uuid.equals(branch.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
