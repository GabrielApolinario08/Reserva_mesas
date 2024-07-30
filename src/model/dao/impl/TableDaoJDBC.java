package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.TableDao;
import model.entities.Reservation;
import model.entities.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public Table findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st= conn.prepareStatement("""
                    SELECT * FROM restauranttable 
                    WHERE Id = ?
                    """);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instanciateTable(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Table> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM restauranttable");
            rs = st.executeQuery();
            List<Table> tables= new ArrayList<>();
            while (rs.next()) {
                tables.add(instanciateTable(rs));
            }
            return tables;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Table instanciateTable(ResultSet rs) throws SQLException{
        return new Table(rs.getInt("Id"), rs.getInt("Number"), rs.getInt("Capacity"));
    }
}
