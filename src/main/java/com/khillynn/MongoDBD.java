package com.khillynn;

public class MongoDBD {
    public static String username = Core.getPlugin().getConfig().getString("mongo.username");
    public static String password = Core.getPlugin().getConfig().getString("mongo.password");
    public static String database = Core.getPlugin().getConfig().getString("mongo.database");
    public static String host = Core.getPlugin().getConfig().getString("mongo.host");
    public static int port = Core.getPlugin().getConfig().getInt("mongo.port");
}
