package org.kesler.client.transform;

import org.kesler.client.domain.Branch;
import org.kesler.common.BranchDTO;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BranchTransform {

    public static Branch transform(BranchDTO branchDTO) {
        Branch branch = new Branch();

        branch.setId(branchDTO.getId());
        branch.setUuid(branchDTO.getUuid());
        branch.setCode(branchDTO.getCode());
        branch.setName(branchDTO.getName());
        branch.setPvdIp(branchDTO.getPvdIp());
        branch.setPvdUser(branchDTO.getPvdUser());
        branch.setPvdPassword(branchDTO.getPvdPassword());

        return branch;
    }

    public static Collection<Branch> transform(Collection<BranchDTO> branchDTOs) {
        Collection<Branch> branches = new ArrayList<Branch>();

        for (BranchDTO branchDTO:branchDTOs) {
            branches.add(transform(branchDTO));
        }

        return branches;
    }

    public static BranchDTO transform(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();

        branchDTO.setId(branch.getId());
        branchDTO.setUuid(branch.getUuid());
        branchDTO.setCode(branch.getCode());
        branchDTO.setName(branch.getName());
        branchDTO.setPvdIp(branch.getPvdIp());
        branchDTO.setPvdUser(branch.getPvdUser());
        branchDTO.setPvdPassword(branch.getPvdPassword());

        return branchDTO;
    }
}
