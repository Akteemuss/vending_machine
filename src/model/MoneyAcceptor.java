package model;

public interface MoneyAcceptor {
    int getAmount();
    void addFunds();
    void deduct(int amount);
}
