package org.kesler.server.controller;

import org.kesler.common.BranchDTO;
import org.kesler.common.BranchesDTO;
import org.kesler.server.repository.BranchRepository;
import org.kesler.server.transform.BranchTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/branches")
public class BranchController {
    private static final Logger log = LoggerFactory.getLogger(BranchController.class);

    private final BranchRepository branchRepository;

    @Autowired
    public BranchController(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    BranchesDTO getAllBranches() {
        log.info("Retriving all branches");
        return new BranchesDTO(BranchTransform.transform(branchRepository.getAllBranches()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBranch(@RequestBody BranchDTO branchDTO) {
        log.info("Adding new Branch " + branchDTO.getName());
        branchRepository.addBranch(BranchTransform.transform(branchDTO));
        return "redirect:/branches";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateBranch(@RequestBody BranchDTO branchDTO) {
        log.info("Updating Branch " + branchDTO.getName());
        branchRepository.updateBranch(BranchTransform.transform(branchDTO));
        return "redirect:/branches";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeBranch(@RequestBody BranchDTO branchDTO) {
        log.info("Removing Branch " + branchDTO.getName());
        branchRepository.removeBranch(BranchTransform.transform(branchDTO));
        return "redirect:/branches";
    }
}
