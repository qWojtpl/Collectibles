package pl.collectibles;

import org.bukkit.plugin.java.JavaPlugin;
import pl.collectibles.data.DataHandler;
import pl.collectibles.events.Events;
import pl.collectibles.logic.CollectiblesManager;

public final class Collectibles extends JavaPlugin {

    private static Collectibles main;
    private CollectiblesManager collectiblesManager;
    private DataHandler dataHandler;
    private Events events;

    @Override
    public void onEnable() {
        main = this;
        this.collectiblesManager = new CollectiblesManager();
        this.dataHandler = new DataHandler();
        this.events = new Events();
        getServer().getPluginManager().registerEvents(events, this);
        dataHandler.loadAll();
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        dataHandler.saveData();
        getLogger().info("Disabled.");
    }

    public static Collectibles getInstance() {
        return main;
    }

    public CollectiblesManager getCollectiblesManager() {
        return this.collectiblesManager;
    }

    public DataHandler getDataHandler() {
        return this.dataHandler;
    }

    public Events getEvents() {
        return this.events;
    }

}
