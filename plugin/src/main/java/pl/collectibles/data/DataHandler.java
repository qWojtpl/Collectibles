package pl.collectibles.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.collectibles.Collectibles;
import pl.collectibles.logic.CollectiblesManager;
import pl.collectibles.logic.Collection;
import pl.collectibles.logic.PlayerProfile;
import pl.collectibles.util.LocationUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private final Collectibles plugin = Collectibles.getInstance();
    private final CollectiblesManager collectiblesManager = plugin.getCollectiblesManager();
    private YamlConfiguration data;

    public void loadAll() {
        collectiblesManager.clearCollections();
        collectiblesManager.clearProfiles();
        loadConfig();
        loadData();
    }

    public void loadConfig() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getFile("config.yml"));
        ConfigurationSection collectionsSection = yml.getConfigurationSection("collections");
        if(collectionsSection == null) {
            return;
        }
        for(String collectionName : collectionsSection.getKeys(false)) {
            String path = "collections." + collectionName + ".";
            Collection collection = new Collection(collectionName);
            collection.setViewName(yml.getString(path + "name", "").replace("&", "§"));
            Material m;
            String icon = yml.getString(path + "icon", "BEDROCK").toUpperCase();
            try {
                m = Material.valueOf(icon);
            } catch(IllegalArgumentException e) {
                m = Material.BEDROCK;
            }
            collection.setIcon(m);
            List<String> lore = yml.getStringList(path + "lore");
            List<String> parsedLore = new ArrayList<>();
            for(String line : lore) {
                parsedLore.add(line.replace("&", "§"));
            }
            collection.setLore(parsedLore);
            List<String> locationList = yml.getStringList(path + "locations");
            for(String locationString : locationList) {
                collection.addLocation(LocationUtil.parseLocation(locationString));
            }
            collection.setRewards(ItemLoader.getItemStackList(yml, path + "rewards"));
            collectiblesManager.addCollection(collection);
        }
    }

    public void loadData() {
        data = YamlConfiguration.loadConfiguration(getFile("data.yml"));
        ConfigurationSection playerSection = data.getConfigurationSection("players");
        if(playerSection == null) {
            return;
        }
        for(String player : playerSection.getKeys(false)) {
            ConfigurationSection collectionsSection = data.getConfigurationSection("players." + player);
            if(collectionsSection == null) {
                continue;
            }
            for(String collection : collectionsSection.getKeys(false)) {
                ConfigurationSection keysSection = data.getConfigurationSection("players." + player + "." + collection);
                if(keysSection == null) {
                    continue;
                }
                for(String key : keysSection.getKeys(false)) {
                    PlayerProfile profile = collectiblesManager.getPlayerProfile(player);
                    profile.setScore(
                            collection,
                            Integer.parseInt(key),
                            data.getInt("players." + player + "." + collection + "." + key)
                    );
                }
            }
        }
    }

    public void saveData() {
        try {
            data.save(getFile("data.yml"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String collectionName, String player, int index, int value) {
        data.set("players." + player + "." + collectionName + "." + index, value);
    }

    public File getFile(String path) {
        File file = new File(plugin.getDataFolder(), path);
        if(!file.exists()) {
            plugin.saveResource(path, false);
        }
        return file;
    }

}
