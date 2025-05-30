package org.example.rm_back.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.dto.AdminRequestDto;
import org.example.rm_back.admin.dto.AdminResponseDto;
import org.example.rm_back.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping
    public ResponseEntity<AdminResponseDto.Add> addAdmin(
        @Valid @RequestBody AdminRequestDto.Add dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAdmin(dto));
    }
}
