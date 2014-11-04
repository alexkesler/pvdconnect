package org.kesler.client.service;

import org.kesler.client.domain.Branch;

import java.util.Collection;

public interface BranchService {
    public Collection<Branch> getAllBranches();
    public void addBranch(Branch branch);
    public void removeBranch(Branch branch);
    public void updateBranch(Branch branch);
}
