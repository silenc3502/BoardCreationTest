package player.service;

import player.Player;
import player.SpecialDicePattern;

import java.util.List;

public class PlayerServiceImpl implements PlayerService {

    final int EVEN_ODD_DECISION_VALUE = 2;
    final int EVEN = 0;

    // 1. 일반 주사위를 보고 짝수인지 판정한다.
    // 2. 짝수라면 특수 주사위의 숫자를 파악한다.

    // 2-1. 숫자 4가 나오는 경우 즉시 게임 오버된다.
    // 2-2. 숫자 3이 나오는 경우엔 친구들 주사위에서 점수를 3점씩 뺏어올 수 있다.
    // 2-3. 숫자 5가 나오는 경우엔 자신의 점수를 2점씩 친구들에게 나눠줘야 한다.
    // 2-4. 숫자 1의 경우엔 모두 다 함께 -2점씩 감점된다.

    // 3. 각 숫자에 적합한 작업을 진행한다.
    private Boolean isEven(List<Player> playerList, int currentIdx) {
        int diceNumber = playerList.get(currentIdx).getGeneralDiceNumber();
        Boolean isEven = diceNumber % EVEN_ODD_DECISION_VALUE == EVEN;

        if (isEven) {
            System.out.println("일반 주사위는 짝수입니다: " + diceNumber);
        } else {
            System.out.println("일반 주사위는 홀수입니다: " + diceNumber);
        }

        return isEven;
    }

    private int getSpecialDiceNumber (List<Player> playerList, int currentIdx) {
        return playerList.get(currentIdx).getSpecialDiceNumber();
    }

    private void applyEachPlayer (List<Player> playerList, int currentIdx) {
        if (isEven(playerList, currentIdx)) {

            int specialDice = getSpecialDiceNumber(playerList, currentIdx);

            if (specialDice == SpecialDicePattern.PATTERN_DEATH.getValue()) {
                System.out.println(SpecialDicePattern.PATTERN_DEATH.getName());
            } else if (specialDice == SpecialDicePattern.PATTERN_STEAL.getValue()) {
                System.out.println(SpecialDicePattern.PATTERN_STEAL.getName());
            } else if (specialDice == SpecialDicePattern.PATTERN_BORROW.getValue()) {
                System.out.println(SpecialDicePattern.PATTERN_BORROW.getName());
            } else if (specialDice == SpecialDicePattern.PATTERN_BUDDY_FUCKER.getValue()) {
                System.out.println(SpecialDicePattern.PATTERN_BUDDY_FUCKER.getName());
            } else {
                System.out.println("특수 동작 없음: " + specialDice);
            }
        }
    }
    @Override
    public Boolean playDiceGame(List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println("player" + (i + 1) + ": ");
            applyEachPlayer(playerList, i);
        }

        return false;
    }

    /*

     */
}
