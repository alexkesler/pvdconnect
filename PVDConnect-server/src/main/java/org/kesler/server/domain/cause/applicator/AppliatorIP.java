package org.kesler.server.domain.cause.applicator;

import org.kesler.server.domain.cause.Applicator;

public class AppliatorIP extends Applicator {
    private FL fl;
    private FL repres;

    @Override
    public String getCommonName() {
        return "ИП " + fl.getFIO() + (repres==null?"":" (" + repres.getFIO() + ")");
    }
}
