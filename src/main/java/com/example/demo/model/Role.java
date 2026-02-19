package com.example.demo.model;

public enum Role {
    ROLE_USER,    // usuario normal
    ROLE_ADMIN    // administrador
}
// Spring Security espera el prefijo "ROLE_" para hasRole("ADMIN")