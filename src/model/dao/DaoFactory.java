package model.dao;

import model.dao.impl.ReservationDaoJDBC;
import model.dao.impl.TableDaoJDBC;

public class DaoFactory {
    public static ReservationDaoJDBC getReservationDao() {
        return new ReservationDaoJDBC();
    }

    public static TableDaoJDBC getTableDao() {
        return new TableDaoJDBC();
    }
}
