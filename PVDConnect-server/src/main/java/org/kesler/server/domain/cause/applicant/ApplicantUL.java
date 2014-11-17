package org.kesler.server.domain.cause.applicant;

import org.kesler.server.domain.cause.Applicant;

public class ApplicantUL extends Applicant {
    private UL ul;
    private FL repres;

    public FL getRepres() {
        return repres;
    }

    public void setRepres(FL repres) {
        this.repres = repres;
    }

    public UL getUl() {
        return ul;
    }

    public void setUl(UL ul) {
        this.ul = ul;
    }

    @Override
    public String getCommonName() {
        return ul.getShortName() + (repres==null?"":" (" + repres.getFIO() + ")");
    }
}
