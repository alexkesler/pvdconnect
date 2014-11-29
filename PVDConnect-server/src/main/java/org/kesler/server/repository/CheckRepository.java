package org.kesler.server.repository;

import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Check;

import java.util.Collection;

public interface CheckRepository {

    public void addCheck(Check check);
    public Collection<Check> getAllChecks();
    public Check getCheckByBranch(Branch branch);

}
