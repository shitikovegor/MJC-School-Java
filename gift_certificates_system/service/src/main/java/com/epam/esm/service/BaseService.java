package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    T add (T t);

    List<T> findAll();

    T findById(long id);

    void remove (long id);
}
