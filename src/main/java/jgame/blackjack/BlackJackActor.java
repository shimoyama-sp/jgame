package jgame.blackjack;

import jgame.core.cardgame.CardGameActor;

/**
 */
public class BlackJackActor extends CardGameActor {

    /** ユーザの手札の合計ポイント */
    private int point = 0;
    
    public BlackJackActor(ActorMode actorMode, String playerName) {
        super(actorMode, playerName);
    }

    public int getPoint() {
        return point;
    }

    public void setPoin(int point) {
        this.point = point;
    }

}
