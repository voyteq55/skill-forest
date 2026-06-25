package com.wburda.skillforest.backend.dto;

import com.wburda.skillforest.backend.entities.enums.UserRole;
import lombok.Value;

@Value
public class AssignRoleDTO {
    UserRole role;
}
