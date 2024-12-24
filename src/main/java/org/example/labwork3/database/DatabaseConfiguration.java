package org.example.labwork3.database;

import lombok.Getter;

@Getter
public class DatabaseConfiguration {
    private final String hostname = System.getenv("HOSTNAME");
    private final String password = System.getenv("PASSWORD");
    private final String url = System.getenv("URL");
}
