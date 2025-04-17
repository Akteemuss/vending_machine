package model;

public interface moneyAcceptor {
    int getAmount();
    void addFunds();
    void deduct(int amount);
}
