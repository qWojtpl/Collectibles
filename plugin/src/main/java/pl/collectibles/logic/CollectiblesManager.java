package pl.collectibles.logic;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.collectibles.Collectibles;
import pl.collectibles.util.PlayerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectiblesManager {

    private final Collectibles plugin = Collectibles.getInstance();
    private final List<Collection> collections = new ArrayList<>();
    private final List<PlayerProfile> playerProfiles = new ArrayList<>();

    public void addCollection(Collection collection) {
        collections.add(collection);
    }

    public List<Collection> getCollections() {
        return new ArrayList<>(collections);
    }

    public void clearCollections() {
        for(Collection c : collections) {
            c.removeTask();
        }
        collections.clear();
    }

    public void clearProfiles() {
        playerProfiles.clear();
    }

    public Collection getByName(String name) {
        for(Collection c : collections) {
            if(c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public PlayerProfile getPlayerProfile(String name) {
        for(PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getName().equals(name)) {
                return playerProfile;
            }
        }
        PlayerProfile profile = new PlayerProfile(name);
        addPlayerProfile(profile);
        return profile;
    }

    public void addPlayerProfile(PlayerProfile playerProfile) {
        playerProfiles.add(playerProfile);
    }

    public void registerClick(String player, Location location) {
        Player p = PlayerUtil.getPlayer(player);
        if(p == null) {
            return;
        }
        for(Collection c : collections) {
            int i = -1;
            for(Location loc : c.getLocations()) {
                i++;
                if(!location.equals(loc)) {
                    continue;
                }
                if(getScore(player, c.getName(), i) == 1) {
                    continue;
                }
                setScore(player, c.getName(), i, 1);
                p.sendMessage(c.getViewName() + " §a(§f" + getPlayerScore(player, c) + "§e/§6" + getGoal(c) + "§a)");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                if(getPlayerScore(player, c) >= getGoal(c)) {
                    p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);
                    for(ItemStack is : c.getRewards()) {
                        HashMap<Integer, ItemStack> rest = p.getInventory().addItem(is);
                        if(is.getItemMeta() != null) {
                            if(!is.getItemMeta().getDisplayName().isEmpty()) {
                                p.sendMessage("§a+ §ex" + is.getAmount() + "§a " + is.getItemMeta().getDisplayName());
                            } else {
                                p.sendMessage("§a+ §ex" + is.getAmount() + "§a " + is.getType().name());
                            }
                        } else {
                            p.sendMessage("§a+ §ex" + is.getAmount() + "§a " + is.getType().name());
                        }
                        for(int key : rest.keySet()) {
                            if(p.getLocation().getWorld() == null) {
                                continue;
                            }
                            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), rest.get(key));
                        }
                    }
                }
            }
        }
    }

    public int getScore(String player, String collection, int index) {
        PlayerProfile profile = getPlayerProfile(player);
        return profile.getScore(collection, index);
    }

    public int getPlayerScore(String player, Collection c) {
        PlayerProfile profile = getPlayerProfile(player);
        int score = 0;
        for(int i = 0; i < getGoal(c); i++) {
            score += profile.getScore(c.getName(), i);
        }
        return score;
    }

    public int getGoal(Collection c) {
        return c.getLocations().size();
    }

    public int getGoal(String collection) {
        Collection c = getByName(collection);
        if(c == null) {
            return 0;
        }
        return getGoal(c);
    }

    public void setScore(String player, String collection, int index, int value) {
        PlayerProfile profile = getPlayerProfile(player);
        profile.setScore(collection, index, value);
        Collectibles.getInstance().getDataHandler().save(collection, player, index, value);
    }

    public void registerSaveTask() {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> plugin.getDataHandler().saveData(),
                0L, 20L * 60 * 5);
    }

}
