package com.snce.lightcom.services;

import com.snce.lightcom.entities.AppRole;

import java.util.List;

public interface RoleService {
    AppRole addNewRole(AppRole appRole);

    List<AppRole> getAllRole();
}
