package org.kesler.server.controller;

import org.kesler.common.RecordsDTO;
import org.kesler.server.domain.Check;
import org.kesler.common.RecordDTO;
import org.kesler.server.domain.Record;
import org.kesler.server.repository.CheckRepository;
import org.kesler.server.transform.RecordTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordsController {

    private final CheckRepository checkRepository;

    @Autowired
    public RecordsController(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    RecordsDTO getRecordsByCode(@RequestParam String code) {
        List<RecordDTO> recordDTOList = new ArrayList<RecordDTO>();
        Collection<Check> checks = checkRepository.getAllChecks();
        for (Check check:checks) {
            for(Record record :check.getRecords()) {
                if (record.getRegnum().contains(code))
                    recordDTOList.add(RecordTransform.transform(record));
            }
        }

        return new RecordsDTO(recordDTOList);
    }

}
