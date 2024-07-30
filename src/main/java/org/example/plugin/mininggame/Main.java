package org.example.plugin.mininggame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.plugin.mininggame.command.MiningGameCommand;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        MiningGameCommand miningGameCommand = new MiningGameCommand(this);
        Bukkit.getPluginManager().registerEvents(miningGameCommand, this);
        getCommand("miningGame").setExecutor(miningGameCommand);
    }
}
