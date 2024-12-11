package me.shiqui.compassBar;

import me.shiqui.compassBar.commands.CompassCommand;
import me.shiqui.compassBar.tasks.CompassUpdater;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class CompassBar extends JavaPlugin {
    private final HashMap<UUID, BossBar> compassBars = new HashMap<>();

    @Override
    public void onEnable() {
        // Load config
        saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        int updateInterval = config.getInt("bar.refresh-rate");

        // Register commands
        Objects.requireNonNull(getCommand("compass")).setExecutor(new CompassCommand(compassBars, config));

        // Start Updater
        new CompassUpdater(compassBars, config).runTaskTimer(this, 0, updateInterval);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
