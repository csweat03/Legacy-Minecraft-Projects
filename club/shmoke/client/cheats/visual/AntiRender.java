package club.shmoke.client.cheats.visual;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class AntiRender extends Cheat {

	public AntiRender() {
		super("AntiRender", Type.VISUAL);
	}
	
	@EventListener
	public void onUpdate(UpdatePlayerEvent e) {
		if(mc.theWorld == null) return;
		for (Object o : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity)o;
            if (!(entity instanceof EntityItem)) continue;
            EntityItem item = (EntityItem)entity;
            item.renderDistanceWeight = 0.0;
        }
	}
	
	@Override
	public void onDisable(){
        super.onDisable();
        for (Object o : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity)o;
            if (!(entity instanceof EntityItem)) continue;
            EntityItem item = (EntityItem)entity;
            item.renderDistanceWeight = 1.0;
        }
	}

}
