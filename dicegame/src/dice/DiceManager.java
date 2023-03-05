package dice;

public class DiceManager {

    final static int EVEN_ODD_CHECK = 2;
    final static int EVEN = 0;

    final private Long id;
    final private Dice generalDice;
    final private SpecialDice specialDice;

    final private Long playerId;

    public DiceManager (Long id, Long playerId) {
        // TODO: 주사위 개수가 여러개가 되면 룰 변경 가능성이 존재함에 따라 변경 파급이 존재함 (추가 리팩토링 필요)
        this.id = id;
        generalDice = new Dice();

        if (generalDice.getDiceNumber() % EVEN_ODD_CHECK == EVEN) {
            specialDice = new SpecialDice();
        }
        else { specialDice = null; }

        this.playerId = playerId;
    }

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Dice getGeneralDice() {
        return generalDice;
    }

    public SpecialDice getSpecialDice() {
        return specialDice;
    }

    @Override
    public String toString() {
        return "DiceManager{" +
                "id=" + id +
                ", generalDice=" + generalDice +
                ", specialDice=" + specialDice +
                ", playerId=" + playerId +
                '}' + '\n';
    }
}
