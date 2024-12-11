package me.shiqui.compassBar.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CompassCommand implements CommandExecutor {
    private final HashMap<UUID, BossBar> compassBars;
    private final FileConfiguration config;
    private final MiniMessage mm;

    public CompassCommand(HashMap<UUID, BossBar> compassBars, FileConfiguration config) {
        this.compassBars = compassBars;
        this.config = config;
        this.mm = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length != 0) {
            Component msg = mm.deserialize(config.getString("prefix") + config.getString("msg.error"));
            player.sendMessage(msg);
            return true;
        }

        if (compassBars.containsKey(player.getUniqueId())) {
            compassBars.get(player.getUniqueId()).removeAll();
            compassBars.remove(player.getUniqueId());
            Component msg = mm.deserialize(config.getString("prefix") + config.getString("msg.toggled-off"));
            player.sendMessage(msg);
        } else {
            compassBars.put(player.getUniqueId(), Bukkit.createBossBar("Compass", BarColor.WHITE, BarStyle.SOLID));
            compassBars.get(player.getUniqueId()).addPlayer(player);
            Component msg = mm.deserialize(config.getString("prefix") + config.getString("msg.toggled-on"));
            player.sendMessage(msg);
        }
        return true;
    }
}
