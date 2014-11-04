package org.kesler.server.transform;

import org.kesler.common.CauseDTO;
import org.kesler.server.domain.Cause;
import org.kesler.server.domain.cause.Applicator;
import org.kesler.server.domain.cause.Step;

import java.util.ArrayList;
import java.util.List;


public abstract class CauseTransform {
    public static CauseDTO transform(Cause cause) {
        CauseDTO causeDTO = new CauseDTO();

        causeDTO.setId(cause.getId());
        causeDTO.setRecordDTO(RecordTransform.transform(cause.getRecord()));

        List<String> applicators = new ArrayList<String>();
        for (Applicator applicator:cause.getApplicators()) {
            applicators.add(applicator.getCommonName());
        }

        causeDTO.setApplcators(applicators);

        causeDTO.setObj(cause.getObj()==null?"Не опр":cause.getObj().getFullAddress());

        causeDTO.setCurStep(cause.getCurStep()==null?"Не опр":cause.getCurStep().getCurName());

        List<String> steps = new ArrayList<String>();
        for (Step step:cause.getSteps()) {
            steps.add(step.getHistName());
        }

        causeDTO.setSteps(steps);

        return causeDTO;
    }
}
