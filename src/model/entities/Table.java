package model.entities;

import java.util.Objects;

public class Table {
    private Integer id;
    private Integer number;
    private Integer capacity;

    public Table() {
        setId(null);
        setNumber(null);
        setCapacity(null);
    }

    public Table(Integer id) {
        setId(id);
        setNumber(null);
        setCapacity(null);
    }

    public Table(Integer number, Integer capacity) {
        setId(null);
        setNumber(number);
        setCapacity(capacity);
    }

    public Table(Integer id, Integer number, Integer capacity) {
        setId(id);
        setNumber(number);
        setCapacity(capacity);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Mesa " + getNumber() + ": Capacidade para "+ getCapacity() + " pessoas.";
    }
}
