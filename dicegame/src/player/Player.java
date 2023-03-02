package player;

public class Player {

    final private Long playerId;
    final private String nickname;
    final private Dice generalDice;
    final private Dice specialDice;

    public Player(Long playerId, String nickname) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.generalDice = new Dice();
        this.specialDice = new Dice();
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", nickname='" + nickname + '\'' +
                ", generalDice=" + generalDice +
                ", specialDice=" + specialDice +
                '}';
    }

    public int getGeneralDiceNumber() {
        return generalDice.getDiceNumber();
    }

    public int getSpecialDiceNumber() {
        return specialDice.getDiceNumber();
    }
}
