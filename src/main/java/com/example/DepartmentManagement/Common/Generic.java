package com.example.DepartmentManagement.Common;

import java.util.List;
import java.util.Optional;

public interface Generic<T, ID> {
    List<T> findAll();
    Optional<T> findById(Number id);
    Optional<T> findByName(String name);
}