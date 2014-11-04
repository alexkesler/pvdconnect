package org.kesler.client.service;

import org.kesler.client.domain.Cause;
import org.kesler.client.domain.Record;

public interface CauseService {
    public Cause getCauseByRecord(Record record);
}
