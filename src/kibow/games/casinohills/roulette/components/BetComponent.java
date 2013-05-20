package kibow.games.casinohills.roulette.components;

public class BetComponent {
    public String betPatternID;
    public int betAmount;

    public BetComponent(String betPatternID, int betAmount) {
        this.betAmount = betAmount;
        this.betPatternID = betPatternID;
    }
}
