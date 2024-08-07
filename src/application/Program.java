package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Reservation;
import model.entities.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        int opc = 0;
        while (opc != 9){
            try {
                System.out.println(UI.menu());
                System.out.println("Escolha uma opção: ");
                opc = scanner.nextInt();
                if (opc < 1 || opc > 9) throw new ApplicationException("Erro! Digite um número válido!");

                switch (opc) {
                    case 1 -> registerTable(scanner);
                    case 2 -> registerReservation(scanner, scannerString);
                    case 3 -> listTables();
                    case 4 -> listReservations();
                    case 5 -> updateTable(scanner);
                    case 6 -> uptadeReservation(scanner, scannerString);
                    case 7 -> deleteTable(scanner);
                    case 8 -> deleteReservation(scanner);
                    case 9 -> System.out.println("Programa encerrado com sucesso!");
                }
            } catch (ApplicationException e) {
                System.out.println("\n\t" + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("\n\tErro! Digite um número válido!");
                scanner.nextLine();
            } catch (DateTimeParseException e) {
                System.out.println("\n\tErro! Digite a data e hora corretamente!");
            } finally {
                if (opc != 9) scannerString.nextLine();
            }

        }
        DB.closeConnection();
        scanner.close();
    }
    static void registerTable(Scanner scanner) {
        int number, capacity;
        System.out.print("Informe o número da mesa: ");
        number = scanner.nextInt();
        if (DaoFactory.getTableDao().existNumber(number)) {
            throw new ApplicationException("Número da mesa já existente.");
        }
        System.out.print("Informe a capacidade máxima da mesa: ");
        capacity = scanner.nextInt();
        if (capacity <= 0) {
            throw new ApplicationException("Capacidade da mesa deve ser maior que 0.");
        }

        DaoFactory.getTableDao().insert(new Table(number, capacity));
        System.out.println("Mesa cadastrada com sucesso!");
    }

    static void registerReservation(Scanner scanner, Scanner scannerString) {
        String clientName;
        LocalDateTime reservationDate;
        int peopleNumber, tableNumber;
        System.out.print("Informe o nome do cliente: ");
        clientName = scannerString.nextLine();
        System.out.print("Informe a data e hora da reserva (dd/mm/yyyy hh:mm): ");
        reservationDate = LocalDateTime.parse(scannerString.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Informe o número de pessoas da reserva: ");
        peopleNumber = scanner.nextInt();
        System.out.print("Informe o número da mesa escolhida: ");
        tableNumber = scanner.nextInt();

        if (!DaoFactory.getTableDao().existNumber(tableNumber)) throw new ApplicationException("Mesa não existente.");
        Table table = DaoFactory.getTableDao().findByNumber(tableNumber);
        if (peopleNumber > table.getCapacity()) throw new ApplicationException("Número de pessoas excede a capacidade da mesa.");
        DaoFactory.getReservationDao().insert(new Reservation(clientName, peopleNumber, reservationDate, table));
        System.out.println("Reserva cadastrada com sucesso!");
    }

    static void listTables() {
        System.out.println("Lista de todas as mesas: ");
        List<Table> tables = DaoFactory.getTableDao().findAll();
        for (Table table:tables) {
            System.out.println(table);
        }
    }

    static void listReservations() {
        System.out.println("Lista de todas as reservas: ");
        List<Reservation> reservations = DaoFactory.getReservationDao().findAll();
        for (Reservation reservation:reservations) {
            System.out.println(reservation);
        }
    }

    static void updateTable(Scanner scanner) {
        int number, capacity;
        System.out.print("Informe o número da mesa: ");
        number = scanner.nextInt();

        if (!DaoFactory.getTableDao().existNumber(number)) {
            throw new ApplicationException("Mesa não existente.");
        }
        Table table = DaoFactory.getTableDao().findByNumber(number);
        System.out.println("Dados atuais da mesa: ");
        System.out.println(table);
        System.out.println("Informe os novos dados abaixo: ");
        System.out.print("Número: ");
        number = scanner.nextInt();
        System.out.print("Capacidade máxima da mesa: ");
        capacity = scanner.nextInt();
        if (capacity <= 0) {
            throw new ApplicationException("Capacidade da mesa deve ser maior que 0.");
        }

        DaoFactory.getTableDao().update(new Table(table.getId(), number, capacity));
        System.out.println("Mesa atualizada com sucesso!");
        System.out.println("Dados atualizados: ");
        System.out.println(DaoFactory.getTableDao().findById(table.getId()));
    }
    static void uptadeReservation(Scanner scanner, Scanner scannerString) {
        String clientName;
        LocalDateTime reservationDate;
        int peopleNumber, tableNumber, id;
        System.out.println("Lista de reservas: ");
        for (Reservation reservation:DaoFactory.getReservationDao().findAll()) {
            System.out.println(reservation.toString(true));
        }
        System.out.print("Informe o id da reserva que deseja atualizar: ");
        id = scanner.nextInt();
        scanner.nextLine();
        Reservation reservation = DaoFactory.getReservationDao().findById(id);
        if (reservation == null) throw new ApplicationException("Reserva não existente");

        System.out.println("Informe os novos dados abaixo: ");
        System.out.print("Cliente: ");
        clientName = scannerString.nextLine();
        System.out.print("Data e hora (dd/mm/yyyy hh:mm): ");
        reservationDate = LocalDateTime.parse(scannerString.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Número de pessoas: ");
        peopleNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Número da mesa: ");
        tableNumber = scanner.nextInt();
        scanner.nextLine();
        if (!DaoFactory.getTableDao().existNumber(tableNumber)) throw new ApplicationException("Mesa não existente.");
        Table table = DaoFactory.getTableDao().findByNumber(tableNumber);
        if (peopleNumber > table.getCapacity()) throw new ApplicationException("Número de pessoas excede a capacidade da mesa.");


        DaoFactory.getReservationDao().update(new Reservation(id, clientName, peopleNumber, reservationDate, table));
        System.out.println("Reserva atualizada com sucesso!");
        System.out.println("Dados atualizados: ");
        System.out.println(DaoFactory.getReservationDao().findById(id));
    }
    static void deleteTable(Scanner scanner) {
        int number;
        System.out.println("Lista de todas as mesas: ");
        for (Table table:DaoFactory.getTableDao().findAll()) {
            System.out.println(table);
        }
        System.out.print("Número da mesa que deseja deletar: ");
        number = scanner.nextInt();
        scanner.nextLine();
        if (!DaoFactory.getTableDao().existNumber(number)) throw new ApplicationException("Mesa não existente.");
        Table table = DaoFactory.getTableDao().findByNumber(number);
        DaoFactory.getTableDao().deleteById(table.getId());
        System.out.println("Mesa deletada com sucesso!");
    }
    static void deleteReservation(Scanner scanner) {
        int id;
        System.out.println("Lista de todas as reservas: ");
        for (Reservation reservation:DaoFactory.getReservationDao().findAll()) {
            System.out.println(reservation.toString(true));
        }
        System.out.print("ID da reserva que deseja deletar: ");
        id = scanner.nextInt();
        scanner.nextLine();
        Reservation reservation = DaoFactory.getReservationDao().findById(id);
        if (reservation == null) throw new ApplicationException("Reserva não existente.");
        DaoFactory.getReservationDao().deleteById(id);
        System.out.println("Reserva deletada com sucesso!");
    }
}
