package com.zervladpy.data.dao;

import java.util.List;

public interface DAO<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);

    public void deleteById(long id);

    public void updateImage(T t, String imageRoute);

    public void updateImageById(long id, String imageRoute);

    void deleteAll();

}
