package club.shmoke.client.cheats.visual;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;

public class Animations extends Cheat {
	
	public boolean running = false;
	
	public Property<ModeType> mode = new Property(this, "Mode", ModeType.HORIZON);

	public Animations() {
		super("Animations", Type.VISUAL);
		this.description = "Changes the blocking animationX.";
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		running = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		running = false;
	}
	
	public enum ModeType {
		HORIZON, SUMMER, GEO, PUSH, NORMAL
	}

}
