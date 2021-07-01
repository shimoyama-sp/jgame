package jgame.core;

/**
 * ゲームの審判員を定義するためのスーパークラス。<br>
 * <br>
 * このインタフェイスはゲームにおける審判員の役割を果たす。<br>
 * ゲームの勝敗等、ゲーム振興における判断を行うことを責務とする。<br>
 */
public interface Judgment<R extends Rule> {

    /**
     * ゲーム開始の準備ができている場合、true を返す。<br>
     * <br>
     * @return
     */
    public boolean canStart();
    
    /**
     * 参加者のターンが完了したかを判断する。<br>
     * <br>
     * @return 参加者のターンが終了していたら true を返す
     */
    public boolean turnEnd();
}
