package org.kesler.client.transform;


import org.kesler.client.domain.Check;
import org.kesler.common.CheckDTO;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CheckTransform {
    public static Check transform(CheckDTO checkDTO) {
        Check check = new Check();

        check.setBranch(BranchTransform.transform(checkDTO.getBranchDTO()));
        check.setCheckDate(checkDTO.getCheckDate());
        check.setRecordsSize(checkDTO.getRecordsSize());

        return check;
    }

    public static Collection<Check> transform(Collection<CheckDTO> checkDTOs) {
        Collection<Check> checks = new ArrayList<Check>();

        for (CheckDTO checkDTO:checkDTOs) {
            checks.add(transform(checkDTO));
        }

        return checks;
    }
}
