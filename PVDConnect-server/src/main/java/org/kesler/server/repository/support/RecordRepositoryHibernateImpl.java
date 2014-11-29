package org.kesler.server.repository.support;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.kesler.server.domain.Record;
import org.kesler.server.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by alex on 29.11.14.
 */
@Repository
public class RecordRepositoryHibernateImpl implements RecordRepository {
    private static final Logger log = LoggerFactory.getLogger(RecordRepositoryHibernateImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Collection<Record> getRecordsByRegnum(String regnum) {
        log.info("Get records by code: " + regnum);
        return this.sessionFactory.getCurrentSession()
                .createCriteria(Record.class)
                .add(Restrictions.like("regnum","%"+regnum+"%"))
                .list();
    }
}
