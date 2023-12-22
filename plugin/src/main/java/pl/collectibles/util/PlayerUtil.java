package pl.collectibles.util;

import org.bukkit.entity.Player;
import pl.collectibles.Collectibles;

public class PlayerUtil {

    public static Player getPlayer(String name) {
        for(Player p : Collectibles.getInstance().getServer().getOnlinePlayers()) {
            if(p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

}
