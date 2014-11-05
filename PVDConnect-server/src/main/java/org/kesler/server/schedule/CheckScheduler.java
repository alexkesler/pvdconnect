package org.kesler.server.schedule;

import org.kesler.server.service.CheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckScheduler {
    private static Logger log = LoggerFactory.getLogger(CheckScheduler.class);

    @Autowired
    CheckService checkService;

    @Scheduled(cron = "0 0 21 * * *")
//    @Scheduled(cron = "0 */1 * * * *")
    public void doCheck() {
        log.info("Run scheduled CheckTask");
        checkService.doCheck();
        log.info("Scheduled CheckTask complete");
    }
}
