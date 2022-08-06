package com.fbiclient.bureau.main.checks.combat;

import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.api.check.annotes.Prevention;
import com.fbiclient.bureau.main.checks.combat.attrib.autoblock.Constant;
import com.fbiclient.bureau.main.checks.combat.attrib.autoblock.Heuristic;
import com.fbiclient.utility.Logger;

@Prevention
@CheckManifest(label = "Killaura")
public class Killaura extends Check {

    public Killaura() {
        if (Bureau.getBureau().hasProtocolLib()) {
            new Constant();
            new Heuristic();
        } else {
            Logger.write("SubChecks [Constant, Heuristic] of Check [AutoBlock] will not be loaded due to ProtocolLib not being a detected dependency.", Logger.Level.WARNING);
        }
    }
}
