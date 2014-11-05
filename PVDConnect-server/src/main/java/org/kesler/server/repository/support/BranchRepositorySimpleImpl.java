package org.kesler.server.repository.support;

import org.kesler.server.domain.Branch;
import org.kesler.server.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BranchRepositorySimpleImpl implements BranchRepository {

    private static final Logger log = LoggerFactory.getLogger(BranchRepositorySimpleImpl.class);
    private  List<Branch> branches = new ArrayList<Branch>();

    @Override
    public void addBranch(Branch branch) {
        log.info("Saving branch: " + branch.getName());
        branches.add(branch);
    }

    @Override
    public Collection<Branch> getAllBranches() {
        log.info("Getting all branches");
        return branches;
    }

    @Override
    public Branch getBranchById(Long id) {
        return null;
    }

    @Override
    public void updateBranch(Branch branch) {
        for (Branch oldBranch:branches) {
            if (oldBranch.getUuid().equals(branch.getUuid())) {
                oldBranch.setName(branch.getName());
                oldBranch.setCode(branch.getCode());
                oldBranch.setPvdIp(branch.getPvdIp());
                oldBranch.setPvdUser(branch.getPvdUser());
                oldBranch.setPvdPassword(branch.getPvdPassword());
            }
        }

    }

    @Override
    public void removeBranch(Branch branch) {
        branches.remove(branch);
    }
}
