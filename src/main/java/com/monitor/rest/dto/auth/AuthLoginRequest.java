package com.monitor.rest.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequest {
    
    @Email(message = "El email ingresado no es válido")
    @NotBlank(message = "Es necesario un email")
    private String email;

    @NotBlank(message = "La contaseña es requerida")
    @Pattern(
            message = "La contaseña no es válida", 
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"
    )
    private String password;
}
