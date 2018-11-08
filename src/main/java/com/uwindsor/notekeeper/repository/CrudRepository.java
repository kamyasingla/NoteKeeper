package com.uwindsor.notekeeper.repository;

import java.util.List;

public interface CrudRepository<T> {
    T save(T item, String content);

    T findById(T item);

    List<T> findAllInPath(String path);

    T deleteById(String id);
}
