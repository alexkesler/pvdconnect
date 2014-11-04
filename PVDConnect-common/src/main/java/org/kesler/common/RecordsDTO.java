package org.kesler.common;

import java.util.Collection;

public class RecordsDTO {
    private Collection<RecordDTO> recordDTOs;

    public RecordsDTO() {}

    public RecordsDTO(Collection<RecordDTO> recordDTOs) {
        this.recordDTOs = recordDTOs;
    }

    public Collection<RecordDTO> getRecordDTOs() {
        return recordDTOs;
    }
    public void setRecordDTOs(Collection<RecordDTO> recordDTOs) {
        this.recordDTOs = recordDTOs;
    }
}
