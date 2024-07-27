package model.dao;

import model.entities.Table;

public interface TableDao {
    void insert(Table reservation);
    void delete(Table reservation);
    void update(Table reservation);
    Table findById(Long id);
    Table findAll();
}
