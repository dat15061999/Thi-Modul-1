package com.example.demo1.service;


import com.example.demo1.dao.RoleDAO;
import com.example.demo1.model.Role;

import java.util.List;

public class RoleService {
    private final RoleDAO roleDAO;

    public RoleService() {
        roleDAO = new RoleDAO();
    }

    public List<Role> getAllRoles(){
        return roleDAO.findAll();
    }

    public Role getRole(int id){
        return roleDAO.findById(id);
    }
}
