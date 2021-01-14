package com.epam.esm.service;

public interface BaseService<T> {
    T add(T t);

    T findById(long id);

    void remove(long id);
}
