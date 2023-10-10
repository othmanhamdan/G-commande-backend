package com.snce.lightcom.repository;

import com.snce.lightcom.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangerRepository extends JpaRepository<AppUser, Long> {
}
