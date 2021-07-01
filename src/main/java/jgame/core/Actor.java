package jgame.core;

/**
 * ゲームをする人 (参加者) を定義するためのスーパークラス。<br>
 * <br>
 */
public interface Actor {

    /**
     * プレイヤー名を取得する。<br>
     * <br>
     * インタフェイスの名前が Actor なのに、取得するのは「Player」って・・・みたいな突っ込みがあるかもしれないが、
     * ここはあくまでもプレイヤーとして参加している「Actor (参加者)」の名称、という意味です。<br>
     * 実装クラスがどう使うか、までは知らんがな～<br>
     * 
     * @return プレイヤー名
     */
    public String getPlayerName();
}
