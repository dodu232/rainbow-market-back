package org.example.rm_back.admin.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.example.rm_back.admin.dto.AdminRequestDto;
import org.example.rm_back.admin.entity.Admin;
import org.example.rm_back.admin.entity.AdminRole;
import org.example.rm_back.admin.repository.AdminRepository;
import org.example.rm_back.common.BCryptEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    AdminRepository adminRepository;

    @Mock
    private BCryptEncryptor encryptor;

    @InjectMocks
    AdminService adminService;


    @Test
    void addAdmin() {
        // given
        String account = "hama";
        String[] role = {"notice_read", "notice_write"};
        String password = "admin1122";

        AdminRequestDto.Add dto = new AdminRequestDto.Add(account, role, password);
        Admin admin = new Admin(account, encryptor.encrypt(password), AdminRole.toAdminRole(role));

        given(adminRepository.save(any())).willReturn(admin);

        // when
        adminService.addAdmin(dto);

        // then
        verify(adminRepository, times(1)).save(any());
    }

}
