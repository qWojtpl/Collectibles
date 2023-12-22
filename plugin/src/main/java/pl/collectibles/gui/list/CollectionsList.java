package pl.collectibles.gui.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.collectibles.Collectibles;
import pl.collectibles.gui.PluginGUI;
import pl.collectibles.logic.CollectiblesManager;
import pl.collectibles.logic.Collection;

import java.util.List;

public class CollectionsList extends PluginGUI {

    public CollectionsList(Player owner) {
        super(owner, "Collections", 54);
    }

    @Override
    public void onOpen() {
        setGUIProtected(true);
        fillWith(Material.BLACK_STAINED_GLASS_PANE);
        loadCollections();
    }

    private void loadCollections() {
        CollectiblesManager collectiblesManager = Collectibles.getInstance().getCollectiblesManager();
        int i = 0;
        for(Collection c : collectiblesManager.getCollections()) {
            List<String> lore = c.getLore();
            lore.add(" ");
            lore.add("§a- §f" + collectiblesManager.getPlayerScore(getOwner().getName(), c) + "§e/" + "§6" + collectiblesManager.getGoal(c));
            setSlot(i++, c.getIcon(), c.getViewName(), lore);
        }
    }

}
