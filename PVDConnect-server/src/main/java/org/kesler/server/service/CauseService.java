package org.kesler.server.service;

import org.kesler.server.domain.Cause;
import org.kesler.server.domain.Record;

public interface CauseService {

    public Cause getCauseByRecord(Record record);

}
