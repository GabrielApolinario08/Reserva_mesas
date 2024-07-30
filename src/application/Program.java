package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Reservation;
import model.entities.Table;

import java.time.LocalDate;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Table table = DaoFactory.getTableDao().findById(4);
        System.out.println(table);
        System.out.println(DaoFactory.getTableDao().findAll());

        DaoFactory.getTableDao().insert(new Table(null, 10, 10));
    }
}
