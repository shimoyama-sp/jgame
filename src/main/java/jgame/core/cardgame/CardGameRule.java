package jgame.core.cardgame;

import java.util.List;

import jgame.core.Rule;

/**
 * ゲームのルールを定義するためのインタフェイス
 */
public interface CardGameRule<A extends CardGameActor> extends Rule {

    /**
     * 次のカードを引くことができるかどうかを判定する。<br>
     * <br>
     * @param actor ユーザ情報
     * @param stock デッキ情報
     * @return カードを引くことが可能な場合、true を返す。<br>
     */    
    boolean canDraw(A actor, List<Card> stock);
}
