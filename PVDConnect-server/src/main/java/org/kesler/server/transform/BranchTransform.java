package org.kesler.server.transform;

import org.kesler.common.BranchDTO;
import org.kesler.server.domain.Branch;

import java.util.ArrayList;
import java.util.Collection;


public abstract class BranchTransform {

    public static BranchDTO transform(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();

        branchDTO.setId(branch.getId());
        branchDTO.setCode(branch.getCode());
        branchDTO.setName(branch.getName());
        branchDTO.setPvdIp(branch.getPvdIp());
        branchDTO.setPvdUser(branch.getPvdUser());
        branchDTO.setPvdPassword(branch.getPvdPassword());

        return branchDTO;
    }

    public static Branch transform(BranchDTO branchDTO) {
        Branch branch = new Branch();

        branch.setId(branchDTO.getId());
        branch.setCode(branchDTO.getCode());
        branch.setName(branchDTO.getName());
        branch.setPvdIp(branchDTO.getPvdIp());
        branch.setPvdUser(branchDTO.getPvdUser());
        branch.setPvdPassword(branchDTO.getPvdPassword());

        return branch;
    }

    public static Collection<BranchDTO> transform(Collection<Branch> branches) {
        Collection<BranchDTO> branchDTOs = new ArrayList<BranchDTO>();
        for (Branch branch:branches) {
            branchDTOs.add(transform(branch));
        }
        return branchDTOs;
    }


}
