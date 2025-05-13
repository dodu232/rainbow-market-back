package org.example.rm_back.admin.repository;

import java.util.Optional;
import org.example.rm_back.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Boolean existsByAccount(String account);

    Optional<Admin> findByAccount(String account);
}
