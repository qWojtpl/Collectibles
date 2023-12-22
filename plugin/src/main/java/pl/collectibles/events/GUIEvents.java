package pl.collectibles.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import pl.collectibles.Collectibles;
import pl.collectibles.gui.GUIHandler;
import pl.collectibles.gui.PluginGUI;

public class GUIEvents implements Listener {

    private final GUIHandler guiHandler = Collectibles.getInstance().getGUIHandler();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        PluginGUI gui = guiHandler.getGUIByInventory(event.getClickedInventory());
        if(gui == null) {
            return;
        }
        gui.onClick(event.getSlot());
        gui.onClick(event.getSlot(), event.isRightClick());
        if(gui.isGuiProtected()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        PluginGUI gui = guiHandler.getGUIByInventory(event.getInventory());
        if(gui == null) {
            return;
        }
        if(gui.isGuiProtected()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        guiHandler.removeInventory(guiHandler.getGUIByInventory(event.getInventory()));
    }

}
