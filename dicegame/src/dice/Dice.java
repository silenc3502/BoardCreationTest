package dice;

import utility.CustomRandom;

public class Dice {

    final private int MIN = 1;
    final private int MAX = 6;
    final private int diceNumber;

    public Dice () {
        this.diceNumber =
                CustomRandom.createCustomRandom(MIN, MAX);
    }
}
