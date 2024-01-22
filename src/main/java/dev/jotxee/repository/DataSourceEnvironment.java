package dev.jotxee.repository;

import java.util.Arrays;

public enum DataSourceEnvironment {
    PRO("mango"),
    PRE("MNGPRED"),
    DEV("MNGPRES");

    private final String sid;

    DataSourceEnvironment(final String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }
    public static String getSid(final String env) {
        return Arrays.stream(values())
                .filter(environment -> environment.name().equalsIgnoreCase(env))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Entorno no válido: " + env))
                .getSid();
    }
}
