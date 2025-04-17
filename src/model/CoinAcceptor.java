package model;

public class CoinAcceptor implements MoneyAcceptor {
    private int amount;

    public CoinAcceptor(int amount) {
        this.amount = amount;
    }

    @Override
    public void addFunds() {
        amount += 10;
        System.out.println("Вы пополнили баланс на 10");
    }

    @Override
    public void deduct(int amount) {
        this.amount -= amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
