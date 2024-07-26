package org.example.plugin.mininggame.command;

import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.example.plugin.mininggame.PlayerScoreData;
import org.example.plugin.mininggame.data.PlayerData;
import org.example.plugin.mininggame.Main;
import org.example.plugin.mininggame.mapper.data.PlayerScore;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MiningGameCommand implements Listener, CommandExecutor {
    int score = 0;
    int count = 0;
    private final long GAME_TIME = 180;
    public static final String LIST = "list";
    private final Main main;
    List<PlayerData> playerDataList = new ArrayList<>();
    PlayerScoreData playerScoreData = new PlayerScoreData();
    public MiningGameCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1 && LIST.equals(args[0])) {
                sendPlayerList(player);
            }
            sendTitle(player);
            PlayerData nowPlayerData = getPlayerData(player);
            gamePlay(player, nowPlayerData);
            return true;
        } else {
            return false;
        }
    }

    /**
     * コマンドを実行したら画面にゲームが開始したことを表示する
     * @param player プレイヤー
     */
    private void sendTitle(Player player) {
        player.sendTitle("鉱石採掘ゲームスタート", "制限時間：3分", 50, 20, 0);
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 30, 4);
    }
    private void sendPlayerList(Player player) {
        List<PlayerScore> playerDataList = playerScoreData.selectList();
        for(PlayerScore playerScore : playerDataList) {
            player.sendMessage(playerScore.getCount() + " / "
                    + playerScore.getScore() + " / "
                    + playerScore.getRegistered_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        Block block = e.getBlock();
        Material material = block.getType();
        Player player = e.getPlayer();

        playerDataList.stream()
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

    private void gamePlay(Player player, PlayerData nowPlayerData) {
        Bukkit.getScheduler().runTaskTimer(main, Runnable -> {
            if (nowPlayerData.getGameTime() <= 0) {
                Runnable.cancel();

                player.sendTitle("ゲームが終了しました", "スコア：" + nowPlayerData.getScore() + "点", 0, 60, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 30, 4);

                count++;
                PlayerScore playerScore = new PlayerScore(count,nowPlayerData.getScore());
                return;
            }
            nowPlayerData.setGameTime(nowPlayerData.getGameTime() - 1);
        }, 0,20);

        playerScoreData.insert(new PlayerScore(nowPlayerData.getCount(),nowPlayerData.getScore()));

    }
    private  PlayerData getPlayerData(Player player) {
        PlayerData playerData = new PlayerData(player);
       if (playerDataList.isEmpty()) {
            playerData = addPlayerData(player);
        } else {
            playerData = playerDataList.stream()
                    .findFirst()
                    .map(ps -> ps.getPlayer().equals(player)
                    ? ps
                    : addPlayerData(player)).orElse(playerData);
        }
        playerData.setGameTime(GAME_TIME);
        playerData.setScore(0);
        return playerData;
    }
    private PlayerData addPlayerData(Player player) {
        PlayerData newPlayerData = new PlayerData(player);
        playerDataList.add(newPlayerData);
        return newPlayerData;
    }
}


