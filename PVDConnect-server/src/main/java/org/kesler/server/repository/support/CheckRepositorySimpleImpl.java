package org.kesler.server.repository.support;

import org.kesler.server.domain.Check;
import org.kesler.server.repository.CheckRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CheckRepositorySimpleImpl implements CheckRepository {
    private List<Check> checks = new ArrayList<Check>();

    @Override
    public void addCheck(Check check) {

        Iterator<Check> iterator = checks.iterator();
        Check chk = null;
        while (iterator.hasNext()) {
            chk = iterator.next();
            if(check.getBranch().equals(chk.getBranch())) iterator.remove();
        }
        checks.add(check);
    }

    @Override
    public Collection<Check> getAllChecks() {
        return checks;
    }
}
