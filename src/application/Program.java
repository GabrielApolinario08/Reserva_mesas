package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Reservation;
import model.entities.Table;

public class Program {
    public static void main(String[] args) {
        DB.getConnection();
        DaoFactory.getReservationDao().insert(new Reservation());
        DaoFactory.getTableDao().insert(new Table());
        DB.closeConnection();
    }
}
