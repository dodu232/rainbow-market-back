package org.example.rm_back.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.dto.AdminRequestDto;
import org.example.rm_back.admin.dto.AdminResponseDto;
import org.example.rm_back.admin.dto.AdminResponseDto.Add;
import org.example.rm_back.admin.entity.Admin;
import org.example.rm_back.admin.entity.AdminRole;
import org.example.rm_back.admin.repository.AdminRepository;
import org.example.rm_back.common.BCryptEncryptor;
import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;
    private final BCryptEncryptor encryptor;

    public AdminResponseDto.Add addAdmin(AdminRequestDto.Add dto) {

        if (repository.existsByAccount(dto.getAccount())) {
            throw new ApiException("중복된 account 입니다.", ErrorType.INVALID_PARAMETER,
                HttpStatus.BAD_REQUEST);
        }

        String roles = AdminRole.toAdminRole(dto.getRole());
        String hashedPassword = encryptor.encrypt(dto.getPassword());

        Admin admin = new Admin(dto.getAccount(), hashedPassword, roles);

        Admin saveAdmin = repository.save(admin);
        return new Add(saveAdmin.getId(), saveAdmin.getAccount());
    }

    public Admin getAdminByAccount(String account) {
        return repository.findByAccount(account)
            .orElseThrow(() -> new ApiException("존재하지 않는 account 입니다.", ErrorType.INVALID_PARAMETER,
                HttpStatus.BAD_REQUEST));
    }
}
