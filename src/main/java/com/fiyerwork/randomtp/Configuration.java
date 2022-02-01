package com.fiyerwork.randomtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {
    private File file;
    private int minx;
    private int maxx;
    private int minz;
    private int maxz;

    private String defaultWorld;

    private String msgTeleportSelf;
    private String msgTeleportOther;
    private String msgPermissionError;
    private String msgPlayerNotFound;

    protected Configuration() {
        File dir = RandomTP.getInstance().getDataFolder();
        if(!dir.exists())
            dir.mkdir();
        File file = new File(dir, "config.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        this.file = file;
        load();
        save();
    }

    public void load() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        defaultWorld = config.getString("default.world", Bukkit.getWorlds().get(0).getName());
        minx = config.getInt("default.min.x", -10000);
        maxx = config.getInt("default.max.x", 10000);
        minz = config.getInt("default.min.z", -10000);
        maxz = config.getInt("default.max.z", 10000);

        msgTeleportSelf = config.getString("messages.teleport-self", "&aTeleporting to random location.");
        msgTeleportOther = config.getString("messages.teleport-other","&aTeleporting %s to random location.");
        msgPlayerNotFound = config.getString("messages.player-not-found", "&cCould not find player %s.");
        msgPermissionError = config.getString("messages.permission-error", "&cYou do not have permission to perform this command.");
    }

    public void save() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("default.world", defaultWorld);
        config.set("default.min.x", minx);
        config.set("default.max.x", maxx);
        config.set("default.min.z", minz);
        config.set("default.max.z", maxz);
        config.set("messages.teleport-self", msgTeleportSelf);
        config.set("messages.teleport-other", msgTeleportOther);
        config.set("messages.player-not-found", msgPlayerNotFound);
        config.set("messages.permission-error", msgPermissionError);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMinX() {
        return minx;
    }

    public int getMaxX() {
        return maxx;
    }

    public int getMinZ() {
        return minz;
    }

    public int getMaxZ() {
        return maxz;
    }

    public String getDefaultWorld() {
        return defaultWorld;
    }

    public String getTeleportSelfMsg() {
        return ChatColor.translateAlternateColorCodes('&', msgTeleportSelf);
    }

    public String getTeleportOtherMsg(String string) {
        return ChatColor.translateAlternateColorCodes('&', String.format(msgTeleportOther, string));
    }

    public String getPermissionErrorMsg() {
        return ChatColor.translateAlternateColorCodes('&', msgPermissionError);
    }

    public String getPlayerNotFoundMsg(String string) {
        return ChatColor.translateAlternateColorCodes('&', String.format(msgPlayerNotFound, string));
    }
}
