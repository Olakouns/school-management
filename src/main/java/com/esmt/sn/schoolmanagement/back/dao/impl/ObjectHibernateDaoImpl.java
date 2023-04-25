package com.esmt.sn.schoolmanagement.back.dao.impl;


import com.esmt.sn.schoolmanagement.back.config.HibernateSession;
import com.esmt.sn.schoolmanagement.back.dao.IDao;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class ObjectHibernateDaoImpl implements IDao<Object> {

    public final static Logger logger = LogManager.getLogger(ObjectHibernateDaoImpl.class);

    private static Session session = null;
    private static Transaction transaction = null;

    private static ObjectHibernateDaoImpl daoInstance = null;

    private ObjectHibernateDaoImpl() {
    }

    public static ObjectHibernateDaoImpl getCurrentInstance() {
        if (daoInstance == null) daoInstance = new ObjectHibernateDaoImpl();
        return daoInstance;
    }

    @Override
    public <T> T create(T entity) throws DAOException {
        try {
            session = HibernateSession.getSession();

            // Creating Transaction Object
            transaction = session.beginTransaction();

            logger.info("Begin transaction.");
            session.persist(entity);

            // Transaction Is Committed To Database
            transaction.commit();
            logger.info("Record is Successfully added.");

            return entity;

        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }
    }

    @Override
    public <T> T read(int id, Class<T> entityClass) throws DAOException {
        T entity = null;
        try {
            session = HibernateSession.getSession();
            entity = session.find(entityClass, id);
            if (entity != null) logger.info("Record Successfully read.");
            else logger.info("Record not found.");
        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> list(Class<T> entityClass) throws DAOException {
        List<T> entities = null;
        try {
            session = HibernateSession.getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cr = (CriteriaQuery<T>) cb.createQuery(entityClass);
            Root<T> root = (Root<T>) cr.from(entityClass);
            cr.select(root);

            Query<T> query = session.createQuery(cr);
            entities = query.getResultList();

        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }

        return entities;
    }

    @Override
    public <T> T update(T entity) throws DAOException {
        try {
            session = HibernateSession.getSession();
            transaction = session.beginTransaction();
            logger.info("Begin transaction.");
            session.persist(entity);
            transaction.commit();
            logger.info("Record is Successfully updated.");
            return entity;
        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }
    }

    @Override
    public void delete(int id, Class<?> entityClass) throws DAOException {
        try {
            session = HibernateSession.getSession();
            transaction = session.beginTransaction();
            logger.info("Begin transaction.");
            Object object = session.find(entityClass, id);
            if (object != null) {
                session.remove(object);
                logger.info("Record is Successfully deleted.");
            } else {
                logger.info("Record not found. Nothing to be done.");
            }
            transaction.commit();
        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }
    }

    @Override
    public String getSource() {
        return "ObjectHibernateDaoImpl";
    }

    public static void flush() {
        try {
            session = HibernateSession.getSession();
            // Creating Transaction Object
            transaction = session.beginTransaction();
            session.flush();
            logger.info("Session flushed.");
        } catch (Exception exception) {
        }
    }
}