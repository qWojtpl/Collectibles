package pl.collectibles.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.collectibles.Collectibles;
import pl.collectibles.logic.CollectiblesManager;

public class Events implements Listener {

    private final Collectibles plugin = Collectibles.getInstance();
    private final CollectiblesManager collectiblesManager = plugin.getCollectiblesManager();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) {
            return;
        }
        collectiblesManager.registerClick(event.getPlayer().getName(), event.getClickedBlock().getLocation());
    }

}
