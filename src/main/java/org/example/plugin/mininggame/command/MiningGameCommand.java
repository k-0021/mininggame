package org.example.plugin.mininggame.command;

import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.example.plugin.mininggame.PlayerScoreData;
import org.example.plugin.mininggame.data.ExcutingPlayer;
import org.example.plugin.mininggame.Main;
import org.example.plugin.mininggame.mapper.data.PlayerScore;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 制限時間内に鉱石の採掘をして、スコアを獲得するゲームを起動するコマンド
 * スコアは鉱石の種類によって変わり採掘した鉱石の点数の合計によってスコアが変動する
 * 結果は点数、日時で保存される
 */
public class MiningGameCommand extends BaseCommand implements Listener {
    private static final String LIST = "list";
    private final Main main;
    List<ExcutingPlayer> excutingPlayerList = new ArrayList<>();
    PlayerScoreData playerScoreData = new PlayerScoreData();
    public MiningGameCommand(Main main) {this.main = main;}

    @Override
    public boolean onExecutePlayerCommand(Player player, Command command, String label, String[] args) {
            if (args.length == 1 && LIST.equals(args[0])) {
                sendPlayerList(player);
                return false;
            }
            sendTitle(player);
            ExcutingPlayer nowExcutingPlayer = getPlayerData(player);
            gamePlay(player, nowExcutingPlayer);
            return true;
    }
    @Override
    public  boolean onNPCPlayerCommand(CommandSender sender, Command command, String label, String[]args){
        return false;
    }


    private void sendTitle(Player player) {
        player.sendTitle("鉱石採掘ゲームスタート", "制限時間：3分", 50, 20, 0);
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 30, 4);
    }

    /**
     * 現在登録されているスコアの一覧をメッセージに送る
     * @param player プレイヤー
     */
    private void sendPlayerList(Player player) {
        List<PlayerScore> playerDataList = playerScoreData.selectList();
        for(PlayerScore playerScore : playerDataList) {
            player.sendMessage(playerScore.getId() + "回目" + " / "
                    + playerScore.getScore() + "点" + " / "
                    + playerScore.getRegisteredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        Block block = e.getBlock();
        Material material = block.getType();
        Player player = e.getPlayer();

        excutingPlayerList.stream()
                .filter(p -> p.getPlayer().equals(player))
                .findFirst()
                .ifPresent(p -> {
                            int point = switch (material) {
                                case DEEPSLATE_IRON_ORE -> 10;
                                case DEEPSLATE_LAPIS_ORE -> 20;
                                case DEEPSLATE_REDSTONE_ORE -> 30;
                                case DEEPSLATE_DIAMOND_ORE -> 60;
                                default -> 0;
                };
                            p.setScore(p.getScore() + point);
        });
    }

    private void gamePlay(Player player, ExcutingPlayer nowExcutingPlayer) {
        Bukkit.getScheduler().runTaskTimer(main, Runnable -> {
            if (nowExcutingPlayer.getGameTime() <= 0) {
                Runnable.cancel();

                player.sendTitle("ゲームが終了しました", "スコア：" + nowExcutingPlayer.getScore() + "点", 0, 60, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 30, 4);

                playerScoreData.insert(new PlayerScore(nowExcutingPlayer.getScore()));
                return;
            }
            nowExcutingPlayer.setGameTime(nowExcutingPlayer.getGameTime() - 1);
        }, 0,20);
    }

    /**
     * 現在実行しているプレイヤーのスコア情報を取得する
     * @param player コマンドを実行したプレイヤー
     * @return 現在実行しているプレイヤーのスコア情報
     */
    private ExcutingPlayer getPlayerData(Player player) {
        ExcutingPlayer excutingPlayer = new ExcutingPlayer(player);
       if (excutingPlayerList.isEmpty()) {
            excutingPlayer = addPlayerData(player);
        } else {
            excutingPlayer = excutingPlayerList.stream()
                    .findFirst()
                    .map(ps -> ps.getPlayer().equals(player)
                    ? ps
                    : addPlayerData(player)).orElse(excutingPlayer);
        }
        long GAME_TIME = 180;
        excutingPlayer.setGameTime(GAME_TIME);
        excutingPlayer.setScore(0);
        return excutingPlayer;
    }

    /**
     * 新規のプレイヤー情報をリストに追加する
     * @param player コマンドを実行したプレイヤー
     * @return 新規プレイヤー
     */
    private ExcutingPlayer addPlayerData(Player player) {
        ExcutingPlayer newExcutingPlayer = new ExcutingPlayer(player);
        excutingPlayerList.add(newExcutingPlayer);
        return newExcutingPlayer;
    }
}


