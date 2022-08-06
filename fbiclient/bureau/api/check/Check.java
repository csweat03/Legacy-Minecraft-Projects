package com.fbiclient.bureau.api.check;

import org.bukkit.event.Listener;

public abstract class Check implements Listener {

    private String label;
    private boolean running, silent;
    private int violationsToBan;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getLabel() {
        return label;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public int getViolationsToBan() {
        return violationsToBan;
    }

    public void setViolationsToBan(int violationsToBan) {
        this.violationsToBan = violationsToBan;
    }
}