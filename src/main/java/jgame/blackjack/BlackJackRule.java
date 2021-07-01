package jgame.blackjack;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jgame.core.cardgame.Card;
import jgame.core.cardgame.CardGameActor.ActorMode;
import jgame.core.cardgame.CardGameRule;

/**
 * BlackJack のルールを定義する。<br>
 * <br>
 * 以下のルール。<br>
 * <ul>
 * <li>プレイヤーは手札の合計が 21 以上の場合、ヒットすることができません。
 * <li>ディーラーは手札の合計が 17 以上の場合、ヒットすることができません。
 * </li>
 *
 */
public class BlackJackRule<A extends BlackJackActor>  implements CardGameRule<A> {

    private static final Logger logger = LogManager.getLogger(BlackJackRule.class);
    
    /**
     * ヒットすることが可能かを判定します。<br>
     * <br>
     * BjackJack では追加カードを要請することを「ヒット」と呼ぶ。<br>
     * <br>
     */
    @Override
    public boolean canDraw(A actor, List<Card> stock) {
        logger.debug("point = {}", actor.getPoint());

        // ディーラーが特別仕様
        if (actor.getActorMode() == ActorMode.DEALER) {
            return (actor.getPoint() < 17);
        } else {
            return (actor.getPoint() < 21);
        }
    }
}
