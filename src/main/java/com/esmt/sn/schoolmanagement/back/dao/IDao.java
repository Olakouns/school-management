package com.esmt.sn.schoolmanagement.back.dao;


import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;

import java.util.List;

public interface IDao<T> {

	/**
	 * @param entity
	 * @throws DAOException
	 */
	public <T> T create (T entity) throws DAOException;

	/**
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	@Deprecated
	default public T read (int id) throws DAOException {
		return null;
	}

	/**
	 * @return
	 * @throws DAOException
	 */
	@Deprecated
	default public List<T> list () throws DAOException {
		return null;
	}

	/**
	 * @param entity
	 * @throws DAOException
	 */
	public  <T> T update (T entity) throws DAOException;

	/**
	 * @param id
	 * @throws DAOException
	 *
	 */
	@Deprecated
	default public void delete (int id) throws DAOException {}

	/**
	 * @return
	 */
	public String getSource ();

	/**
	 * @param id
	 * @param entityClass
	 * @throws DAOException
	 */
	default public void delete(int id, Class<?> entityClass) throws DAOException {}

	/**
	 * @param entityClass
	 * @return
	 * @throws DAOException
	 */
	default public <T> List<T> list(Class<T> entityClass) throws DAOException {
		return null;
	}

	/**
	 * @param id
	 * @param entityClass
	 * @return
	 * @throws DAOException
	 */
	default public <T> T read(int id, Class<T> entityClass) throws DAOException {
		return null;
	}
}