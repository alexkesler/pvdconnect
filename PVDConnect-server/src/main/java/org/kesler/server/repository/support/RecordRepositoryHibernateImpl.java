package org.kesler.server.repository.support;

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
            Collection<Record> storedRecords = sessionFactory.getCurrentSession()
                    .createCriteria(Record.class)
                    .add(Restrictions.eq("branch",branch))
                    .add(Restrictions.ge("regdate", firstDate))
                    .list();
            for (Record record : storedRecords) {
                records.remove(record);
            }
        }


        Session session = sessionFactory.getCurrentSession();

        log.info("Save " + records.size() + " new records");
        for (Record record : records) {
            session.save(record);
        }

    }

    @Override
    public Collection<Record> getRecordsByRegnum(String regnum) {
        log.info("Get records by code: " + regnum);
        return this.sessionFactory.getCurrentSession()
                .createCriteria(Record.class)
                .add(Restrictions.like("regnum","%"+regnum+"%"))
                .list();
    }

    @Override
    public Integer getRecordsCount(Branch branch) {
        log.info("Getting records count for branch: "+branch.getName());
        Long count = (Long) (sessionFactory.getCurrentSession()
                .createQuery("select count(*) from Record as record where record.branch=:branch")
                .setParameter("branch",branch)
                .iterate().next());
        return count.intValue();
    }
}
