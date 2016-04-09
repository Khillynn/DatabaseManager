package com.khillynn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private static Core plugin;
    private static MongoDB mongoDB;
    private static PluginManager pm;

    @Override
    public void onEnable(){
        plugin = this;
        pm = Bukkit.getPluginManager();
        getConfig().options().copyDefaults(true);
        saveConfig();

        MongoDB mdb = new MongoDB(MongoDBD.username, MongoDBD.password, MongoDBD.database, MongoDBD.host, MongoDBD.port);
        mdb.setDAtabase(MongoDBD.database);
        mongoDB = mdb;

        regListeners();
    }

    @Override
    public void onDisable(){
        getMongoDB().closeConnection();
    }

    public static Core getPlugin(){
        return plugin;
    }

    public static MongoDB getMongoDB(){
        return mongoDB;
    }

    public PluginManager getPluginManagers(){
        return pm;
    }

    void regListeners(){
        getPluginManagers().registerEvents(new PlayerPreLoginEventListener(), this);
    }


}