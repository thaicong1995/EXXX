package com.example.DepartmentManagement.Mapper;

import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

public class Mapper<T, U> {
    private final Class<T> entityClass;
    private final Class<U> dtoClass;

    public Mapper(Class<T> entityClass, Class<U> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public U mapEntityToDto(T entity) {
        try {
            U dto = dtoClass.getDeclaredConstructor().newInstance();
            for (Field entityField : entityClass.getDeclaredFields()) {
                entityField.setAccessible(true);
                Field dtoField;
                try {
                    dtoField = dtoClass.getDeclaredField(entityField.getName());
                    dtoField.setAccessible(true);
                    dtoField.set(dto, entityField.get(entity));
                } catch (NoSuchFieldException ignored) {
                }
            }
            return dto;
        } catch (Exception ex) {
            throw new RuntimeException("Error mapping entity to DTO", ex);
        }
    }

    public T mapDtoToEntity(U dto) {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            for (Field dtoField : dtoClass.getDeclaredFields()) {
                dtoField.setAccessible(true);
                Field entityField;
                try {
                    entityField = entityClass.getDeclaredField(dtoField.getName());
                    entityField.setAccessible(true);
                    entityField.set(entity, dtoField.get(dto));
                } catch (NoSuchFieldException ignored) {
                }
            }
            return entity;
        } catch (Exception ex) {
            throw new RuntimeException("Error mapping DTO to entity", ex);
        }
    }
}
