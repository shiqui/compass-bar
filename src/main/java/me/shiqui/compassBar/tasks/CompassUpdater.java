package me.shiqui.compassBar.tasks;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CompassUpdater extends BukkitRunnable {
    private final HashMap<UUID, BossBar> compassBars;

    public CompassUpdater(HashMap<UUID, BossBar> compassBars) {
        this.compassBars = compassBars;
    }

    @Override
    public void run() {
        for (UUID uuid : compassBars.keySet()) {
            BossBar bar = compassBars.get(uuid);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                float yaw = player.getYaw() + 180;
                String barString = "S   |   |   |   SW   |   |   |   W   |   |   |   WN   |   |   |   N   |   |   |   NE   |   |   |   E   |   |   |   ES   |   |   |   S   |   |   |   SW   |   |   |   W   |   |   |   WN   |   |   |   N   |   |   |   NE   |   |   |   E   |   |   |   ES   |   |   |   ";
                int barStringLength = 132;
                int startIndex = (int) ((yaw / 360) * barStringLength);
                bar.setTitle(barString.substring(startIndex, startIndex + barStringLength));
            } else {
                compassBars.remove(uuid);
            }
        }
    }
}
