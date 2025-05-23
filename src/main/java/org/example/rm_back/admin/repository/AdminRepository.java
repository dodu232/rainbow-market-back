package org.example.rm_back.admin.repository;

import java.util.Optional;
import org.example.rm_back.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Boolean existsByAccount(String account);

    Optional<Admin> findByAccount(String account);
}
