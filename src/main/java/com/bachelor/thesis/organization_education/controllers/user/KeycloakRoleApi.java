package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.services.interfaces.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class KeycloakRoleApi {
    private final RoleService roleService;

    @PutMapping("/assign-role/user/{userId}")
    public ResponseEntity<?>  assignRole(@PathVariable String userId, @RequestParam String roleName){
        roleService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
