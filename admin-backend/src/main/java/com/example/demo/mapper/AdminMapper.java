package com.example.demo.mapper;

import com.example.demo.dto.AdminDto;
import com.example.demo.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDto entityToAdminDto(Admin a) {
        AdminDto adminDto = new AdminDto();
        adminDto.setId(a.getId());
        adminDto.setUsername(a.getUsername());

        return adminDto;
    }

}
