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
        getLogger().info("DatabaseManager is Enabled! =D");
        getConfig().options().copyDefaults(true);
        saveConfig();

        MongoDB mdb = new MongoDB(MongoDBD.host, MongoDBD.port);
        mdb.setDatabase(MongoDBD.database);
        mongoDB = mdb;
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

    /*void regListeners(){
        getPluginManagers().registerEvents(new PlayerLoginEventListener(), this);
    }*/


}
