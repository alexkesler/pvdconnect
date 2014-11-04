package org.kesler.client.service.support;

import org.kesler.client.domain.Check;
import org.kesler.client.service.CheckService;
import org.kesler.client.transform.CheckTransform;
import org.kesler.common.ChecksDTO;
import org.kesler.common.ProgressStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Служба для отправки REST запросов при работе с перечнем
 */
@Service
public class CheckRestServiceImpl implements CheckService{

    private static final Logger log = LoggerFactory.getLogger(CheckRestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private String serverUrl;

    public CheckRestServiceImpl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public Collection<Check> getAllChecks() {
        log.debug("Getting all checks");
        ChecksDTO checksDTO = restTemplate.getForObject(serverUrl + "/checks", ChecksDTO.class);
        log.debug("Recieved from server " + checksDTO.getCheckDTOs().size() + " checks");
        return CheckTransform.transform(checksDTO.getCheckDTOs());
    }

    @Override
    public void doChecks() {
        log.info("Starting checks...");
        restTemplate.put(serverUrl + "/checks/go", null);
        log.info("Checks complete.");
    }

    @Override
    public ProgressStatus getProcessStatus() {
        log.debug("Get process status..");
        ProgressStatus status = restTemplate.getForObject(serverUrl + "/checks/status",ProgressStatus.class);
        log.debug("Process status: " + status.getCurrent() + " of " + status.getTotal());
        return status;
    }
}
