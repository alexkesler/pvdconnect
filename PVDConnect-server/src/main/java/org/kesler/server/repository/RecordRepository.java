package org.kesler.server.repository;

import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Record;

import java.util.Collection;

/**
 * Created by alex on 29.11.14.
 */
public interface RecordRepository {
    public void saveRecords(Collection<Record> records);
    public Collection<Record> getRecordsByRegnum(String regnum);
    public Integer getRecordsCount(Branch branch);
}
