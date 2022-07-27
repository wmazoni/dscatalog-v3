package com.devsuperior.dscatalog.factories;

import com.devsuperior.dscatalog.dto.RoleDto;
import com.devsuperior.dscatalog.entities.Role;

public class RoleFactory {

    public static Role createRole() {
        return new Role(1L, "ROLE_DEV");
    }

    public static RoleDto createRoleDto() {
        Role role = createRole();
        return new RoleDto(role);
    }
}
