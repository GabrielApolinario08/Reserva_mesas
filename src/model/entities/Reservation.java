package model.entities;

import application.ApplicationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Integer id;
    private String clientName;
    private Integer peopleNumber;
    private LocalDateTime date;
    private Table table;

    public Reservation() {
        this.id = null;
        this.clientName = null;
        this.peopleNumber = null;
        this.date = null;
        this.table = null;
    }

    public Reservation(Integer id, String clientName, Integer peopleNumber, LocalDateTime date, Table table) {
        setId(id);
        setClientName(clientName);
        setPeopleNumber(peopleNumber);
        setDate(date);
        setTable(table);
    }

    public Reservation(String clientName, Integer peopleNumber, LocalDateTime date, Table table) {
        setId(null);
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
        if (clientName.isEmpty()) throw new ApplicationException("Cliente deve ter nome.");
        this.clientName = clientName;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        if (peopleNumber <= 0) throw new ApplicationException("Reserva deve ter no mínimo uma pessoa.");
        this.peopleNumber = peopleNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if (LocalDateTime.now().isAfter(date)) throw new ApplicationException("A data e hora deve ser no futuro.");
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
        return "\n=======Reserva=======\n" +
                "Mesa: " + getTable().getNumber() +
                "\nData: " + getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "\nCliente: " + getClientName() +
                "\nNúmero de pessoas: " + getPeopleNumber() +
                "\n=====================";
    }

    public String toString(Boolean withId) {
        if (withId) {
            return "\n=======Reserva=======\n" +
                    "ID: " + getId() +
                    "\nMesa: " + getTable().getNumber() +
                    "\nData: " + getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                    "\nCliente: " + getClientName() +
                    "\nNúmero de pessoas: " + getPeopleNumber() +
                    "\n=====================";
        }
        return toString();
    }
}
