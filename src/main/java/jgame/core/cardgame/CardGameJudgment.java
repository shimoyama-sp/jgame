package jgame.core.cardgame;

import java.util.List;

import jgame.core.Judgment;
import jgame.core.Rule;

/**
 *  ゲームの審判員<br>
 * <br>
 * @param <R> ゲームのルール
 */
public abstract class CardGameJudgment<R extends CardGameRule> implements Judgment<Rule> {

    private List<R> rules;

    public CardGameJudgment(List<R> rules) {
        this.rules = rules;
    }
    
    public List<R> getRules(){
        return this.rules;
    }

    protected abstract CardGameActor getCurrentActor();

    protected abstract List<Card> getStock();

    /**
     * 次のカードが引けるかチェックする。<br>
     * <br>
     * @return
     */
    public boolean canDraw(CardGameActor actor) {
        
        CardGameRule violateRule = getRules().stream().filter(rule -> !rule.canDraw(actor, getStock())).findFirst()
                .orElse(null);

        return (violateRule == null);
    }

    /**
     * 参加者の勝敗を決める。<br>
     * <br>
     * @param actor1
     * @param actor2
     * @return actor1 が勝者の場合「1(0 より大きい)」、引き分けの場合「0」、敗者の場合「-1(0 未満)」
     */
    protected abstract int judge(CardGameActor actor1, CardGameActor actor2);

    protected abstract void calcPoint(CardGameActor actor);

    public abstract boolean turnEnd();
    
    /**
     * ユーザの処理を行う。<br>
     * <br>
     * @return 個のユーザが続けて処理可能な場合、true を返す。
     */
    protected abstract boolean action(CardGameActor actor);
}
