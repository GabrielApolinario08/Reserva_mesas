package model.dao.impl;

import db.DB;
import model.dao.TableDao;
import model.entities.Table;

import java.sql.Connection;

public class TableDaoJDBC implements TableDao {

    private Connection conn = null;
    public TableDaoJDBC() {
        this.conn = DB.getConnection();
    }
    @Override
    public void insert(Table reservation) {
        System.out.println("DEU CEROT");
    }

    @Override
    public void delete(Table reservation) {

    }

    @Override
    public void update(Table reservation) {

    }

    @Override
    public Table findById(Long id) {
        return null;
    }

    @Override
    public Table findAll() {
        return null;
    }
}
