package jgame.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jgame.core.cardgame.Card;
import jgame.core.cardgame.CardGame;
import jgame.core.cardgame.CardGameActor.ActorMode;
import jgame.core.cardgame.CardGameJudgment;
import jgame.core.cardgame.CardGameRule;

/**
 * 
 * BlackJack<br>
 * <br>
 * CUI ベースの BlackJack を実施します。<br>
 * <br>
 * 
 * https://ja.wikipedia.org/wiki/%E3%83%96%E3%83%A9%E3%83%83%E3%82%AF%E3%82%B8%E3%83%A3%E3%83%83%E3%82%AF
 * 
 * @author shimoyama.sp
 *
 */
public class BlackJack<A extends BlackJackActor, J extends BlackJackJudgment<BlackJackRule<? extends BlackJackActor>>>
        extends CardGame<A, CardGameJudgment<CardGameRule<A>>> {

    private static final Logger logger = LogManager.getLogger(BlackJack.class);

    /** 参加者 */
    private List<BlackJackActor> actors = new ArrayList<>();

    /** 山札 */
    private List<Card> stock;

    /** 審判員 */
    private BlackJackJudgment<BlackJackRule<BlackJackActor>> judgment;

    private Scanner scan = new Scanner(System.in);

    /**
     * 
     * @param judgement
     */
    public BlackJack(BlackJackJudgment<BlackJackRule<BlackJackActor>> judgement) {
        this.judgment = judgement;
    }

    /**
     * 1ゲーム分、ゲームを行う。<br>
     * <br>
     */
    @Override
    protected void play() {
        // ここから Player ターン
        for (BlackJackActor actor : actors) {
            if (actor.getActorMode() == ActorMode.DEALER) {
                break;
            }
            System.out.println(actor.getPlayerName() + " さんのターンです。[ポイント :" + actor.getPoint() + "]");
            while (judgment.canDraw(actor)) {
                String val = inputString("カードを引きますか？[Y|N]");
                if ("y".equals(val) || "Y".equals(val)) {
                    actor.addCard(deal());
                    judgment.calcPoint(actor);
                    showCard(actor);
                } else {
                    break;
                }
            }
        }

        // ここからディーラーターン
        // 最後の actor がディーラーになっている
        BlackJackActor dealer = actors.get(actors.size() - 1);
        // この辺が戦略 (AI) として機能するが、とりあえずは適当にやる
        while (judgment.canDraw(dealer) && dealer.getPoint() < 15) {
            dealer.addCard(deal());
            judgment.calcPoint(dealer);
        }
        showCard(dealer);

        // 全員終わったので、勝敗を表示する
        System.out.println("結果発表 ------------------------------------------------------------");
        for (int i = 0; i < actors.size() - 1; i++) {
            BlackJackActor actor = actors.get(i);
            int judge = judgment.judge(dealer, actor);
            if (judge < 0) {
                System.out.println(actor.getPlayerName() + " - [ WIN ]");
            } else if (judge == 0) {
                System.out.println(actor.getPlayerName() + " - [ DRAW ]");
            } else {
                System.out.println(actor.getPlayerName() + " - [ LOSE ]");
            }
        }
        System.out.println("");
    }

    private void showCard(BlackJackActor actor) {
        System.out.println(actor.getPlayerName() + "さんの手札");
        for (Card card : actor.getHand()) {
            showCard(card);
        }
        if (actor.getPoint() > 21) {
            System.out.println("合計：" + actor.getPoint() + " [bust]\n");
        } else {
            System.out.println("合計：" + actor.getPoint() + "\n");
        }
    }

    /**
     * カードを表示する。<br>
     * <br>
     * @param card
     */
    private void showCard(Card card) {
        showCard(card, true);
    }

    /**
     * カードを表示する。<br>
     * <br>
     * ただし、open フラグが false の場合は「*」が表示される。<br>
     * <br>
     * @param card
     * @param open
     */
    private void showCard(Card card, boolean open) {
        if (!open) {
            System.out.println("[***]");
            return;
        }

        if (card.isJoker()) {
            System.out.println("[Jocker]");
        }

        System.out.print("");
        switch (card.getSuit()) {
        case CLUB:
            System.out.print("[♣");
            break;
        case DIAMOND:
            System.out.print("[♦");
            break;
        case HEART:
            System.out.print("[♥");
            break;
        case SPADE:
            System.out.print("[♠");
            break;
        }
        switch (card.getNum()) {
        case 10:
            System.out.println("10]");
            break;
        case 11:
            System.out.println(" J]");
            break;
        case 12:
            System.out.println(" Q]");
            break;
        case 13:
            System.out.println(" K]");
            break;
        case 1:
            System.out.println(" A]");
            break;
        default:
            System.out.println(" " + card.getNum() + "]");
        }
    }

    /**
     * 1ゲームが終了した時点でゲームを続行するかを確認する。<br>
     * <br>
     * @return 続行する場合、true を返す。
     */
    protected boolean continueGame() {
        String val = inputString("ゲームを継続しますか？[Y|N]");
        return ("y".equals(val) || "Y".equals(val));
    }

    /**
     * ゲームを終了する。<br>
     * <br>
     */
    protected void endGame() {
        System.out.println("お疲れさまでした。");
    }

    /**
     * 画面から数値を入れてもらう。<br>
     * <br>
     * @param message
     * @return
     */
    private int inputNum(String message) {
        System.out.println(message);

        int num = 0;
        do {
            //Scanner scan = new Scanner(System.in);
            String line = scan.nextLine();
            line = line.trim();

            try {
                num = Integer.valueOf(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println(message);
            }

        } while (true);

        return num;
    }

    /**
     * ジョーカー無しで山札を作成する。<br>
     * <br>
     * @return
     */
    protected List<Card> setupStock() {
        return setupStock(0);
    }

    /**
     * 画面から文字列を入れてもらう。<br>
     * <br>
     * @param message
     * @return
     */
    private String inputString(String message) {
        System.out.println(message);

        do {
            //Scanner scan = new Scanner(System.in);
            String line = scan.nextLine();
            line = line.trim();

            if (line.length() > 0) {
                return line;
            }
        } while (true);
    }

    /**
     * 現在の山札を返す。<br>
     * <br>
     */
    @Override
    protected List<Card> getStock() {
        return stock;
    }

    @Override
    protected Card drawCard() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    /**
     * 参加者をすべて取得する。<br>
     * <br>
     * 最後の参加者がディーラーになります。<br>
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<A> getActors() {
        // 邪道・・
        return actors.stream().map(actor -> (A) actor).collect(Collectors.toList());
    }

    /**
     * ゲームの初期設定を行う。<br>
     * <br>
     * プレイヤーを設定する。
     */
    @Override
    public boolean startUp() {

        int playerCount = inputNum("プレイヤーの人数を入力してください。");

        for (int i = 1; i <= playerCount; i++) {
            String name = inputString("プレイヤー" + i + "の名前を入力してください。");
            actors.add(new BlackJackActor(ActorMode.PLAYER, name));
        }

        // 最後にディーラーを設定する
        actors.add(new BlackJackActor(ActorMode.DEALER, "ディーラー"));

        return true;
    }

    @Override
    public void gameOver() {
        System.out.println("お疲れさまでした。");
        scan.close();
    }

    /**
     * ゲームを開始する。<br>
     * <br>
     * ここでゲーム開始状態を作成する。<br>
     * <br>
     */
    @Override
    public void start() {

        // 山札を作る
        stock = setupStock();
        Collections.shuffle(stock);

        judgment.init(stock);

        // プレイヤーの手札をリセットする
        for (BlackJackActor actor : actors) {
            actor.reflashHand();
        }

        // 最低2枚は配布する必要がある
        // 順番に1枚ずつ配る (2枚ずつではない)
        for (int i = 0; i < 2; i++) {
            for (BlackJackActor actor : actors) {
                actor.addCard(deal());
            }
        }

        // 初期配置した手札を公開する
        for (BlackJackActor actor : actors) {
            judgment.calcPoint(actor);
            switch (actor.getActorMode()) {
            case PLAYER:
                showCard(actor);
                break;
            case DEALER:
                System.out.println(actor.getPlayerName() + " さんの手札");
                showCard(actor.getHand().get(0));
                showCard(actor.getHand().get(1), false);
                break;
            }
        }
        System.out.println("----------");
    }

    /**
     * 1ゲーム終了したら呼び出される。<br>
     * <br>
     */
    @Override
    public boolean end() {
        return continueGame();
    }

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        new BlackJack(new BlackJackJudgment<>(
                Arrays.asList((new BlackJackRule<BlackJackActor>())))).run();
    }
}
