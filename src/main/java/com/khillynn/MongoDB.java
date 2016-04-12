package com.khillynn;

import com.mongodb.*;

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

            System.out.println("mongo was null in getMongo()");
        }

        System.out.println(" ********** I called getMongo()");
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
            System.out.println(" ********* database was null");
        }

        System.out.println(" ********** getDatabase() was called");
        return database;
    }

    //connection
    public void setDatabase(String db){
        database = getMongo().getDB(db);
    }

    public void closeConnection(){
        if(mongo != null) {
            mongo.close();
            System.out.println(" ********** goodbye mongo");
        }
    }
}
