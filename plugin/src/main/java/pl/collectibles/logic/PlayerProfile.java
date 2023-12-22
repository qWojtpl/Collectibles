package pl.collectibles.logic;

import pl.collectibles.Collectibles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerProfile {

    private final String name;
    private final HashMap<String, List<Integer>> playerScores = new HashMap<>();

    public PlayerProfile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore(String collection, int index) {
        if(index < 0) {
            return 0;
        }
        List<Integer> scores = playerScores.getOrDefault(collection, new ArrayList<>());
        if(scores.size() == 0) {
            for(int i = 0; i < Collectibles.getInstance().getCollectiblesManager().getGoal(collection); i++) {
                scores.add(0);
            }
        }
        if(index > scores.size() - 1) {
            return 0;
        }
        return scores.get(index);
    }

    public void setScore(String collection, int index, int value) {
        List<Integer> scores = playerScores.getOrDefault(collection, new ArrayList<>());
        if(scores.size() == 0) {
            for(int i = 0; i < Collectibles.getInstance().getCollectiblesManager().getGoal(collection); i++) {
                scores.add(0);
            }
        }
        scores.set(index, value);
        playerScores.put(collection, scores);
    }

}
