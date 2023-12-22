package pl.collectibles.logic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import pl.collectibles.Collectibles;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private final String name;
    private String viewName;
    private Material icon = Material.DIRT;
    private List<String> lore = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private List<ItemStack> rewards = new ArrayList<>();
    private final int task;

    public Collection(String name) {
        this.name = name;
        task = Collectibles.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(
                Collectibles.getInstance(),
                this::playParticles,
                0L,
                20L
        );
    }

    public void playParticles() {
        for(Location location : locations) {
            if(location.getWorld() == null) {
                continue;
            }
            Location loc = location.clone();
            loc.setX(loc.getX() + 0.5);
            loc.setY(loc.getY() + 0.75);
            loc.setZ(loc.getZ() + 0.5);
            location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 20);
        }
    }

    public void removeTask() {
        Collectibles.getInstance().getServer().getScheduler().cancelTask(task);
    }

    public String getName() {
        return this.name;
    }

    public String getViewName() {
        return this.viewName;
    }

    public Material getIcon() {
        return this.icon;
    }

    public List<String> getLore() {
        return new ArrayList<>(lore);
    }

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    public List<ItemStack> getRewards() {
        return new ArrayList<>(rewards);
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void addLocation(Location location) {
        if(location == null) {
            return;
        }
        this.locations.add(location);
    }

    public void setRewards(List<ItemStack> rewards) {
        this.rewards = rewards;
    }

    public void addReward(ItemStack reward) {
        this.rewards.add(reward);
    }

}
