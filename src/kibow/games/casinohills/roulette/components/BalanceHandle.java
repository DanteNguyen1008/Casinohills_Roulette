package kibow.games.casinohills.roulette.components;

public class BalanceHandle {

    public double balance;

    public void updateBalance(double amount) {
        this.balance = amount;
    }

    public void increase(double amount) {
        this.balance += amount;
    }

    public void decrease(double amount) {
        this.balance -= amount;
    }
}
