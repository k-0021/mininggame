package org.example.plugin.mininggame.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Setter
@Getter
public class PlayerData {
    private int count;
    private Player player;
    private int score;
    private long gameTime;

    public PlayerData(Player player) {
        this.player = player;
    }
}

