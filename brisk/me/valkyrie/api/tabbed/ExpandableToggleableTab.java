package me.valkyrie.api.tabbed;

public abstract class ExpandableToggleableTab extends ExpandableTab {
	
    private boolean state;

    public void toggle() {
        this.setState(!this.getState());
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
