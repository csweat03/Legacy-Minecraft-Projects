package com.fbiclient.bureau.api.check;

import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.api.check.annotes.Offline;
import com.fbiclient.bureau.api.check.annotes.Prevention;
import com.fbiclient.bureau.main.checks.Phase;
import com.fbiclient.bureau.main.checks.Sprint;
import com.fbiclient.bureau.main.checks.client.ClientStatus;
import com.fbiclient.bureau.main.checks.combat.Killaura;
import com.fbiclient.bureau.main.checks.movement.InvalidMovement;
import com.fbiclient.utility.Logger;
import com.fbiclient.utility.RegistryHelper;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class CheckRegistry extends RegistryHelper<Check> {

    public void initialize() {
        try {
            for (Class clazz : getClasses()) {
                if (!clazz.isAnnotationPresent(CheckManifest.class)) continue;
                CheckManifest manifesto = (CheckManifest) clazz.getAnnotation(CheckManifest.class);
                Check check = (Check) clazz.newInstance();
                //this is autistic
                check.setLabel(manifesto.label());
                check.setSilent(!clazz.isAnnotationPresent(Prevention.class));
                check.setRunning(!clazz.isAnnotationPresent(Offline.class));
                check.setViolationsToBan(manifesto.vio());
                //this is autistic
                addContent(check);
                Logger.write("Initialized Check: " + check.getLabel(), Logger.Level.INFO);
                Bukkit.getPluginManager().registerEvents(check, Bureau.getPlugin());
            }
        } catch (Exception e) {
            Logger.write("Exception Thrown! Could not find checks or an error was thrown. Stacktrace below:", Logger.Level.ERROR);
            e.printStackTrace();
        }
    }

    private List<Class> getClasses() {
        Class[] checks = {InvalidMovement.class};//Sprint.class, Killaura.class, Phase.class, InvalidMovement.class, ClientStatus.class};

        return Arrays.asList(checks);
    }
}
