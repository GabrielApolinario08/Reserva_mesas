package model.dao;

import model.entities.Table;

import java.util.List;

public interface TableDao {
    void insert(Table reservation);
    void deleteById(Integer id);
    void update(Table reservation);
    Table findById(int id);
    List<Table> findAll();
}
