package org.kesler.server.repository.support;

import org.hibernate.SessionFactory;
import org.kesler.server.domain.Branch;
import org.kesler.server.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BranchRepositoryHibernateImpl implements BranchRepository {

    private static final Logger log = LoggerFactory.getLogger(BranchRepositoryHibernateImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addBranch(Branch branch) {
        log.info("Adding branch " + branch.getName());
        try {
            this.sessionFactory.getCurrentSession()
                    .saveOrUpdate(branch);
        } catch (Exception ex) {
            log.error("Error saving Branch " + ex, ex);
            throw new RuntimeException("Error saving Branch",ex);
        }

    }


    @Override
    public Collection<Branch> getAllBranches() {
        return this.sessionFactory.getCurrentSession()
                .createCriteria(Branch.class)
                .list();
    }

    @Override
    public Branch getBranchById(Long id) {
        return null;
    }

    @Override
    public void updateBranch(Branch branch) {
        this.sessionFactory.getCurrentSession()
                .update(branch);
    }

    @Override
    public void removeBranch(Branch branch) {
        this.sessionFactory.getCurrentSession()
                .delete(branch);

    }
}
