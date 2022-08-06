package com.fbiclient.fbi.impl.cheats.combat;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.child.Child;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import me.xx.utility.Stopwatch;

@CheatManifest(label = "Auto Heal", description = "Automatically throw potions and eat soups", category = Category.COMBAT)
public class AutoHeal extends Cheat {
	
	@Val(label = "Health", aliases = { "Hearts" }, description = "How many hearts you should have before healing.")
	@Clamp(min = "0", max = "10")
	@Increment("0.5")
	private double health = 3.0;
	
	@Val(label = "Delay", aliases = { "Time" }, description = "How long autoheal should wait before healing again.")
	@Clamp(min = "25", max = "2000")
	@Increment("25")
	private long delay = 300L;
	
	@Val(label = "Potions", aliases = { "Potion", "Pot", "Pots" }, description = "If health potion should be thrown.")
	private boolean potions = true;
	
	@Child("Potions")
	@Val(label = "Jump", aliases = { "Up", "Vertical", "Upwards" }, description = "If potions should be throw upwards")
	private boolean upwards = false;
	
	@Val(label = "Soup", aliases = { "Soups" }, description = "If mushroom soup should be eaten for health.")
	private boolean soup = true;
	
	@Child("Soup")
	@Val(label = "Drop", aliases = { "ThrowAway", "Throw" }, description = "If used soups should be thrown away.")
	private boolean drop = false;
	
	private int slot = -1;
	public static boolean healing = false;
	private Stopwatch gameTimer = new Stopwatch();
	private int packetSlot = -1;
	private boolean packetSoup = false;
	private ItemStack itemStack = null;

	@Register
	public void handlePackets(PacketEvent event) {
		if (this.packetSlot != -1 && !this.gameTimer.hasReached(1000L) && event.getType() != Event.Type.OUTGOING) {
			mc.thePlayer.inventoryContainer.putStackInSlot(this.packetSlot, null);
		}
		if (event.getPacket() instanceof S2FPacketSetSlot && event.getType() != Event.Type.POST && this.packetSlot != -1
				&& !this.gameTimer.hasReached(1000L)) {
			S2FPacketSetSlot packet = (S2FPacketSetSlot) event.getPacket();
			if (packet.func_149173_d() == 44) {
				packet.setItem(this.itemStack);
			}
			this.packetSlot = -1;
		}
	}

	@Register
	public void handleUpdate(UpdateMotionEvent event) {
		if (event.getType() == Event.Type.PRE) {
			this.update();
			if (!this.shouldHeal()) {
				return;
			}
			if (this.slot == -1) {
				return;
			}
			boolean up = this.upwards && mc.thePlayer.onGround;
			if (mc.thePlayer.inventory.mainInventory[this.slot].getItem() instanceof ItemPotion) {
				if (up) {
					mc.thePlayer.jump();
				}
				event.setPitch(up ? -90.0f : 90.0f);
			}
			this.healing = true;
		} else {
			if (!this.healing) {
				return;
			}
			if (!this.shouldHeal()) {
				return;
			}
			if (this.slot == -1) {
				return;
			}
			if (mc.thePlayer.inventory.mainInventory[8] != null) {
				this.itemStack = mc.thePlayer.inventory.mainInventory[8].copy();
			}
			this.packetSoup = (mc.thePlayer.inventory.mainInventory[this.slot].getItem() == Items.mushroom_stew);
			this.packetSlot = this.slot;
			if (this.slot < 9) {
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.slot));
				mc.thePlayer.sendQueue.getNetworkManager()
						.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
				if (this.drop && this.packetSoup) {
					mc.thePlayer.dropItem(mc.thePlayer.inventory.mainInventory[this.slot].getItem(), 64);
				}
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
			} else {
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(8));
				IHelper.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot, 8, 2,
						IHelper.mc.thePlayer);
				IHelper.mc.getNetHandler().getNetworkManager()
						.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
				if (this.drop && this.packetSoup) {
					mc.thePlayer.dropItem(mc.thePlayer.inventory.mainInventory[this.slot].getItem(), 64);
				}
				IHelper.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.packetSlot, 8, 2,
						IHelper.mc.thePlayer);
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
			}
			this.healing = false;
			this.gameTimer.reset();
		}
	}

	public void update() {
		int count = 0;
		this.slot = -1;
		for (int i = 0; i <= 35; ++i) {
			if (mc.thePlayer.inventory.mainInventory[i] != null) {
				if (mc.thePlayer.inventory.mainInventory[i].getItem() != null) {
					ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
					Item item = stack.getItem();
					if (item instanceof ItemPotion && this.potions) {
						if (stack.getMetadata() == 16421) {
							this.slot = i;
							++count;
						}
					} else if (item instanceof ItemSoup && this.soup) {
						this.slot = i;
						++count;
					}
				}
			}
		}
		this.suffix = count + "";
	}

	private boolean shouldHeal() {
		return this.gameTimer.hasReached(delay) && mc.thePlayer.getHealth() <= health * 2;
	}

}
