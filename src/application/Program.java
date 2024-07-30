package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Reservation;
import model.entities.Table;

import java.time.LocalDate;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        /*Reservation reservation = DaoFactory.getReservationDao().findById(1);
        System.out.println(reservation);

        for (Reservation reser:DaoFactory.getReservationDao().findAll()) {
            System.out.println(reser.getClientName());
        }*/
       /* Reservation reservation = new Reservation("Maria", 6, new Date(), new Table(1));
        DaoFactory.getReservationDao().insert(reservation);*/

        /*DaoFactory.getReservationDao().update(new Reservation(18, "Maria Helena", 5, new Date(), new Table(8, 5, 20)));*/
        DaoFactory.getReservationDao().deleteById(21);
    }
}
