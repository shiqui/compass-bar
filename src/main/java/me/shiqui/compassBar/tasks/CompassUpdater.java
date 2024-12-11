package me.shiqui.compassBar.tasks;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static java.lang.Math.max;

public class CompassUpdater extends BukkitRunnable {
    private final HashMap<UUID, BossBar> compassBars;
    private final int barHalfWidth;
    private final String barString;
    private final int baseIndex;

    public CompassUpdater(HashMap<UUID, BossBar> compassBars, FileConfiguration config) {
        this.compassBars = compassBars;
        String barString = config.getString("bar.string");
        if (barString == null) {
            barString = "N   |   |   |   NE   |   |   |   E   |   |   |   ES   |   |   |   S   |   |   |   SW   |   |   |   W   |   |   |   WN   |   |   |   ";
        }
        this.baseIndex = barString.length();
        this.barString = barString + barString + barString;
        this.barHalfWidth = (int) (max(config.getInt("bar.width"), barString.length()) / 2);

    }

    @Override
    public void run() {
        for (UUID uuid : compassBars.keySet()) {
            BossBar bar = compassBars.get(uuid);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                float yaw = player.getYaw() + 180;
                int centerIndex = this.baseIndex + (int) ((yaw / 360) * this.baseIndex);
                bar.setTitle(this.barString.substring(centerIndex - this.barHalfWidth, centerIndex + this.barHalfWidth));
            } else {
                compassBars.remove(uuid);
            }
        }
    }
}
