package com.esmt.sn.schoolmanagement.back.services;

import com.esmt.sn.schoolmanagement.back.config.HibernateSession;
import com.esmt.sn.schoolmanagement.back.dao.impl.ObjectHibernateDaoImpl;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.models.Utilisateur;
import com.esmt.sn.schoolmanagement.payload.ApiResponse;
import com.esmt.sn.schoolmanagement.utils.Constants;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AuthServiceImpl implements AuthService {

    private static final Session session = HibernateSession.getSession();
    private static final Transaction transaction = null;
    private static final ObjectHibernateDaoImpl daoInstance = null;

    private static AuthServiceImpl authService;

    private AuthServiceImpl() {
    }

    public static AuthServiceImpl getInstance() {
        return (authService == null) ? new AuthServiceImpl() : authService;
    }

    @Override
    public ApiResponse checkUser(String username) throws DAOException {
        Utilisateur utilisateur;
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Utilisateur> cr = cb.createQuery(Utilisateur.class);
            Root<Utilisateur> root = cr.from(Utilisateur.class);
            cr.select(root).where(cb.equal(root.get("login"), username));
            Query<Utilisateur> query = session.createQuery(cr);
            utilisateur = query.uniqueResult();
        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }
        return new ApiResponse((utilisateur != null), (utilisateur != null) ? "user found" : "Utilisateur non trouve");
    }

    @Override
    public ApiResponse loginUser(String username, String password) throws DAOException {
        Utilisateur utilisateur;
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Utilisateur> cr = cb.createQuery(Utilisateur.class);
            Root<Utilisateur> root = cr.from(Utilisateur.class);
            cr.select(root).where(cb.and(cb.equal(root.get("login"), username), cb.equal(root.get("password"), password)));
            Query<Utilisateur> query = session.createQuery(cr);
            utilisateur = query.uniqueResult();
            Constants.utilisateur = utilisateur;
        } catch (Exception exception) {
            throw new DAOException("ERROR:" + exception.getClass() + ":" + exception.getMessage());
        }
        return new ApiResponse((utilisateur != null), (utilisateur != null) ? "user found" : "Utilisateur non trouve");

    }

    @Override
    public Utilisateur getCurrentUser() {
        return null;
    }
}
