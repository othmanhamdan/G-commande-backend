package com.snce.lightcom.repository;


import com.snce.lightcom.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByNameRole(String nameRole);


}
