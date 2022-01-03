package com.fiyerwork.randomtp;

import org.bukkit.plugin.java.JavaPlugin;

public class RandomTP extends JavaPlugin {
    private static RandomTP instance;
    private static Configuration config;
    @Override
    public void onEnable() {
        instance = this;
        config = new Configuration();

        getCommand("randomtp").setExecutor(new CommandRandomTP());
    }

    public static RandomTP getInstance() {
        return instance;
    }

    public static Configuration getPluginConfig() {
        return config;
    }
}
