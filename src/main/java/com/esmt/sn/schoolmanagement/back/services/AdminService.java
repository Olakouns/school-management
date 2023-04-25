package com.esmt.sn.schoolmanagement.back.services;

import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.models.*;
import com.esmt.sn.schoolmanagement.payload.ApiResponse;

import java.util.List;

public interface AdminService {

    List<Privilege> getPrivileges() throws DAOException;
    Privilege addPrivilege(Privilege privilege) throws DAOException;
    Role addRole(Role role) throws DAOException;
    List<Role> getRoles() throws DAOException;
    int countRole() throws DAOException;

    List<Utilisateur> getUsers() throws DAOException;
    Utilisateur addUser(Utilisateur utilisateur) throws DAOException;


    //    SECTION PART
    List<Section> getSections() throws DAOException;

    Section addSection(Section section) throws DAOException;

    Section getSection(long section_id) throws DAOException;

    Section updateSection(long sectionId, Section section);

    ApiResponse deleteSection(long sectionId);

    //   CLASSE PART

    List<Classe> getClassList(String section)  throws DAOException;;

    Classe getClasse(long classId);

    Classe addClass(long sectionId, Classe classe) throws DAOException;

    Classe updateClass(Classe classe);

    ApiResponse deleteClass(long classId);

//    RUBRIQUE PART

    List<Rubrique> getRubriques(String search);

    List<Rubrique> getRubriques(long classId, long sectionId);

    Rubrique addRubrique(long classId, long sectionId, Rubrique rubrique);

    Rubrique getRubrique(long rubriqueId);

    Rubrique updateRubrique(long classId, long sectionId, Rubrique rubrique);

    ApiResponse deleteRubrique(int rubriqueId);

}
