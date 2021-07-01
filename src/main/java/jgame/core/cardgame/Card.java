package jgame.core.cardgame;

/**
 * トランプ (英語ではカード) <br>
 * <br>
 * このクラス 1つが 1枚のカードを意味します。<br>
 */
public class Card {

    /**
     * 模様 (柄)
     */
    public enum Suit {
        SPADE,
        HEART,
        CLUB,
        DIAMOND,
        ;
    }
    
    private Suit suit;
    
    private int num;

    private boolean joker = false;
    
    /**
     * Joker としてカードを定義する。<br>
     * <br>
     */
    public Card() {
        this.joker = true;
    }
    
    /**
     * 
     * @param suit 柄
     * @param num 値
     */
    public Card(Suit suit, int num) {
        super();
        this.suit = suit;
        this.num = num;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNum() {
        return num;
    }

    public boolean isJoker() {
        return joker;
    }

    @Override
    public String toString() {
        return "Card [suit=" + suit + ", num=" + num + ", joker=" + joker + "]";
    }
}
