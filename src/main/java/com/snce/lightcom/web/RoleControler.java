package com.snce.lightcom.web;

import com.snce.lightcom.entities.AppRole;
import com.snce.lightcom.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleControler {
    private RoleService roleService;

    @PostMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole save(@RequestBody AppRole appRole){
        return roleService.addNewRole(appRole);
    }
    @GetMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<AppRole> getAll(){
        return roleService.getAllRole();
    }

}
