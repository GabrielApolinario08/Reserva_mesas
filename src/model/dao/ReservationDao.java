package model.dao;

import model.entities.Reservation;

import java.util.List;

public interface ReservationDao {
    void insert(Reservation reservation);
    void delete(Reservation reservation);
    void update(Reservation reservation);
    Reservation findById(int id);
    List<Reservation> findAll();

}
