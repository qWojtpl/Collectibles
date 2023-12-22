package pl.collectibles.util;

import org.bukkit.Location;
import org.bukkit.World;
import pl.collectibles.Collectibles;

public class LocationUtil {

    public static Location parseLocation(String locationString) {
        String[] split = locationString.split(" ");
        if(split.length != 4) {
            Collectibles.getInstance().getLogger().severe("Location " + locationString + " is wrong!");
            return null;
        }
        int x, y, z;
        try {
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            z = Integer.parseInt(split[2]);
        } catch(NumberFormatException e) {
            Collectibles.getInstance().getLogger().severe("Location " + locationString + " is wrong!");
            return null;
        }
        World w = Collectibles.getInstance().getServer().getWorld(split[3]);
        if(w == null) {
            Collectibles.getInstance().getLogger().severe("Location " + locationString + " is wrong!");
            return null;
        }
        return new Location(w, x, y, z);
    }

}
