package me.shiqui.compassBar;

import me.shiqui.compassBar.commands.CompassCommand;
import me.shiqui.compassBar.tasks.CompassUpdater;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class CompassBar extends JavaPlugin {
    private final HashMap<UUID, BossBar> compassBars = new HashMap<>();

    @Override
    public void onEnable() {
//        saveDefaultConfig();
        Objects.requireNonNull(getCommand("compass")).setExecutor(new CompassCommand(compassBars));
        new CompassUpdater(compassBars).runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
