package model;

import java.util.Scanner;

public class CardAcceptor implements moneyAcceptor{
    private int amount;

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void addFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер карты (16 значений): ");
        String card = scanner.nextLine();
        System.out.println("Введите пароль (4 значений): ");
        String password = scanner.nextLine();

        if (card.length() == 16 && password.length() == 4) {
            System.out.println("Введите сумму для пополнения: ");
            int added = scanner.nextInt();
            amount += added;
            System.out.printf("Вы пополнили баланс на %d", added);
        } else {
            System.out.println("Ошбика авторизации карты");
        }
    }

    @Override
    public void deduct(int amount) {
        this.amount -= amount;
    }
}
