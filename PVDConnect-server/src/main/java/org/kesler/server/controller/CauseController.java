package org.kesler.server.controller;

import org.kesler.common.CauseDTO;
import org.kesler.common.RecordDTO;
import org.kesler.server.service.CauseService;
import org.kesler.server.transform.CauseTransform;
import org.kesler.server.transform.RecordTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cause")
public class CauseController {
    private static final Logger log = LoggerFactory.getLogger(CauseController.class);

    @Autowired
    private CauseService causeService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    CauseDTO getCause(@RequestBody RecordDTO recordDTO) throws Exception {
        log.info("Recieve Cause request " + recordDTO.getRegnum());
        CauseDTO causeDTO = CauseTransform.transform(causeService.getCauseByRecord(RecordTransform.transform(recordDTO)));
        log.info("Send Cause " + causeDTO.getRecordDTO().getRegnum());
        return causeDTO;

    }

}
