package application;

import db.DB;

import java.util.InputMismatchException;
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
                System.out.println(opc);
                if (opc < 1 || opc > 9) throw new ApplicationException("Erro! Digite um número válido!");

                switch (opc) {
                    case 1 -> System.out.println("1");
                    case 2 -> System.out.println("2");
                    case 3 -> System.out.println("3");
                    case 4 -> System.out.println("4");
                    case 5 -> System.out.println("5");
                    case 6 -> System.out.println("6");
                    case 7 -> System.out.println("7");
                    case 8 -> System.out.println("8");
                }
            } catch (ApplicationException e) {
                System.out.println("\t" + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("\tErro! Digite um número válido!");
                scanner.nextLine();
            }

        }
        DB.closeConnection();
        scanner.close();
    }
}
