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

        Table table1 = new Table(10, 20, 30);
        DaoFactory.getTableDao().update(table1);
        System.out.println(table1);
    }
}
