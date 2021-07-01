package jgame.blackjack;

import java.util.ArrayList;
import java.util.List;

import jgame.core.cardgame.Card;
import jgame.core.cardgame.CardGameActor;
import jgame.core.cardgame.CardGameJudgment;

/**
 * BlackJack 用の審判員<br>
 *
 * @param <R> BlackJack のルール
 */
public class BlackJackJudgment<R extends BlackJackRule<? extends BlackJackActor>> extends CardGameJudgment<R> {

    private List<Card> stock;

    public BlackJackJudgment(List<R> rules) {
        super(rules);
    }

    public void init(List<Card> stock) {
        this.stock = stock;
    }

    @Override
    public boolean canStart() {
        return (stock != null && !stock.isEmpty());
    }

    @Override
    protected CardGameActor getCurrentActor() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    protected List<Card> getStock() {
        return stock;
    }

    /**
     * キャストがうぜぇ
     */
    @Override
    protected int judge(CardGameActor actor1, CardGameActor actor2) {

        BlackJackActor act1 = (BlackJackActor) actor1;
        BlackJackActor act2 = (BlackJackActor) actor2;

        return (act1.getPoint() > 21 ? 0 : act1.getPoint()) - (act2.getPoint() > 21 ? 0 : act2.getPoint());
    }

    /**
     * カードのポイントを計算する。<br>
     * <br>
     */
    @Override
    protected void calcPoint(CardGameActor actor) {
        BlackJackActor act = (BlackJackActor) actor;
        List<Card> aceList = new ArrayList<>();
        int point = 0;
        boolean hasJocker = false;
        // エース以外を合計する
        for (Card card : act.getHand()) {
            if (card.isJoker()) {
                // Jocker はワイルドカードなので最後に計算する
                hasJocker = true;
            } else {
                switch (card.getNum()) {
                case 10:
                case 11:
                case 12:
                case 13:
                    point += 10;
                    break;
                case 1:
                    aceList.add(card);
                    break;
                default:
                    point += card.getNum();
                    break;

                }
            }
        }

        // エースの処理
        // カードの合計が 10以下の場合、11として計算
        // カードの合計が 11以上の場合、1として計算
        for (Card ace : aceList) {
            if (point <= 10) {
                point += 11;
            } else {
                point += 1;
            }
        }

        // Jocker の処理
        // 21 以上の場合は 何もしない
        if (hasJocker && point < 21) {
            point = 21;
        }

        act.setPoin(point);
    }

    @Override
    public boolean turnEnd() {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    protected boolean action(CardGameActor actor) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

}
