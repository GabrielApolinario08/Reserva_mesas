package model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Integer id;
    private String clientName;
    private Integer peopleNumber;
    private LocalDate date;
    private Table table;

    public Reservation() {
        setId(null);
        setClientName(null);
        setPeopleNumber(null);
        setDate(null);
        setTable(null);
    }

    public Reservation(Integer id, String clientName, Integer peopleNumber, LocalDate date, Table table) {
        setId(id);
        setClientName(clientName);
        setPeopleNumber(peopleNumber);
        setDate(date);
        setTable(table);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", peopleNumber=" + peopleNumber +
                ", date=" + date +
                ", table=" + table +
                '}';
    }
}
