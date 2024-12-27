package com.monitor.rest.dto.auth;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthCreateRoleRequest {
    
    @Size(max = 3, message = "El usuario no puede tener mas de 3 roles")
    private List<String> roleListName;
}
