package com.monitor.rest.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.monitor.rest.model.Permission;
import com.monitor.rest.model.Role;
import com.monitor.rest.model.enums.RoleEnum;
import com.monitor.rest.repository.PermissionRepository;
import com.monitor.rest.repository.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InitializationService implements CommandLineRunner {
    
    private final PermissionRepository permissionRepository;

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        
        Set<Permission> defaultPermissions = createDefaultPermissions();

        createDefaultRoles(defaultPermissions);
    }

    private Set<Permission> createDefaultPermissions() {
        String[] permissions = {"READ", "CREATE", "UPDATE", "DELETE"};

        return Arrays.stream(permissions)
            .map(name -> permissionRepository.findByName(name)
                .orElseGet(() -> {
                    Permission permission = Permission.builder()
                        .name(name)
                        .build();
                    return permissionRepository.save(permission);  
                }))
            .collect(Collectors.toSet());
    }

    private void createDefaultRoles(Set<Permission> permissions) {
        Map<RoleEnum, Set<Permission>> rolePermissionsMap = new HashMap<>();

        rolePermissionsMap.put(RoleEnum.ADMIN, permissions);

        Set<Permission> userPermissions = permissions.stream()
            .filter(p -> Arrays.asList("READ", "CREATE").contains(p.getName()))
            .collect(Collectors.toSet());
        rolePermissionsMap.put(RoleEnum.USER, userPermissions);

        Set<Permission> invitedPermissions = permissions.stream()
            .filter(p -> p.getName().equals("READ"))
            .collect(Collectors.toSet());
        rolePermissionsMap.put(RoleEnum.INVITED, invitedPermissions);

        Arrays.stream(RoleEnum.values()).forEach(roleEnum -> {
            Role role = roleRepository.findByRole(roleEnum)
                .orElseGet(() -> Role.builder().role(roleEnum).build());
            
            role.setPermission(rolePermissionsMap.get(roleEnum));
            roleRepository.save(role);
        });
    }
    
}
