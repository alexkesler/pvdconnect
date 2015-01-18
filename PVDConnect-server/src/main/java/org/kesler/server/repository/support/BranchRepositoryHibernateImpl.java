package org.kesler.server.repository.support;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Record;
import org.kesler.server.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
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
        log.info("Getting all branches");
        Collection<Branch> branches;
        try {
            branches = this.sessionFactory.getCurrentSession()
                    .createCriteria(Branch.class)
                    .list();
        } catch (HibernateException e) {
            log.error("Error getting Branches " + e, e);
            throw new RuntimeException("Error getting Branches",e);
        }
        return branches;
    }


    @Override
    public void updateBranch(Branch branch) {
        log.info("Update branch " + branch.getName());

        try {
            this.sessionFactory.getCurrentSession()
                    .update(branch);
        } catch (HibernateException e) {
            log.error("Error updating Branch " + e, e);
            throw new RuntimeException("Error updating Branch",e);
        }
    }

    @Override
    public void removeBranch(Branch branch) {
        log.info("Remove branch " + branch.getName());
        try {
            log.info("Removing records for branch..");
            this.sessionFactory.getCurrentSession()
                    .createQuery("delete from Record where branch=:branch")
                    .setParameter("branch",branch)
                    .executeUpdate();

            log.info("Removing checks for branch..");
            this.sessionFactory.getCurrentSession()
                    .createQuery("delete from Check where branch=:branch")
                    .setParameter("branch",branch)
                    .executeUpdate();

            log.info("Removing branch..");
            this.sessionFactory.getCurrentSession()
                    .delete(branch);
        } catch (HibernateException e) {
            log.error("Error removing Branch " + e, e);
            throw new RuntimeException("Error removing Branch",e);
        }

    }
}
