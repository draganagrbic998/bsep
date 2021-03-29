package com.example.demo.mapper;

import com.example.demo.dto.AdminDTO;
import com.example.demo.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDTO entityToAdminDto(Admin a) {
        AdminDTO adminDto = new AdminDTO();
        adminDto.setId(a.getId());
        adminDto.setUsername(a.getUsername());

        return adminDto;
    }

}
