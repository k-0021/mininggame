package org.example.plugin.mininggame.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

/**
 * MiningGameのゲームを実行する際のプレイヤー情報を持つオブジェクト
 * ゲームをプレイした回数、合計点数、日時を情報に持つ。
 */
@Setter
@Getter
public class ExcutingPlayer {
    private Player player;
    private int score;
    private long gameTime;

    public ExcutingPlayer(Player player) {
        this.player = player;
    }
}

