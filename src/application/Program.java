package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Reservation;
import model.entities.Table;

public class Program {
    public static void main(String[] args) {
        Reservation reservation = DaoFactory.getReservationDao().findById(1);
        System.out.println(reservation);

        for (Reservation reser:DaoFactory.getReservationDao().findAll()) {
            System.out.println(reser.getClientName());
        }
    }
}
