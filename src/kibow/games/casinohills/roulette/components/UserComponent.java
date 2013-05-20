package kibow.games.casinohills.roulette.components;

import java.util.List;

public class UserComponent {
    public enum UserAction {
        INCREASE_BALANCE, DECREASE_BALANCE, UPDATE_BALANCE
    }

    public UserComponent(String id, int status, String nickname,
                         String language, String dateCreate, String dateModify,
                         String email, double balanceAmount, String token) {
        this.email = email;
        this.balance = new BalanceHandle();
        this.balance.updateBalance(balanceAmount);
        this.id = id;
        this.status = status;
        this.nickname = nickname;
        this.language = language;
        this.dateCreate = dateCreate;
        this.dateModify = dateModify;
        this.token = token;
    }

    public List<HistoryComponent> historyList;

    public double getBalance() {
        return this.balance.balance;
    }

    public double updateBalance(UserAction action, double amount) {
        switch (action) {
            case INCREASE_BALANCE:
                balance.increase(amount);
                break;
            case DECREASE_BALANCE:
                balance.decrease(amount);
                break;
            case UPDATE_BALANCE:
                balance.updateBalance(amount);
                break;
            default:
                break;
        }

        return getBalance();
    }

    public String id;
    public int status;
    public String nickname;
    public String language;
    public String dateCreate;
    public String dateModify;
    public String email;
    public BalanceHandle balance;
    public long actionTime;
    public String token;
}
