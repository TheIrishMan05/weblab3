package org.example.labwork3.database;

import java.util.List;

public interface Repository<T> {
    List<T> findBySessionId(String sessionId);

    void insert(T t);
}
