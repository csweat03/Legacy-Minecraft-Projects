package com.fbiclient.fbi.impl.cheats.visual;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.TickEvent;

@CheatManifest(label = "No Render", description = "Do not render unwanted entities", category = Category.VISUAL)
public class NoRender extends Cheat {
	public ArrayList<Entity> entities = new ArrayList<Entity>();

	@Val(label = "Players")
	public boolean players = false;
	@Val(label = "Passive")
	public boolean animals = false;
	@Val(label = "Hostile")
	public boolean monsters = false;
	@Val(label = "Items")
	public boolean items = true;

	@Register
	public void onTick(TickEvent tick) {
		mc.theWorld.loadedEntityList.forEach(o -> {
			Entity entity = (Entity) o;
			if (qualifies(entity))
				entity.renderDistanceWeight = 0;
			else if (entity.renderDistanceWeight <= 0)
				entity.renderDistanceWeight = 1;
		});
	}

	@Override
	public void onDisable() {
		mc.theWorld.loadedEntityList.forEach(o -> {
			Entity entity = (Entity) o;
			if (entity.renderDistanceWeight <= 0)
				entity.renderDistanceWeight = 1;
		});
	}

	boolean qualifies(Entity e) {
		if (!(e instanceof EntityAnimal) && !(e instanceof EntityMob) && !(e instanceof EntityPlayer)
				&& !(e instanceof EntityItem))
			return false;
		if ((!animals) && ((e instanceof EntityAnimal))) {
			return false;
		}
		if ((!players) && ((e instanceof EntityPlayer))) {
			return false;
		}
		if ((!monsters) && ((e instanceof EntityMob))) {
			return false;
		}
		if ((!items) && ((e instanceof EntityItem))) {
			return false;
		}
		return true;
	}

}
