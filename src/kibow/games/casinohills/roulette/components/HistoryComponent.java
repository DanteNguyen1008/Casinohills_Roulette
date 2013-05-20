package kibow.games.casinohills.roulette.components;

public class HistoryComponent {

    public HistoryComponent(boolean isWin, String betDate, double balance, String dices, String betSpot) {
        this.isWin = isWin;
        this.betDate = betDate;
        this.balance = balance;
        this.dices = dices;
        this.betSpot = betSpot;

    }

    public boolean isWin;
    public String betDate;
    public String dices;
    public double balance;
    public String betSpot;
}
