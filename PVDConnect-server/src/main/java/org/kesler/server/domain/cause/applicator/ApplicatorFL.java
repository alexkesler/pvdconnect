package org.kesler.server.domain.cause.applicator;

import org.kesler.server.domain.cause.Applicator;

public class ApplicatorFL extends Applicator {
    private FL fl;
    private FL repres;

    public FL getFl() {
        return fl;
    }

    public void setFl(FL fl) {
        this.fl = fl;
    }

    public FL getRepres() {
        return repres;
    }

    public void setRepres(FL repres) {
        this.repres = repres;
    }

    @Override
    public String getCommonName() {
        return fl.getFIO() + (repres==null?"":" (" + repres.getFIO() + ")");
    }
}
