package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CardAcceptor implements MoneyAcceptor {
    private int amount;

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void addFunds() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите номер карты (16 значений): ");
            String card = scanner.nextLine();
            if (card.length() != 16 || !card.matches("\\d+")) {
                System.out.println("Номер карты должен содержать 16 цифр");
                return;
            }

            System.out.print("Введите пароль (4 значений): ");
            String password = scanner.nextLine();
            if (password.length() != 4 || !password.matches("\\d+")) {
                System.out.println("Пароль должен содержать 4 цифры");
                return;
            }

            System.out.print("Введите сумму для пополнения: ");
            int added;
            try {
                added = scanner.nextInt();
                scanner.nextLine();

                if (added <= 0) {
                    System.out.println("Сумма должна быть положительной");
                    return;
                }

            } catch (InputMismatchException e) {
                System.out.println("Введите числовое значение суммы");
                scanner.nextLine();
                return;
            }
            amount += added;
            System.out.printf("Вы пополнили баланс на %d%n", added);
        } finally {
            // чтобы не закрылся сканер
        }
    }

    @Override
    public void deduct(int amount) {
        this.amount -= amount;
    }
}
