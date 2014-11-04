package org.kesler.server.repository;

import org.kesler.server.domain.Branch;

import java.util.Collection;


public interface BranchRepository {
    public void addBranch(Branch branch);
    public Collection<Branch> getAllBranches();
    public Branch getBranchById(Long id);
    public void updateBranch(Branch branch);
    public void removeBranch(Branch branch);
}
