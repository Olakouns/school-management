package com.esmt.sn.schoolmanagement.back.dao.factory;
public class ConcreteFactory {

	private ConcreteFactory() {}

	public static AbstractFactory getFactory (Class<? extends AbstractFactory> factory) {

		if (factory == null) {
			return null;
		}
		else if (factory == HibernateFactory.class) {
			return new HibernateFactory();
		}

		return null;
	}
}