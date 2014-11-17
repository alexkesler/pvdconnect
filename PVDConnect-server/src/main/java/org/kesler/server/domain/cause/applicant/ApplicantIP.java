package org.kesler.server.domain.cause.applicant;

import org.kesler.server.domain.cause.Applicant;

public class ApplicantIP extends Applicant {
    private FL fl;
    private FL repres;

    @Override
    public String getCommonName() {
        return "ИП " + fl.getFIO() + (repres==null?"":" (" + repres.getFIO() + ")");
    }
}
