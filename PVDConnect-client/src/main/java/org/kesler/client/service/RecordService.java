package org.kesler.client.service;


import org.kesler.client.domain.Record;
import org.kesler.common.RecordDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface RecordService {

    public Collection<Record> getRecordsByCode(String code);

}
