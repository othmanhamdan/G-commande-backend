package com.snce.lightcom.services;


import com.snce.lightcom.entities.AppRole;
import com.snce.lightcom.entities.AppUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    AppUser addNewUser(AppUser appUser);

    void addRoleToUser(String userName, String nameRole);
    AppUser laodUserByUserName(String userName);

    Page<AppUser> listUsers(int page, int size);
}