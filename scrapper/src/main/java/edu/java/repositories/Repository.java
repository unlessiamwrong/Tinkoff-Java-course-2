package edu.java.repositories;

import java.util.List;

public interface Repository<T> {
    void add(T entity);

    void remove(T entity);

    List<T> findAll();

}
