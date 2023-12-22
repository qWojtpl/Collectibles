package pl.collectibles;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.collectibles.commands.Commands;
import pl.collectibles.data.DataHandler;
import pl.collectibles.events.Events;
import pl.collectibles.events.GUIEvents;
import pl.collectibles.gui.GUIHandler;
import pl.collectibles.logic.CollectiblesManager;

public final class Collectibles extends JavaPlugin {

    private static Collectibles main;
    private Commands commands;
    private GUIHandler guiHandler;
    private CollectiblesManager collectiblesManager;
    private DataHandler dataHandler;
    private Events events;
    private GUIEvents guiEvents;

    @Override
    public void onEnable() {
        main = this;
        this.commands = new Commands();
        this.guiHandler = new GUIHandler();
        this.collectiblesManager = new CollectiblesManager();
        this.dataHandler = new DataHandler();
        this.events = new Events();
        this.guiEvents = new GUIEvents();
        PluginCommand command = getCommand("collections");
        if(command != null) {
            command.setExecutor(commands);
        }
        getServer().getPluginManager().registerEvents(events, this);
        getServer().getPluginManager().registerEvents(guiEvents, this);
        dataHandler.loadAll();
        collectiblesManager.registerSaveTask();
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        guiHandler.closeAllInventories();
        dataHandler.saveData();
        getLogger().info("Disabled.");
    }

    public static Collectibles getInstance() {
        return main;
    }

    public Commands getCommands() {
        return this.commands;
    }

    public GUIHandler getGUIHandler() {
        return this.guiHandler;
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

    public GUIEvents getGUIEvents() {
        return this.guiEvents;
    }

}
