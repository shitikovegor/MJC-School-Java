package com.epam.esm.validator;

public interface DtoValidator<T> {

    /**
     * Validate object.
     *
     * @param entity the entity DTO
     */
    void validate(T entity);
}
