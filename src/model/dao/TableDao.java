package model.dao;

import model.entities.Table;

import java.util.List;

public interface TableDao {
    void insert(Table reservation);
    void delete(Table reservation);
    void update(Table reservation);
    Table findById(int id);
    List<Table> findAll();
}
