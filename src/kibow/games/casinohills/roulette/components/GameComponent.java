package kibow.games.casinohills.roulette.components;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.screen.GameEntity.PatternType;


public class GameComponent {
    public String pocket;
    public boolean isWin;
    public double newBalance;
    public double totalBetAmount;
    public double totalWinAmount;
    public ArrayList<PatternType> winPatterns;

    public void setGame(boolean isWin, String pocket, double newBalance,
                        double totalBetAmount, double totalWinAmount, String strWinPatterns) {
        this.isWin = isWin;
        //int[] idices = convertDice(dices);
        this.pocket = pocket;
        this.newBalance = newBalance;
        this.totalBetAmount = totalBetAmount;
        this.totalWinAmount = totalWinAmount;
        if (!strWinPatterns.equals("[]")) {
            this.winPatterns = convertPattern(strWinPatterns);
        } else {
            this.winPatterns = new ArrayList<PatternType>();
        }
    }

    private int[] convertDice(String arr) {
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "")
                .split(",");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
            }
            ;
        }

        return results;
    }

    private ArrayList<PatternType> convertPattern(String arr) {
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "")
                .split(",");

        ArrayList<PatternType> results = new ArrayList<PatternType>();

        for (int i = 0; i < items.length; i++) {
            try {
                results.add(PatternType.getPattern(items[i]));
            } catch (NumberFormatException nfe) {
            }
            ;
        }

        return results;
    }

    public ArrayList<PatternType> convertStringtoArrayList(String arg) {
        ArrayList<PatternType> result = new ArrayList<PatternType>();

        String[] str = arg.split("\\|");
        for (int i = 0; i < str.length; i++) {
            if (!str[i].equals("|") && !str[i].equals(""))
                result.add(PatternType.values()[Integer.parseInt(str[i]) - 1]);
        }

        return result;
    }
}
