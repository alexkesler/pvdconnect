package org.kesler.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupWatcher implements ApplicationListener<ContextRefreshedEvent>{
    private static Logger log = LoggerFactory.getLogger(StartupWatcher.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Server started... ");
    }
}
