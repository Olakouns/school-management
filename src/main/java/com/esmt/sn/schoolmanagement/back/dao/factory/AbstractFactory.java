package com.esmt.sn.schoolmanagement.back.dao.factory;

import com.esmt.sn.schoolmanagement.back.dao.impl.ObjectHibernateDaoImpl;

public abstract class AbstractFactory {
    public ObjectHibernateDaoImpl getObjectHibernateDao (Class<? extends ObjectHibernateDaoImpl> typeEntity) {
        return null;
    }
}