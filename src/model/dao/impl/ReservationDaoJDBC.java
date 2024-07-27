package model.dao.impl;

import db.DB;
import model.dao.ReservationDao;
import model.entities.Reservation;

import java.sql.Connection;

public class ReservationDaoJDBC implements ReservationDao {

    private Connection conn = null;

    public ReservationDaoJDBC() {
        this.conn = DB.getConnection();
    }
    @Override
    public void insert(Reservation reservation) {
        System.out.println("DEU CEROT");
    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public void update(Reservation reservation) {

    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    @Override
    public Reservation findAll() {
        return null;
    }
}
