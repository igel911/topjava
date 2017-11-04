package ru.javawebinar.topjava.dao;

import java.util.List;

public interface ItemDao<T> {

    T getById(int id);

    List<T> getAll();

    void add(T model);

    void update(T model);

    void delete(T model);
}
