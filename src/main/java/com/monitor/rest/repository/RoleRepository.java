package com.monitor.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.monitor.rest.model.Role;
import com.monitor.rest.model.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
 
    Optional<Role> findByRole(RoleEnum role);

    @Query("SELECT r FROM Role r WHERE r.role IN :roleNames")
    List<Role> findRolesByRoleEnumIn(@Param("roleNames") List<String> roleNames);

}
