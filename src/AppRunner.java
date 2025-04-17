import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final MoneyAcceptor moneyAcceptor;
    private static boolean isExit = false;
    private static boolean isExitAcceptor = false;
    private static final Scanner scanner = new Scanner(System.in);
    private static final CardAcceptor cardAcceptor = new CardAcceptor();
    private static final CoinAcceptor coinAcceptor = new CoinAcceptor(100);


    private AppRunner(MoneyAcceptor acceptor) {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        this.moneyAcceptor = acceptor;
    }

    public static void run() {
        while (!isExit) {
            MoneyAcceptor acceptor = chooseAcceptor();
            if (acceptor == null) {
                isExit = true;
                print("Завершение работы...");
                return;
            }
            AppRunner app = new AppRunner(acceptor);
            app.startSimulation();
        }
    }

    private static MoneyAcceptor chooseAcceptor() {
        print("""
                Выберите тип приёмника:
                1 - Монетоприёмник
                2 - Картоприёмник
                3 - Уйти""");
        System.out.print("Ваш выбор: ");

        return switch (scanner.nextLine()) {
            case "2" -> cardAcceptor;
            case "3" -> null;
            default -> coinAcceptor;
        };
    }

    private void startSimulation() {
        while (true) {
            print("В автомате доступны:");
            showProducts(products);

            print("Монет на сумму: " + moneyAcceptor.getAmount());

            UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
            allowProducts.addAll(getAllowedProducts().toArray());
            chooseAction(allowProducts);

            if (isExitAcceptor) {
                break;
            }
        }
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выбрать другой приёмник");
        System.out.print("Ваш выбор: ");

        String input = fromConsole();

        if (input.isBlank()) {
            print("Вы ничего не ввели, попробуйте ещё раз");
            return;
        }

        String action = input.substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            moneyAcceptor.addFunds();
            isExitAcceptor = false;
            return;
        } else if ("h".equalsIgnoreCase(action)) {
            isExitAcceptor = true;
            print("Выходим из данного приёмника...");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    moneyAcceptor.deduct(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    isExitAcceptor = false;
                    return;
                }
            }
            print("Недостаточно средств для выбранного товара или неверный выбор");
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return scanner.nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}
