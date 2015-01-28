package org.kesler.server.repository.support;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.kesler.server.domain.Branch;
import org.kesler.server.domain.Record;
import org.kesler.server.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;


@Repository
public class RecordRepositoryHibernateImpl implements RecordRepository {
    private static final Logger log = LoggerFactory.getLogger(RecordRepositoryHibernateImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveRecords(Collection<Record> records) {
        log.info("Saving "+ records.size() + " records");
        log.debug("Find first record date");
        Date firstDate=null;
        Branch branch=null;
        for(Record record:records) {
            if (firstDate==null) {
                firstDate = record.getRegdate();
                branch = record.getBranch();
                continue;
            }
            if (firstDate.after(record.getRegdate())) {
                firstDate = record.getRegdate();
            }
        }

        log.debug("FirstDate is " + firstDate);

        if (firstDate != null) {
            log.debug("Getting stored records in and after " + firstDate);
            Collection<Record> storedRecords;
            try {
                storedRecords = sessionFactory.getCurrentSession()
                        .createCriteria(Record.class)
                        .add(Restrictions.eq("branch",branch))
                        .add(Restrictions.ge("regdate", firstDate))
                        .list();
            } catch (HibernateException e) {
                log.error("Error getting Stored records " + e, e);
                throw new RuntimeException("Error getting stored records",e);
            }

            for (Record record : storedRecords) {
                records.remove(record);
            }
        }

        log.info("Save " + records.size() + " new records");

        try {
            Session session = sessionFactory.getCurrentSession();
            for (Record record : records) {
                session.save(record);
            }
        } catch (HibernateException e) {
            log.error("Error saving records " + e, e);
            throw new RuntimeException("Error saving records",e);
        }

    }

    @Override
    public Collection<Record> getRecordsByRegnum(String regnum) {
        log.info("Get records by code: " + regnum);
        Collection<Record> records;
        try {
            records = this.sessionFactory.getCurrentSession()
                    .createCriteria(Record.class)
                    .add(Restrictions.like("regnum", "%" + regnum + "%"))
                    .setMaxResults(500)
                    .list();
        } catch (HibernateException e) {
            log.error("Error getting records " + e, e);
            throw new RuntimeException("Error getting records",e);
        }
        return records;
    }

    @Override
    public Integer getRecordsCount(Branch branch) {
        log.info("Getting records count for branch: "+branch.getName());
        Long count;
        try {
            count = (Long) (sessionFactory.getCurrentSession()
                    .createQuery("select count(*) from Record as record where record.branch=:branch")
                    .setParameter("branch", branch)
                    .iterate().next());
        } catch (HibernateException e) {
            log.error("Error getting records count" + e, e);
            throw new RuntimeException("Error getting records count",e);
        }
        return count.intValue();
    }
}
