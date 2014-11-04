package org.kesler.client.service.support;

import org.kesler.client.domain.Record;
import org.kesler.client.service.RecordService;
import org.kesler.client.transform.RecordTransform;
import org.kesler.common.RecordDTO;
import org.kesler.common.RecordsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RecordRestServiceImpl implements RecordService {
    private static final Logger log = LoggerFactory.getLogger(RecordRestServiceImpl.class);
    private String serverUrl;

    @Autowired
    private RestTemplate restTemplate;

    public RecordRestServiceImpl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public Collection<Record> getRecordsByCode(String code) {
        log.debug("Getting recordsDTO by code: " + code);
        Map<String,Object> values = new HashMap<String, Object>();
        values.put("code", code);
        RecordsDTO recordsDTO = restTemplate.getForObject(serverUrl + "/records?code={code}", RecordsDTO.class, values);
        return RecordTransform.transform(recordsDTO.getRecordDTOs());
    }


}
