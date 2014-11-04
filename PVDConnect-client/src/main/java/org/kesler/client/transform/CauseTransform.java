package org.kesler.client.transform;

import org.kesler.client.domain.Cause;
import org.kesler.common.CauseDTO;

public abstract class CauseTransform {
    public static Cause transform(CauseDTO causeDTO) {
        Cause cause = new Cause();

        cause.setId(causeDTO.getId());
        cause.setRecord(RecordTransform.transform(causeDTO.getRecordDTO()));
        cause.setApplicators(causeDTO.getApplcators());
        cause.setObj(causeDTO.getObj());
        cause.setSteps(causeDTO.getSteps());
        cause.setCurStep(causeDTO.getCurStep());

        return cause;
    }
}
