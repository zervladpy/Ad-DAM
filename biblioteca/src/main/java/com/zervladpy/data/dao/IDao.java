package com.zervladpy.data.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    T get(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void save(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;

    List<Integer> getAllIds() throws SQLException;

    public void deleteById(long id) throws SQLException;

    public void updateImage(T t, String f) throws SQLException;

    public void updateImageById(long id, String f) throws SQLException;

    void deleteAll() throws SQLException;

}
