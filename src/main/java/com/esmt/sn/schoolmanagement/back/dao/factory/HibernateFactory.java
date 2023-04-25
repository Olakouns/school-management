package com.esmt.sn.schoolmanagement.back.dao.factory;

import com.esmt.sn.schoolmanagement.back.dao.impl.ObjectHibernateDaoImpl;

public class HibernateFactory extends AbstractFactory {

    @Override
    public ObjectHibernateDaoImpl getObjectHibernateDao(Class<? extends ObjectHibernateDaoImpl> typeClass) {
        if (typeClass == ObjectHibernateDaoImpl.class)
            return  ObjectHibernateDaoImpl.getCurrentInstance();

        return null;
    }
}