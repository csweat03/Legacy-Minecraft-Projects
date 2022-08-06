package com.fbiclient.fbi.impl.cheats.motion;

import org.lwjgl.input.Keyboard;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;

@CheatManifest(label = "Screen Move", description = "Move when guis are open", category = Category.MOTION)
public class ScreenMove extends Cheat {

    private KeyBinding[] validMovementKeys = {Minecraft.getMinecraft().gameSettings.keyBindRight, Minecraft.getMinecraft().gameSettings.keyBindLeft, Minecraft.getMinecraft().gameSettings.keyBindBack, Minecraft.getMinecraft().gameSettings.keyBindForward, Minecraft.getMinecraft().gameSettings.keyBindJump};

    @Register
    public void handleUpdates(UpdateMotionEvent event) {
        if (Minecraft.getMinecraft().currentScreen != null && (Minecraft.getMinecraft().currentScreen instanceof GuiContainer
                || Minecraft.getMinecraft().currentScreen instanceof GuiClickable)) {
            for (KeyBinding key : validMovementKeys) {
                key.setKeyDown(Keyboard.isKeyDown(key.getKeyCode()));
            }
        }
    }
}

