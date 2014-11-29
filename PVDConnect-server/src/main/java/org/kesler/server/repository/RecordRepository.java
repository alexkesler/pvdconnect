package org.kesler.server.repository;

import org.kesler.server.domain.Record;

import java.util.Collection;

/**
 * Created by alex on 29.11.14.
 */
public interface RecordRepository {
    public Collection<Record> getRecordsByRegnum(String regnum);
}
