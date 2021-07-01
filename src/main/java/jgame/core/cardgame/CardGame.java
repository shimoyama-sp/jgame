package jgame.core.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jgame.core.Game;
import jgame.core.cardgame.Card.Suit;

/**
 * カードゲーム (トランプ) のスーパークラス<br>
 * <br>
 */
public abstract class CardGame<A extends CardGameActor, J extends CardGameJudgment<CardGameRule<A>>> extends Game<A, J> {

    abstract protected List<A> getActors();

    /**
     * デッキを取得します。<br>
     * <br>
     * @return
     */
    abstract protected List<Card> getStock();

    /**
     * デッキ (山札) を初期化する。<br>
     * <br>
     * @param jokerCount ジョーカーの枚数
     */
    public List<Card> setupStock(int jokerCount) {

        List<Card> stock = new ArrayList<>();

        // 通常は 1～13 (King) までで山札を込みで作る.
        // ただし、ゲームによっては使用するものが変わる可能性があるので、この辺は拡張できるようにしたい
        for (Suit suit : Suit.values()) {
            for (int num = 1; num <= 13; num++) {
                stock.add(new Card(suit, num));
            }
        }

        for (int i = 0; i < jokerCount; i++) {
            stock.add(new Card());
        }

        return stock;
    }

    /**
     * シャッフルする。<br>
     * <br>
     * 引数で渡された山札自体がシャッフルされた状態になります。<br>
     * <br>
     * @param stock
     */
    public void shuffle(List<Card> stock) {
        Collections.shuffle(stock);
    }

    /**
     * カードを引きます。<br>
     * <br>
     * @return カードが引けなかった場合、null を返します。<br>
     */
    abstract protected Card drawCard();

    public boolean isEmpty(List<Card> stock) {
        return (stock == null || stock.isEmpty());
    }

    protected Card nextCard() {
        Card card = getStock().get(0);
        getStock().remove(0);
        return card;
    }
    
    public void game() {
        for (A actor : getActors()) {
            // カードを引く
            while(getJudgement().canDraw(actor)) {
                actor.addCard(nextCard());
            }
        }
    }
    
    /**
     * カードを引く。<br>
     * <br>
     * @return
     */
    protected Card deal() {
        List<Card> stock = getStock();
        if (stock == null || stock.isEmpty())
            return null;

        Card card = stock.get(0);
        stock.remove(0);

        return card;
    }
}
