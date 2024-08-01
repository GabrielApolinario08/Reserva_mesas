package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
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

    private final Connection conn;
    public TableDaoJDBC() {
        this.conn = DB.getConnection();
    }
    @Override
    public void insert(Table reservation) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                    INSERT into restauranttable 
                    (Number, Capacity) 
                    VALUES
                    (?, ?)
                    """, st.RETURN_GENERATED_KEYS
            );
            st.setInt(1, reservation.getNumber());
            st.setInt(2, reservation.getCapacity());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    reservation.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Não foi possível inserir os dados.");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM restauranttable WHERE Id = ?");
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected <= 0) {
                throw new DbException("Erro inesperado! Não foi possível inserir os dados.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Table reservation) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                    UPDATE restauranttable 
                    SET Number = ?, Capacity = ? 
                    WHERE id = ?
                    """
            );
            st.setInt(1, reservation.getNumber());
            st.setInt(2, reservation.getCapacity());
            st.setInt(3, reservation.getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected <= 0) {
                throw new DbException("Erro inesperado! Não foi possível inserir os novos dados.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
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

    @Override
    public boolean existNumber(Integer number) {
        for (Table table:DaoFactory.getTableDao().findAll()) {
            if (number == table.getNumber()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Table findByNumber(int number) {
        for (Table table:DaoFactory.getTableDao().findAll()) {
            if (number == table.getNumber()) {
                return table;
            }
        }
        return null;
    }

    private Table instanciateTable(ResultSet rs) throws SQLException{
        return new Table(rs.getInt("Id"), rs.getInt("Number"), rs.getInt("Capacity"));
    }
}
