package org.kesler.common;

import java.util.Collection;

public class ChecksDTO {
    private Collection<CheckDTO> checkDTOs;

    public ChecksDTO() {}

    public ChecksDTO(Collection<CheckDTO> checkDTOs) {
        this.checkDTOs = checkDTOs;
    }

    public Collection<CheckDTO> getCheckDTOs() {
        return checkDTOs;
    }
    public void setCheckDTOs(Collection<CheckDTO> checkDTOs) {
        this.checkDTOs = checkDTOs;
    }
}
