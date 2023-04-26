package com.esmt.sn.schoolmanagement.back.services;

import com.esmt.sn.schoolmanagement.back.config.HibernateSession;
import com.esmt.sn.schoolmanagement.back.dao.factory.ConcreteFactory;
import com.esmt.sn.schoolmanagement.back.dao.factory.HibernateFactory;
import com.esmt.sn.schoolmanagement.back.dao.impl.ObjectHibernateDaoImpl;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.models.*;
import com.esmt.sn.schoolmanagement.payload.ApiResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


public class AdminServiceImpl implements AdminService {
    private static final Session session = HibernateSession.getSession();
    private static final Transaction transaction = null;
    private static final ObjectHibernateDaoImpl daoInstance = null;
    private ObjectHibernateDaoImpl hibernateDao = ConcreteFactory.getFactory(HibernateFactory.class).getObjectHibernateDao(ObjectHibernateDaoImpl.class);

    private static AdminServiceImpl adminService;

    private AdminServiceImpl() {
    }

    public static AdminServiceImpl getInstance() {
        return (adminService == null) ? new AdminServiceImpl() : adminService;
    }

    @Override
    public List<Privilege> getPrivileges() throws DAOException {
        return hibernateDao.list(Privilege.class);
    }

    @Override
    public Privilege addPrivilege(Privilege privilege) throws DAOException {
        return hibernateDao.create(privilege);
    }

    @Override
    public Role addRole(Role role) throws DAOException {
        return hibernateDao.create(role);
    }

    @Override
    public List<Role> getRoles() throws DAOException {
        return hibernateDao.list(Role.class);
    }

    @Override
    public int countRole() throws DAOException {
        return hibernateDao.list(Role.class).size();
    }

    @Override
    public List<Utilisateur> getUsers() throws DAOException {
        return hibernateDao.list(Utilisateur.class);
    }

    @Override
    public Utilisateur addUser(Utilisateur utilisateur) throws DAOException {
        return hibernateDao.create(utilisateur);
    }

    @Override
    public List<Section> getSections() throws DAOException {
        return hibernateDao.list(Section.class);
    }

    @Override
    public Section addSection(Section section) throws DAOException {
        return hibernateDao.create(section);
    }

    @Override
    public Section getSection(long section_id) throws DAOException {
        return hibernateDao.read((int) section_id, Section.class);
    }

    @Override
    public Section updateSection(long sectionId, Section section) {
        return null;
    }

    @Override
    public ApiResponse deleteSection(long sectionId) {
        return null;
    }

    @Override
    public List<Classe> getClassList(String section) throws DAOException {
        List<Classe> classes;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Classe> cr = cb.createQuery(Classe.class);
        Root<Classe> root = cr.from(Classe.class);
        cr.select(root).where(cb.equal(root.get("section").get("nom"), section));
        Query<Classe> query = session.createQuery(cr);
        classes = query.getResultList();
        return classes;
    }

    @Override
    public Classe getClasse(long classId) {
        return null;
    }

    @Override
    public Classe addClass(long sectionId, Classe classe) throws DAOException {
        Section section = getSection(sectionId);
        classe.setSection(section);
        return hibernateDao.create(classe);
    }

    @Override
    public Classe updateClass(Classe classe) {
        return null;
    }

    @Override
    public ApiResponse deleteClass(long classId) {
        return null;
    }

    @Override
    public List<Rubrique> getRubriques(String search) {
        return null;
    }

    @Override
    public List<Rubrique> getRubriques(long classId, long sectionId) {
        return null;
    }

    @Override
    public Rubrique addRubrique(long classId, long sectionId, Rubrique rubrique) {
        return null;
    }

    @Override
    public Rubrique getRubrique(long rubriqueId) {
        return null;
    }

    @Override
    public Rubrique updateRubrique(long classId, long sectionId, Rubrique rubrique) {
        return null;
    }

    @Override
    public ApiResponse deleteRubrique(int rubriqueId) {
        return null;
    }
}
