package org.kesler.server.repository.support;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Check;
import org.kesler.server.repository.CheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class CheckRepositoryHibernateImpl implements CheckRepository {
    private static final Logger log = LoggerFactory.getLogger(CheckRepositoryHibernateImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveCheck(Check check) {
        log.info("Adding check for branch " + check.getBranchName());
        try {
            this.sessionFactory.getCurrentSession()
                    .saveOrUpdate(check);
        } catch (Exception ex) {
            log.error("Error saving Check " + ex, ex);
            throw new RuntimeException("Error saving Check",ex);
        }

    }


    @Override
    public Collection<Check> getAllChecks() {
        log.info("Getting all checks");
        Collection<Check> checks = new ArrayList<>();
        try {
            checks = this.sessionFactory.getCurrentSession()
                    .createCriteria(Check.class)
                    .list();
        } catch (HibernateException e) {
            log.error("Error getting Checks " + e, e);
            throw new RuntimeException("Error getting Checks",e);
        }
        return checks;
    }

    @Override
    public Check getCheckByBranch(Branch branch) {
        log.info("Getting check for branch " + branch.getName());
        Collection<Check> checks = new ArrayList<>();
        try {
            checks = this.sessionFactory.getCurrentSession()
                    .createCriteria(Check.class)
                    .add(Restrictions.eq("branch", branch))
                    .list();
        } catch (HibernateException e) {
            log.error("Error getting Checks " + e, e);
            throw new RuntimeException("Error getting Checks",e);
        }
        if (checks.size()==0) return null;
        else return checks.iterator().next();
    }
}
