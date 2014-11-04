package org.kesler.server.controller;

import org.kesler.common.ChecksDTO;
import org.kesler.common.ProgressStatus;
import org.kesler.server.domain.Check;
import org.kesler.server.repository.CheckRepository;
import org.kesler.server.service.CheckService;
import org.kesler.server.transform.CheckTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;


@Controller
@RequestMapping("/checks")
public class CheckController {
    private static final Logger log = LoggerFactory.getLogger(CheckController.class);

    private CheckRepository checkRepository;
    @Autowired
    private CheckService checkService;

    @Autowired
    public CheckController(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @RequestMapping(value = "/go")
    public String doCheck() throws Exception{
        log.debug("Receive Check request");
        checkService.doCheck();
        log.debug("Checking complete.");
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ChecksDTO getChecksDTO() {
        log.debug("Receive getChecks request");
        Collection<Check> checks = checkRepository.getAllChecks();
        log.debug("Send " + checks.size() + " checks");
        return new ChecksDTO(CheckTransform.transform(checks));
    }

    @RequestMapping(value="/status", method = RequestMethod.GET)
    public @ResponseBody
    ProgressStatus getStatus() {
        log.debug("Receive ProcessStatus request");
        ProgressStatus status = new ProgressStatus();

        status.setMessage(checkService.getMessage());
        status.setTotal(checkService.getTotal());
        status.setCurrent(checkService.getCurrent());
        log.debug("Send status: " + status.getMessage());
        return status;
    }

}
