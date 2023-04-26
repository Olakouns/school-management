package com.esmt.sn.schoolmanagement.back.config;

import com.esmt.sn.schoolmanagement.back.dao.impl.ObjectHibernateDaoImpl;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.back.services.AuthServiceImpl;
import com.esmt.sn.schoolmanagement.models.Privilege;
import com.esmt.sn.schoolmanagement.models.Role;
import com.esmt.sn.schoolmanagement.models.Utilisateur;
import com.esmt.sn.schoolmanagement.models.enums.TypePrivilege;
import com.esmt.sn.schoolmanagement.payload.ApiResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataLoad {
    private static final Session session = HibernateSession.getSession();
    private static final Transaction transaction = null;
    private static final ObjectHibernateDaoImpl daoInstance = ObjectHibernateDaoImpl.getCurrentInstance();
    private static final AuthServiceImpl authServiceImpl = AuthServiceImpl.getInstance();
    private static final AdminServiceImpl adminService = AdminServiceImpl.getInstance();

    public static void loadAllData() {
        new Thread(() -> {
            try {
                loadRole();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void loadData() {
//        CREATE SUPER AMIN HERE
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("Administrateur");
            utilisateur.setLogin("supperadmin@esmt.sn");
            utilisateur.setEmail("supperadmin@esmt.sn");
            utilisateur.setPassword("mot2P@ss");
            daoInstance.create(utilisateur);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private static void loadRole() throws DAOException {

            int roleCount = adminService.countRole();

            if (roleCount != 0) {
                return;
            }

            List<Privilege> privilegeAdmin = new ArrayList<>();

            for (int i = 0; i < TypePrivilege.values().length; i++) {
                String description = TypePrivilege.values()[i].toString().replace("_", " ");
                String category = description.split(" ")[description.split(" ").length - 1];
                privilegeAdmin.add(adminService.addPrivilege(new Privilege(category, TypePrivilege.values()[i], description)));
            }

            Role roleAdmin = new Role("Super Admin");
            roleAdmin.setPrivileges(privilegeAdmin);
            roleAdmin = adminService.addRole(roleAdmin);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("Administrateur");
            utilisateur.setLogin("supperadmin@esmt.sn");
            utilisateur.setEmail("supperadmin@esmt.sn");
            utilisateur.setPassword("mot2P@ss");
            utilisateur.setRoles(Collections.singletonList(roleAdmin));
            daoInstance.create(utilisateur);


    }
}
