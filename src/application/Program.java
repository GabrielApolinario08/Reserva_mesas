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
        int opc = 0;
        while (opc != 9){
            try {
                System.out.println(UI.menu());
                System.out.println("Escolha uma opção: ");
                opc = scanner.nextInt();
                scanner.nextLine();
                if (opc < 1 || opc > 9) throw new ApplicationException("Erro! Digite um número válido!");

                switch (opc) {
                    case 1 -> registerTable(scanner);
                    case 2 -> registerReservation(scanner);
                    case 3 -> listTables();
                    case 4 -> listReservations();
                    case 5 -> updateTable(scanner);
                    case 6 -> uptadeReservation(scanner);
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
                if (opc != 9) {
                    System.out.println("\n\tPressione Enter para prosseguir.");
                    scanner.nextLine();

                }
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
    static void registerReservation(Scanner scanner) {
        List<Table> tables = DaoFactory.getTableDao().findAll();
        if (tables.isEmpty()) {
            System.out.println("Não é possível cadastrar reserva, pois não há mesas cadastradas.");
            return;
        }
        String clientName;
        LocalDateTime reservationDate;
        int peopleNumber, tableNumber;
        System.out.print("Informe o nome do cliente: ");
        clientName = scanner.nextLine();
        System.out.print("Informe a data e hora da reserva (dd/mm/yyyy hh:mm): ");
        reservationDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Informe o número de pessoas da reserva: ");
        peopleNumber = scanner.nextInt();
        System.out.print("Informe o número da mesa escolhida: ");
        tableNumber = scanner.nextInt();
        Table table = DaoFactory.getTableDao().findByNumber(tableNumber);
        Reservation reservation = new Reservation(clientName, peopleNumber, reservationDate, table);
        validatingReservation(reservation);
        if (checkEqualDays(reservation)) {
            System.out.println("Já existe uma reserva nesta mesa e neste mesmo dia.");
            String opc = wantToContinue(scanner);
            if (opc.equals("n")) {
                System.out.println("A reserva não foi cadastrada.");
                return;
            }
        }
        DaoFactory.getReservationDao().insert(reservation);
        System.out.println("Reserva cadastrada com sucesso!");
    }
    static void listTables() {
        List<Table> tables = DaoFactory.getTableDao().findAll();
        if (tables.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }
        System.out.println("Lista de todas as mesas: ");
        for (Table table:tables) {
            System.out.println(table);
        }
    }
    static void listReservations() {
        List<Reservation> reservations = DaoFactory.getReservationDao().findAll();
        if (reservations.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        System.out.println("Lista de todas as reservas: ");
        for (Reservation reservation:reservations) {
            System.out.println(reservation);
        }
    }
    static void updateTable(Scanner scanner) {
        int number, capacity;
        List<Table> tables = DaoFactory.getTableDao().findAll();
        if (tables.isEmpty()) {
            System.out.println("Nenhuma mesa para ser atualizada.");
            return;
        }
        System.out.println("Lista de todas as mesas: ");
        for (Table table:tables) {
            System.out.println(table);
        }
        System.out.print("Informe o número da mesa que deseja atualizar: ");
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
    static void uptadeReservation(Scanner scanner) {
        List<Reservation> reservations = DaoFactory.getReservationDao().findAll();
        if (reservations.isEmpty()) {
            System.out.println("Nenhuma reserva para ser atualizada.");
            return;
        }
        String clientName;
        LocalDateTime reservationDate;
        int peopleNumber, tableNumber, id;
        System.out.println("Lista de reservas: ");
        for (Reservation reservation:reservations) {
            System.out.println(reservation.toString(true));
        }
        System.out.print("Informe o id da reserva que deseja atualizar: ");
        id = scanner.nextInt();
        scanner.nextLine();
        Reservation reservation = DaoFactory.getReservationDao().findById(id);
        if (reservation == null) throw new ApplicationException("Reserva não existente");

        System.out.println("Informe os novos dados abaixo: ");
        System.out.print("Cliente: ");
        clientName = scanner.nextLine();
        System.out.print("Data e hora (dd/mm/yyyy hh:mm): ");
        reservationDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Número de pessoas: ");
        peopleNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Número da mesa: ");
        tableNumber = scanner.nextInt();
        scanner.nextLine();
        Table table = DaoFactory.getTableDao().findByNumber(tableNumber);
        Reservation finalReservation = new Reservation(id, clientName, peopleNumber, reservationDate, table);
        validatingReservation(finalReservation);
        if (checkEqualDays(reservation)) {
            System.out.println("Já existe uma reserva nesta mesa e neste mesmo dia as " + reservation.getDate().format(DateTimeFormatter.ofPattern("HH:mm")) + ".");
            String opc = wantToContinue(scanner);
            if (opc.equals("n")) {
                System.out.println("A reserva não foi atualizada.");
                return;
            }
        }
        DaoFactory.getReservationDao().update(finalReservation);
        System.out.println("Reserva atualizada com sucesso!");
        System.out.println("Dados atualizados: ");
        System.out.println(DaoFactory.getReservationDao().findById(id));
    }
    static void deleteTable(Scanner scanner) {
        int number;
        List<Table> tables = DaoFactory.getTableDao().findAll();
        if (tables.isEmpty()) {
            System.out.println("Nenhuma mesa para ser deletada.");
            return;
        }
        System.out.println("Lista de todas as mesas: ");
        for (Table table:tables) {
            System.out.println(table);
        }
        System.out.print("Número da mesa que deseja deletar: ");
        number = scanner.nextInt();
        scanner.nextLine();
        if (!DaoFactory.getTableDao().existNumber(number)) throw new ApplicationException("Mesa não existente.");
        Table table = DaoFactory.getTableDao().findByNumber(number);
        checkDeleteReservationsThisTable(table, scanner);
    }
    static void deleteReservation(Scanner scanner) {
        int id;
        List<Reservation> reservations = DaoFactory.getReservationDao().findAll();
        if (reservations.isEmpty()) {
            System.out.println("Nenhuma reserva para ser deletada.");
            return;
        }
        System.out.println("Lista de todas as reservas: ");
        for (Reservation reservation:reservations) {
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

    //Métodos auxiliares
    static void validatingReservation(Reservation reservation) {
        if (reservation.getTable() == null) throw new ApplicationException("Mesa não existente.");
        if (reservation.getPeopleNumber() > reservation.getTable().getCapacity()) throw new ApplicationException("Número de pessoas excede a capacidade da mesa.");
    }
    static boolean checkEqualDays(Reservation reservation) {
        List<Reservation> reservations = DaoFactory.getReservationDao().findAll();
        for (Reservation reser:reservations) {
            if (reser.getDate().toLocalDate().isEqual(reser.getDate().toLocalDate()) && reser.getTable().equals(reservation.getTable())) {
                return true;
            }
        }
        return false;
    }
    static void checkDeleteReservationsThisTable(Table table, Scanner scanner) {
        String opc;
        List<Reservation> reservations = reservationsForThisTable(table);
        if (!reservations.isEmpty()) {
            System.out.println("Existem reservas cadastradas nesta mesa! Caso prossiga elas serão apagadas.");
            opc = wantToContinue(scanner);
            if (opc.equals("s")) {
                for (Reservation reservation:reservations) {
                    DaoFactory.getReservationDao().deleteById(reservation.getId());
                }
            } else {
                System.out.println("A mesa não foi deletada.");
                return;
            }
        }
        DaoFactory.getTableDao().deleteById(table.getId());
        System.out.println("Mesa deletada com sucesso!");
    }
    static List<Reservation> reservationsForThisTable(Table table) {
        List<Reservation> reservations;
        reservations = DaoFactory.getReservationDao().findAll().stream().filter(r -> r.getTable().equals(table)).toList();
        return reservations;
    }
    static String wantToContinue(Scanner scanner) {
        String opc = "";
        do {
            try {
                System.out.println("Deseja prosseguir? (s/n)");
                opc = scanner.next();
                scanner.nextLine();
                opc = opc.toLowerCase();
                if (!opc.equals("s") && !opc.equals("n")) throw new ApplicationException("Digite apenas 's' para sim ou 'n' para não.");
            } catch (ApplicationException e) {
                System.out.println(e.getMessage());
            }
        } while(!opc.equals("s") && !opc.equals("n"));
        return opc;
    }
}
