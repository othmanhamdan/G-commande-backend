package com.snce.lightcom;

import com.snce.lightcom.entities.AppRole;
import com.snce.lightcom.entities.AppUser;
import com.snce.lightcom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = true)
public class LightComApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightComApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*@Bean
    CommandLineRunner start(UserService userService){
        return args -> {
            userService.addNewUser(new AppUser(null,"hamdan","othman","x346044","user","1234","othman@gmail.com" ,new ArrayList<>() , new ArrayList<>()));
            userService.addNewUser(new AppUser(null,"hamdan","othman","x346044","admin","1234","othman@gmail.com" ,new ArrayList<>() , new ArrayList<>()));

            userService.addNewRole(new AppRole(null,"ADMIN"));
            userService.addNewRole(new AppRole(null,"USER"));
            userService.addRoleToUser("admin","ADMIN");
            userService.addRoleToUser("user","USER");
        };

        }*/
}
