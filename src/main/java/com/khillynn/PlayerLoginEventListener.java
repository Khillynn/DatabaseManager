package com.khillynn;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

@SuppressWarnings("deprecation")
public class PlayerLoginEventListener implements Listener {
    public void onPlayerLoginEvent(PlayerLoginEvent e){
        DBCollection table = Core.getMongoDB().getDatabase().getCollection("users");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("uuid", e.getPlayer().getUniqueId().toString());
        DBObject result = table.findOne(searchQuery);

        //the user was found
        if(result != null){

            //if the user's name has changed since the last time they were on the server then update it in the database
            if(result.get("ign") == null || !e.getPlayer().getName().equals(result.get("ign"))) {
                BasicDBObject fUpdate = new BasicDBObject();
                fUpdate.put("$set", new BasicDBObject("ign", e.getPlayer().getName()));

                table.update(searchQuery, fUpdate);
            }
            System.out.println("YAY I FOUND A PLAYER'S INFO");
        }

        else{
            //the user wasn't found so a new record will be added for them
            BasicDBObject newUser = new BasicDBObject();
            newUser.put("ign", e.getPlayer().getName());
            newUser.put("uuid", e.getPlayer().getUniqueId().toString());
            newUser.put("rank", "Guest");
            newUser.put("points", 0);
            newUser.put("banned", false);
            newUser.put("banReason", null);
            table.insert(newUser);
            System.out.println("YAY I ADDED A PLAYER'S INFO");
        }
    }
}
