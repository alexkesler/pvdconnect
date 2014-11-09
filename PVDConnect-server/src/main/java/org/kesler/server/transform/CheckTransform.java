package org.kesler.server.transform;

import org.kesler.common.CheckDTO;
import org.kesler.server.domain.Check;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CheckTransform{

    public static CheckDTO transform(Check check) {
        CheckDTO checkDTO = new CheckDTO();
        checkDTO.setBranchDTO(BranchTransform.transform(check.getBranch()));
        checkDTO.setCheckDate(check.getCheckDate());
        checkDTO.setRecordsSize(check.getRecordsSize());

        return checkDTO;
    }

    public static Collection<CheckDTO> transform(Collection<Check> checks) {
        Collection<CheckDTO> checkDTOs = new ArrayList<CheckDTO>();

        for (Check check:checks) {
            checkDTOs.add(transform(check));
        }

        return checkDTOs;
    }

}
