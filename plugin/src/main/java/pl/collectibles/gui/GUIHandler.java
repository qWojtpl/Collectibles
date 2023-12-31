package pl.collectibles.gui;

import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GUIHandler {

    private final List<PluginGUI> guiList = new ArrayList<>();

    public void registerInventory(PluginGUI gui) {
        guiList.add(gui);
    }

    public void removeInventory(PluginGUI gui) {
        if(gui == null) {
            return;
        }
        gui.onClose();
        guiList.remove(gui);
    }

    @Nullable
    public PluginGUI getGUIByInventory(Inventory inventory) {
        for(PluginGUI gui : guiList) {
            if(gui.getInventory().equals(inventory)) {
                return gui;
            }
        }
        return null;
    }

    public void closeAllInventories() {
        for(PluginGUI gui : guiList) {
            gui.closeInventory();
        }
    }

}