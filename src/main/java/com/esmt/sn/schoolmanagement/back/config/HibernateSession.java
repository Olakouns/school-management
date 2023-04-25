package com.esmt.sn.schoolmanagement.back.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSession {

    private static SessionFactory factory = null ;
    private static Session session = null ;

    public final static Logger logger = LogManager.getLogger(HibernateSession.class);

    private HibernateSession() {
        try {

            logger.info("HibernateSession created.");
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

            factory = meta.getSessionFactoryBuilder().build();

        } catch (Exception exception) {
            logger.error("Unable to load Hibernate configuration : " + exception.getMessage());
        }
    }

    // Method Used To Create The Hibernate's SessionFactory Object
    public static Session getSession () {
        if (session == null) {
            factory = getSessionFactory ();
            session = factory.openSession();
        }
        return session;
    }

    private static SessionFactory getSessionFactory() {
        init();
        return factory;
    }

    public static void init () {
        if ( factory == null ) {
            new HibernateSession();
            logger.info("HibernateSession created.");
        }
    }

    public static void closeSession() {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public static void closeSessionFactory() {
        if ( factory != null ) {
            closeSession ();
            factory.close();
            factory = null;
        }
    }
}