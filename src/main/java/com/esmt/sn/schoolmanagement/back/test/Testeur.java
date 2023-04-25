package com.esmt.sn.schoolmanagement.back.test;


import com.esmt.sn.schoolmanagement.back.dao.factory.ConcreteFactory;
import com.esmt.sn.schoolmanagement.back.dao.factory.HibernateFactory;
import com.esmt.sn.schoolmanagement.back.dao.impl.ObjectHibernateDaoImpl;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.models.Classe;

public class Testeur {

    public static void ajouterObject(Object entity) throws DAOException {
        ObjectHibernateDaoImpl hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getObjectHibernateDao(ObjectHibernateDaoImpl.class);

        hibernateDao.create(entity);
    }

    public static void updateObject(Object entity) throws DAOException {
        ObjectHibernateDaoImpl hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getObjectHibernateDao(ObjectHibernateDaoImpl.class);

        hibernateDao.update(entity);
    }

    public static void deleteObject(int objectId, Class<?> entityClass)
            throws DAOException {
        ObjectHibernateDaoImpl hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getObjectHibernateDao(ObjectHibernateDaoImpl.class);

        hibernateDao.delete(objectId, entityClass);
    }

    public static Object rechercherObject(int id, Class<?> entityClass) throws DAOException {
        ObjectHibernateDaoImpl hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getObjectHibernateDao(ObjectHibernateDaoImpl.class);

        Object obj = hibernateDao.read(id, entityClass);
        if (obj != null) {
            return obj;
        }
        return null;
    }

}
