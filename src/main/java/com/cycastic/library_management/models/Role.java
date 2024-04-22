package com.cycastic.library_management.models;

import java.util.Locale;
import java.util.Objects;

public enum Role {
    COMMON,
    SUPERUSER;
    public static Role toRole(String str){
        if (str.toUpperCase(Locale.ROOT).equals("SUPERUSER")) return SUPERUSER;
        return COMMON;
    }

    public static String toString(Role role){
        if (Objects.requireNonNull(role) == Role.SUPERUSER) {
            return "SUPERUSER";
        }
        return "COMMON";
    }
}
