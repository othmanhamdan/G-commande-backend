package com.snce.lightcom.services.Imp;

import com.snce.lightcom.entities.AppRole;
import com.snce.lightcom.repository.AppRoleRepository;
import com.snce.lightcom.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final AppRoleRepository appRoleRepository;
    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public List<AppRole> getAllRole() {
        return appRoleRepository.findAll();
    }

}
