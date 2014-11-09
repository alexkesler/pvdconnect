package org.kesler.client.transform;

import org.kesler.client.domain.Cause;
import org.kesler.common.CauseDTO;

public abstract class CauseTransform {
    public static Cause transform(CauseDTO causeDTO) {
        Cause cause = new Cause();

        cause.setCauseId(causeDTO.getCauseId());
        cause.setRecord(RecordTransform.transform(causeDTO.getRecordDTO()));
        cause.setPurposeString(causeDTO.getPurposeString());
        cause.setStateString(causeDTO.getStateString());
        cause.setStatusMdString(causeDTO.getStatusMdString());
        cause.setStateChangeDate(causeDTO.getStateChangeDate());
        cause.setEstimateDate(causeDTO.getEstimateDate());
        cause.setApplicators(causeDTO.getApplicators());
        cause.setObj(causeDTO.getObj());
        cause.setSteps(causeDTO.getSteps());
        cause.setCurStep(causeDTO.getCurStep());

        return cause;
    }
}
