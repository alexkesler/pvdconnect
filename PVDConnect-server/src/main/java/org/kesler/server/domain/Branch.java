package org.kesler.server.domain;

public class Branch {
    private Long id;
	private String code;
    private String name;
    private String pvdIp;
    private String pvdUser;
    private String pvdPassword;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

        if (code != null ? !code.equals(branch.code) : branch.code != null) return false;
        if (id != null ? !id.equals(branch.id) : branch.id != null) return false;
        if (name != null ? !name.equals(branch.name) : branch.name != null) return false;
        if (pvdIp != null ? !pvdIp.equals(branch.pvdIp) : branch.pvdIp != null) return false;
        if (pvdPassword != null ? !pvdPassword.equals(branch.pvdPassword) : branch.pvdPassword != null) return false;
        if (pvdUser != null ? !pvdUser.equals(branch.pvdUser) : branch.pvdUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pvdIp != null ? pvdIp.hashCode() : 0);
        result = 31 * result + (pvdUser != null ? pvdUser.hashCode() : 0);
        result = 31 * result + (pvdPassword != null ? pvdPassword.hashCode() : 0);
        return result;
    }
}
