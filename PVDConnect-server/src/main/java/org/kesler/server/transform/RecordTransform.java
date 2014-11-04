package org.kesler.server.transform;


import org.kesler.common.RecordDTO;
import org.kesler.server.domain.Record;

import java.util.ArrayList;
import java.util.Collection;

public abstract class RecordTransform {
    public static RecordDTO transform(Record record) {
        RecordDTO recordDTO = new RecordDTO();

        recordDTO.setId(record.getId());
        recordDTO.setCauseId(record.getCauseId());
        recordDTO.setBranchDTO(BranchTransform.transform(record.getBranch()));
        recordDTO.setRegnum(record.getRegnum());
        recordDTO.setRegdate(record.getRegdate());

        return recordDTO;
    }

    public static Collection<RecordDTO> transform(Collection<Record> records) {
        Collection<RecordDTO> recordDTOs = new ArrayList<RecordDTO>();

        for (Record record:records) {
            recordDTOs.add(transform(record));
        }

        return recordDTOs;
    }

    public static Record transform(RecordDTO recordDTO) {
        Record record = new Record();

        record.setId(recordDTO.getId());
        record.setBranch(BranchTransform.transform(recordDTO.getBranchDTO()));
        record.setCauseId(recordDTO.getCauseId());
        record.setRegnum(recordDTO.getRegnum());
        record.setRegdate(recordDTO.getRegdate());

        return record;
    }
}
