package com.esmt.sn.schoolmanagement.back.services;

import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.models.Utilisateur;
import com.esmt.sn.schoolmanagement.payload.ApiResponse;

public interface AuthService {
    ApiResponse checkUser(String username) throws DAOException;
    ApiResponse loginUser(String username, String password) throws DAOException;
    Utilisateur getCurrentUser();
}
