package org.example.rm_back.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.dto.AuthRequestDto;
import org.example.rm_back.admin.dto.AuthResponseDto;
import org.example.rm_back.admin.dto.AuthResponseDto.Add;
import org.example.rm_back.admin.entity.Admin;
import org.example.rm_back.admin.entity.AdminRole;
import org.example.rm_back.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;

    public AuthResponseDto.Add addAdmin(AuthRequestDto.Add dto){
        String passwordEncoder = "1212";
        String roles = AdminRole.toAdminRole(dto.getRole());

        Admin admin = new Admin(dto.getAccount(), passwordEncoder, roles);
        Admin saveAdmin = repository.save(admin);
        return new Add(saveAdmin.getId(), saveAdmin.getAccount());
    }
}
