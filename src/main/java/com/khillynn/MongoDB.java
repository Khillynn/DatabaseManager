package com.khillynn;

import com.mongodb.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("deprecation")
public class MongoDB {
    //This will work with only one database

    private Mongo mongo = null;
    private DB database = null;

    public Mongo getMongo(){
        if(mongo == null){
            //Need to fix: NoClassDefFoundError: com/mongodb/MongoCredential
            MongoCredential credential = MongoCredential.createCredential(MongoDBD.username, MongoDBD.database, MongoDBD.password.toCharArray());
            mongo = new MongoClient(new ServerAddress(MongoDBD.host, MongoDBD.port), Arrays.asList(credential));
        }

        return mongo;
    }

    //this is for registering
    public MongoDB(String username, String password, String database2, String host, int port){
        mongo = new MongoClient(new ServerAddress(host, port));
    }

    //getter for the database
    public DB getDatabase(){
        if(database == null) {
            database = getMongo().getDB(MongoDBD.database);
        }

        return database;
    }

    public int getUserPoints(Player player){
        int points = 0;

        if(getUser(player) != null) {
            points = (int) getUser(player).get("points");
            System.out.println(" ++++++++++ " +  player.getName() + "'s points are " + points);
        }
        else{
            System.out.println(" ++++++++++ ERROR: Player not found...defaulting points to " + points + ".");
        }

        return points;
    }

    public void incUserPoints(Player player, int incAmt) {
        DBCollection table = getTable("users");

        //the user was found
        if(getUser(player) != null) {
            System.out.println(" ++++++++++ The user " + player.getName() + " was found.");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("uuid", player.getUniqueId().toString());
            BasicDBObject fUpdate = new BasicDBObject();
            fUpdate.put("$inc", new BasicDBObject("points", incAmt));

            table.update(searchQuery, fUpdate);
        }

        System.out.println(" ++++++++++ incUserPoints() ran with the player: " + player.getName());
    }

    public DBObject getUser(Player player){
        DBCollection table = getTable("users");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("uuid", player.getUniqueId().toString());

        return table.findOne(searchQuery);
    }

    public DBCollection getTable(String tableName){
        return Core.getMongoDB().getDatabase().getCollection(tableName);
    }

    //connection
    public void setDatabase(String db){
        database = getMongo().getDB(db);
    }

    public void closeConnection(){
        if(mongo != null) {
            mongo.close();
        }
    }
}
