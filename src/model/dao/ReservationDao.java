package model.dao;

import model.entities.Reservation;

public interface ReservationDao {
    void insert(Reservation reservation);
    void delete(Reservation reservation);
    void update(Reservation reservation);
    Reservation findById(int id);
    Reservation findAll();

}
