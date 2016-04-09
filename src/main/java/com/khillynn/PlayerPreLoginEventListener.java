package com.khillynn;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

@SuppressWarnings("deprecation")
public class PlayerPreLoginEventListener implements Listener {
    public void onPlayerPreLoginEvent(PlayerPreLoginEvent e){
        DBCollection table = Core.getMongoDB().getDatabse().getCollection("users");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("uuid", e.getUniqueId());
        DBCursor result = table.find(searchQuery);

        if(result.hasNext()){
            //the user was found
            BasicDBObject updated = new BasicDBObject();
            updated.putAll(result.next());

            //if the user's name has changed since the last time they were on the server then update it in the database
            if(updated.get("ign") != e.getName()) {
                updated.remove("ign");
                updated.put("ign", e.getName());

                BasicDBObject fUpdate = new BasicDBObject();
                fUpdate.put("$put", updated);

                table.update(searchQuery, fUpdate);
            }
        }

        if(!result.hasNext()){
            //the user wasn't found so a new recored will be added for them
            BasicDBObject newUser = new BasicDBObject();
            newUser.put("ign", e.getName());
            newUser.put("uuid", e.getUniqueId());
            newUser.put("rank", "Guest");
            newUser.put("points", 0);
            newUser.put("banned", false);
            newUser.put("banReason", null);
            table.insert(newUser);
        }
    }
}
