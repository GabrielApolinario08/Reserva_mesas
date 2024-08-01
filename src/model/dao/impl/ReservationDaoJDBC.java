package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.ReservationDao;
import model.entities.Reservation;
import model.entities.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoJDBC implements ReservationDao {

    private final Connection conn;

    public ReservationDaoJDBC() {
        this.conn = DB.getConnection();
    }
    @Override
    public void insert(Reservation reservation) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                    INSERT INTO reservation 
                    (ClientName, ReservationDate, PeopleNumber, IdTable) 
                    VALUES 
                    (?, ?, ?, ?)
                    """, st.RETURN_GENERATED_KEYS
            );
            st.setString(1, reservation.getClientName());
            Timestamp timestamp = Timestamp.valueOf(reservation.getDate());
            st.setTimestamp(2, timestamp);
            st.setInt(3, reservation.getPeopleNumber());
            st.setInt(4, reservation.getTable().getId());
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
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM reservation WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Reservation reservation) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                    UPDATE reservation 
                    SET ClientName = ?, ReservationDate = ?, PeopleNumber = ?, IdTable = ? 
                    WHERE Id = ?
                    """);
            st.setString(1, reservation.getClientName());
            Timestamp timestamp = Timestamp.valueOf(reservation.getDate().toString());
            st.setTimestamp(2, timestamp);
            st.setInt(3, reservation.getPeopleNumber());
            st.setInt(4, reservation.getTable().getId());
            st.setInt(5, reservation.getId());
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
    public Reservation findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement( """
                    SELECT reservation.*, restauranttable.*
                    FROM reservation
                    INNER JOIN restauranttable
                    ON reservation.IdTable = restauranttable.Id
                    WHERE reservation.Id = ?
                    """
            );
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Table table = instanciateTable(rs);
                return instanciateReservation(rs, table);
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
    public List<Reservation> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT reservation.*, restauranttable.* 
                    FROM reservation 
                    INNER JOIN restauranttable 
                    ON reservation.IdTable = restauranttable.Id
                    """);
            rs = st.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (rs.next()) {
                reservations.add(instanciateReservation(rs, instanciateTable(rs)));
            }
            return reservations;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Table instanciateTable(ResultSet rs) throws SQLException{
        return new Table(rs.getInt("Id"), rs.getInt("Number"), rs.getInt("Capacity"));
    }

    private Reservation instanciateReservation(ResultSet rs, Table table) throws SQLException{
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("Id"));
        reservation.setClientName(rs.getString("ClientName"));
        reservation.setDate(rs.getTimestamp("ReservationDate").toLocalDateTime());
        reservation.setPeopleNumber(rs.getInt("PeopleNumber"));
        reservation.setTable(table);
        return reservation;
    }
}
