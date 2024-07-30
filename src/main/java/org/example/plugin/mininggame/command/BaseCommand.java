package org.example.plugin.mininggame.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;

/**
 * コマンドを実行して動かすプラグイン処理の基底クラス
 */
public abstract class  BaseCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            onExecutePlayerCommand(player, command, label, args);
        } else if (sender instanceof NPC) {
            onNPCPlayerCommand(sender, command, label, args);
        }
        return true;
    }

    public abstract boolean onExecutePlayerCommand(Player player, Command command, String label, String[]args);
    public abstract boolean onNPCPlayerCommand(CommandSender sender, Command command, String label, String[]args);
}