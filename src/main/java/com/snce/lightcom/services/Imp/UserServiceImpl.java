package com.snce.lightcom.services.Imp;

import com.snce.lightcom.entities.AppRole;
import com.snce.lightcom.entities.AppUser;
import com.snce.lightcom.repository.AppRoleRepository;
import com.snce.lightcom.repository.AppUserRepository;
import com.snce.lightcom.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }



    @Override
    public void addRoleToUser(String userName, String nameRole) {
        AppUser appUser = appUserRepository.findByUserName(userName);
        AppRole appRole = appRoleRepository.findByNameRole(nameRole);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser laodUserByUserName(String userName) {
        return appUserRepository.findByUserName(userName);
    }

    @Override
    public Page<AppUser> listUsers(int page, int size) {
        return appUserRepository.findAll(PageRequest.of(page,size));
    }
}


