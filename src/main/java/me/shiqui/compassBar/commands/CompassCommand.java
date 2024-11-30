package me.shiqui.compassBar.commands;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CompassCommand implements CommandExecutor {
    private final HashMap<UUID, BossBar> compassBars;

    public CompassCommand(HashMap<UUID, BossBar> compassBars) {
        this.compassBars = compassBars;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length != 0) {
            player.sendMessage("Invalid syntax");
            return true;
        }

        if (compassBars.containsKey(player.getUniqueId())) {
            compassBars.get(player.getUniqueId()).removeAll();
            compassBars.remove(player.getUniqueId());
            player.sendMessage("[CompassBar] Compass toggled off");
        } else {
            compassBars.put(player.getUniqueId(), Bukkit.createBossBar("Compass", BarColor.BLUE, BarStyle.SOLID));
            compassBars.get(player.getUniqueId()).addPlayer(player);
            player.sendMessage("[CompassBar] Compass toggled on");
        }
        return true;
    }
}
