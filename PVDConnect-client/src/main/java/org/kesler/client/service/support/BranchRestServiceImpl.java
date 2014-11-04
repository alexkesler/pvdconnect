package org.kesler.client.service.support;

import org.kesler.client.domain.Branch;
import org.kesler.client.service.BranchService;
import org.kesler.client.transform.BranchTransform;
import org.kesler.common.BranchDTO;
import org.kesler.common.BranchesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Служба для отправки REST запросов при работе с перечнем филиалов
 */
@Service
public class BranchRestServiceImpl implements BranchService{

    private static final Logger log = LoggerFactory.getLogger(BranchRestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private String serverUrl;

    public BranchRestServiceImpl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public Collection<Branch> getAllBranches() {
        log.info("Getting all branches");
        BranchesDTO branchesDTO = restTemplate.getForObject(serverUrl + "/branches", BranchesDTO.class);
        return BranchTransform.transform(branchesDTO.getBranchDTOs());
    }

    @Override
    public void addBranch(Branch branch) {
        log.info("Add new branch: " + branch.getName());
        BranchDTO branchDTO = BranchTransform.transform(branch);
        restTemplate.postForObject(serverUrl + "/branches/add", branchDTO, BranchDTO.class);
    }

    @Override
    public void removeBranch(Branch branch) {
        log.info("Remove branch: " + branch.getName());
        BranchDTO branchDTO = BranchTransform.transform(branch);
        restTemplate.postForObject(serverUrl + "/branches/remove", branchDTO, BranchDTO.class);
    }

    @Override
    public void updateBranch(Branch branch) {
        log.info("Update branch: " + branch.getName());
        BranchDTO branchDTO = BranchTransform.transform(branch);
        restTemplate.postForObject(serverUrl + "/branches/update", branchDTO, BranchDTO.class);
    }
}
