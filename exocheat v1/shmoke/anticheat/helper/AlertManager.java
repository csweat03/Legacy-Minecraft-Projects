package club.shmoke.anticheat.helper;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AlertManager implements PrintHelper {
    private TimeHelper delay = new TimeHelper();
    private HashMap<Check, Integer> holdBack = new HashMap<>();
    private Map<Check, Integer> leniency = new HashMap<>();
    private int vio = 0;
    private HashMap<UUID, Integer> violations = new HashMap<>();

    public void alert(Player violator, Check c, CheckType type,
                      final int violation) {
        leniency.put(c, c.getLeniency());
        holdBack.put(c, holdBack.getOrDefault(c, 1) + 1);

        debug(c, violator, c.getLabel() + " " + holdBack.get(c));

        if (holdBack.get(c) >= leniency.get(c)) {
            addViolation(c, violation, violator);
            notifyStaff(violator, c, type);
            holdBack.remove(c);
        }
        if (getViolationLevel(violator) >= 20 && c.isBannable())
            banPlayer(c, violator);
    }

    public void banPlayer(Check c, OfflinePlayer player) {
        if (player instanceof Player) {
            Player player2 = (Player) player;
            print("Reason: " + c.getLabel());
            notifyStaff(player2);
            setViolations(0, player2);
            Anticheat.anticheat.getConfig().set(player2.getUniqueId().toString() + "." + "Reason", c.getLabel());
            Anticheat.anticheat.saveConfig();
            player2.kickPlayer(Anticheat.anticheat.banReason);
        }
    }

    public void debug(Check check, Player who, Object message) {
        if (check.isDebug()) {
            String string = "\247cDEBUG: \2477" + message + "";
            who.sendMessage(string);
        }
    }


    public void debug(Check check, Object message) {
        if (check.isDebug()) {
            String string = "\247cDEBUG: \2477" + message + "";
            for (Player player : Bukkit.getOnlinePlayers())
                player.sendMessage(string);
        }
    }


    private void notifyStaff(final Player violator, final Check c, final CheckType type) {
        print(Anticheat.anticheat.prefix + "\247c" + violator.getName() + (type == CheckType.BLATANT ? " \2477has been detected of \247c" : " \2477is probably using \247c")
                + c.getLabel().replace("[", "\2477[\247e").replace("]", "\2477]") + "\2477. VL: "
                + getViolationLevel(violator));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(Permissions.ADMIN.getPermission()) || player.isOp()) {
                player.sendMessage(Anticheat.anticheat.prefix + "\247c" + violator.getName() + (type == CheckType.BLATANT ? " \2477has been detected of \247c" : " \2477is probably using \247c")
                        + c.getLabel().replace("[", "\2477[\247e").replace("]", "\2477]") + "\2477. VL: "
                        + getViolationLevel(violator));
            }
        }
    }

    private void notifyStaff(Player violator) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(Permissions.ADMIN.getPermission()) || player.isOp()) {
                player.sendMessage("\247c" + violator.getName() + " \2477has been banned from the server.");
            }
        }
    }

    public void addViolation(Check check, int amount, Player player) {
        final UUID uuid = player.getUniqueId();
        if (!violations.containsKey(uuid))
            violations.put(uuid, amount);
        else
            vio = violations.get(uuid);
        setViolations(vio + amount, player);
    }

    public void resetViolations(int ms, Player player, Check check) {
        if (delay.hasReached(ms)) {
            if (violations.containsKey(player.getUniqueId()))
                violations.remove(player.getUniqueId());
            delay.reset();
        }
    }

    public void setViolations(int violations, Player player) {
        this.violations.remove(player.getUniqueId());
        this.violations.put(player.getUniqueId(), violations);
    }

    public int getViolationLevel(Player player) {
        return violations.containsKey(player.getUniqueId()) ? violations.get(player.getUniqueId()) : 0;
    }

    public enum CheckType {
        BLATANT("blatant"), PROBABLE("probable");

        private String type;

        CheckType(String type) {
            this.type = type;
        }
    }

    public enum Permissions {
        ADMIN("admin");

        private String permission;

        Permissions(String permission) {
            this.permission = "anticheat." + permission;
        }

        public String getPermission() {
            return permission;
        }
    }
}
