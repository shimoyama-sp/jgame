package jgame.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ゲームを定義するためのスーパークラス<br>
 * <br>
 * springboot で起動する際、initial で startUp を呼び出し、destroy で exit を呼び出すとかを考えている。<br>
 * <br>
 * @author shimoyama
 */
public abstract class Game<A extends Actor, J extends Judgment<? extends Rule>> {

    private static final Logger logger = LogManager.getLogger(Game.class);

    private J judgement;

    /**
     * サブクラスのエラー回避用のコンストラクタ<br>
     * <br>
     */
    protected Game() {
    }
    
    /**
     * 通常はこちらの形式でコンストラクタを呼び出す。<br>
     * <br>
     * @param j
     */
    protected Game(J j) {
        this.judgement = j;
    }
    
    protected J getJudgement() {
        return judgement;
    }

    /**
     * 
     * ゲーム開始時に起動する処理を定義する。<br>
     * <br>
     * @return 正常に開始できない場合、false を返す。
     */
    protected abstract boolean startUp();

    /**
     * ゲーム終了時に行う処理を定義する。<br>
     * <br>
     * 例えばログアウト処理とか。
     */
    protected abstract void gameOver();

    /**
     * ゲームを開始する。<br>
     * <br>
     */
    public abstract void start();
    
    /**
     * ゲームを実行する。
     */
    protected abstract void play();

    /**
     * ゲームを終了する。<br>
     * <br>
     */
    public abstract boolean end();

    /**
     * 
     */
    public void run() {
        startUp();

        do {
            start();

            play();
            
        } while (end());
        
        gameOver();
    }
}
