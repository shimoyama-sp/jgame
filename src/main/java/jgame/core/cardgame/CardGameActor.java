package jgame.core.cardgame;

import java.util.ArrayList;
import java.util.List;

import jgame.core.Actor;

/**
 * カードゲームをするプレイヤー (プレイヤー、ディーラー等はフラグで管理)<br>
 * <br>
 */
public class CardGameActor implements Actor {

    /**
     * プレイヤーの役割<br>
     * <br>
     */
    public enum ActorMode {
        PLAYER,
        DEALER,
        ;
    }
    
    private ActorMode actorMode;
    
    private String playerName;
    
    /** 順位 */
    private int rank;
    
    /** 手札 */
    private List<Card> hand = new ArrayList<>();

    public CardGameActor(ActorMode actorMode, String playerName) {
        this.actorMode = actorMode;
        this.playerName = playerName;
    }
    
    public ActorMode getActorMode() {
        return actorMode;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
    
    public List<Card> getHand(){
        return hand;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public int getRank() {
        return rank;
    }
    
    public void reflashHand() {
        hand.clear();
    }
}
