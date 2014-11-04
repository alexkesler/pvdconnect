package org.kesler.common;

import java.util.Collection;

public class BranchesDTO {
    private Collection<BranchDTO> branchDTOs;

    public BranchesDTO() {}
    public BranchesDTO(Collection<BranchDTO> branchDTOs) {
        this.branchDTOs = branchDTOs;
    }

    public Collection<BranchDTO> getBranchDTOs() {
        return branchDTOs;
    }
    public void setBranchDTOs(Collection<BranchDTO> branchDTOs) {
        this.branchDTOs = branchDTOs;
    }
}
