package org.example.labwork3.check;

public interface Checker<T> {
    boolean check(T t);

    boolean isValid(T t);
}
