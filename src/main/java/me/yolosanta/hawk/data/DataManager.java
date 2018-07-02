package me.yolosanta.hawk.data;

import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.combat.Criticals;
import me.yolosanta.hawk.checks.combat.KillAura;
import me.yolosanta.hawk.checks.combat.Reach;
import me.yolosanta.hawk.checks.movement.*;
import me.yolosanta.hawk.checks.player.GroundSpoofCheck;
import me.yolosanta.hawk.checks.player.ImpossiblePitch;
import me.yolosanta.hawk.checks.player.LineOfSight;
import org.bukkit.entity.Player;

import java.util.*;

public class DataManager {

    public List<Check> checks;
    public List<PlayerData> players;
    private Map<Player, Map<Check, Integer>> violations;

    public DataManager() {
        checks = new ArrayList<>();
        violations = new WeakHashMap<>();
        players = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        addCheck(new ImpossiblePitch());
        addCheck(new LineOfSight());
        addCheck(new Fly());
        addCheck(new Criticals());
        addCheck(new GroundSpoofCheck());
        addCheck(new Reach());
        addCheck(new Gravity());
        addCheck(new ImpossibleMovements());
        addCheck(new KillAura());
        addCheck(new Speed());
        //addCheck(new MorePackets());
        addCheck(new Jesus());
    }

    public void removeCheck(Check check) {
        if (checks.contains(check)) checks.remove(check);
    }

    public boolean isCheck(Check check) {
        return checks.contains(check);
    }

    public Check getCheckByName(String checkName) {
        for (Check checkLoop : Collections.synchronizedList(checks)) {
            if (checkLoop.getName().equalsIgnoreCase(checkName)) return checkLoop;
        }

        return null;
    }

    public Map<Player, Map<Check, Integer>> getViolationsMap() {
        return violations;
    }

    public int getViolatonsPlayer(Player player, Check check) {
        if (violations.containsKey(player)) {
            Map<Check, Integer> vlMap = violations.get(player);

            return vlMap.getOrDefault(check, 0);
        }
        return 0;
    }

    public void addViolation(Player player, Check check) {
        if (violations.containsKey(player)) {
            Map<Check, Integer> vlMap = violations.get(player);

            vlMap.put(check, vlMap.getOrDefault(check, 0) + 1);
            violations.put(player, vlMap);
        } else {
            Map<Check, Integer> vlMap = new HashMap<>();

            vlMap.put(check, 1);

            violations.put(player, vlMap);
        }
    }

    public void addPlayerData(Player player) {
        players.add(new PlayerData(player));
    }

    public PlayerData getData(Player player) {
        for (PlayerData dataLoop : Collections.synchronizedList(players)) {
            if (dataLoop.getPlayer() == player) {
                return dataLoop;
            }
        }
        return null;
    }

    public void removePlayerData(Player player) {
        for (PlayerData dataLoop : Collections.synchronizedList(players)) {
            if (dataLoop.getPlayer() == player) {
                players.remove(dataLoop);
                break;
            }
        }
    }

    public void addCheck(Check check) {
        if (!checks.contains(check)) checks.add(check);
    }

    public List<Check> getChecks() {
        return checks;
    }
}
