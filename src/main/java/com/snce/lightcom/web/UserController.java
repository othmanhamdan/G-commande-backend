package com.snce.lightcom.web;


import com.snce.lightcom.entities.AppUser;
import com.snce.lightcom.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Page<AppUser> getAllUsers(@RequestParam int page,@RequestParam int size){
        return userService.listUsers(page,size);
    }
    @PostMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUser save(@RequestBody AppUser appUser){
        return userService.addNewUser(appUser);
    }
    @PostMapping("/addRoleToUsers")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUsers(@RequestParam String userName, @RequestParam String roleName){
        userService.addRoleToUser(userName,roleName);
    }
    @GetMapping("/{userName}")
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public AppUser getUserByUserName(@PathVariable("userName") String userName){
        return userService.laodUserByUserName(userName);
    }


    //@PostAuthorize("hasAuthority('ADMIN')")
}
