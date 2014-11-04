package org.kesler.client.service.support;

import org.kesler.client.domain.Cause;
import org.kesler.client.domain.Record;
import org.kesler.client.service.CauseService;
import org.kesler.client.transform.CauseTransform;
import org.kesler.client.transform.RecordTransform;
import org.kesler.common.CauseDTO;
import org.kesler.common.RecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class CauseRestServiceImpl implements CauseService {

    private static final Logger log = LoggerFactory.getLogger(CauseRestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private String serverUrl;

    public CauseRestServiceImpl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public Cause getCauseByRecord(Record record) {
        log.info("Getting cause " + record.getRegnum() + " from server");
        RecordDTO recordDTO = RecordTransform.transform(record);

        CauseDTO causeDTO = restTemplate.postForObject(serverUrl + "/cause", recordDTO, CauseDTO.class);

        Cause cause = CauseTransform.transform(causeDTO);

        log.info("Recieve cause " + cause.getRecord().getRegnum() + " from server");
        return cause;
    }

}
