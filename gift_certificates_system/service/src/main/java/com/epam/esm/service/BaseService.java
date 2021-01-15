package com.epam.esm.service;

public interface BaseService<T> {
    long add(T t);

    T findById(long id);

    void remove(long id);
}
