package com.fbiclient.fbi.impl.gui.account.screens;

import java.io.IOException;
import java.net.Proxy;

import com.fbiclient.fbi.client.account.thread.AddAltThread;
import org.lwjgl.input.Keyboard;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import com.fbiclient.fbi.client.account.Account;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.client.framework.helper.game.RenderHelper;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.account.GuiAccountManager;
import com.fbiclient.fbi.impl.gui.account.screens.field.PasswordField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Kyle
 * @since 2/9/2018
 **/
public class GuiAddAlt extends GuiScreen {
    private final GuiAccountManager manager;
    private PasswordField password;

    private String status = EnumChatFormatting.GRAY + "Idle...";
    private GuiTextField username;
    private GuiTextField combined;

    public GuiAddAlt(GuiAccountManager manager) {
        this.manager = manager;
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                AddAltThread login;
                if (combined.getText().isEmpty())
                    login = new AddAltThread(username.getText(), password.getText());
                else if (!combined.getText().isEmpty() && combined.getText().contains(":")) {
                    String u = combined.getText().split(":")[0];
                    String p = combined.getText().split(":")[1];
                    login = new AddAltThread(u.replaceAll(" ", ""), p.replaceAll(" ", ""));
                } else
                    login = new AddAltThread(username.getText(), password.getText());

                login.start();
                break;
            case 1:
                mc.displayGuiScreen(manager);
        }
    }

    public void drawScreen(int i, int j, float f) {
        IHelper.RENDER_HELPER.drawTexturedRect(RenderHelper.BACKGROUND);
        username.drawTextBox();
        password.drawTextBox();
        combined.drawTextBox();
        drawCenteredString(fontRendererObj, "Add Alt", width / 2, 20, -1);
        if (username.getText().isEmpty()) {
            drawString(mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }
        if (password.getText().isEmpty()) {
            drawString(mc.fontRendererObj, "Password", width / 2 - 96, 96, -7829368);
        }
        if (combined.getText().isEmpty()) {
            drawString(mc.fontRendererObj, "Email:Password", width / 2 - 96, 146, -7829368);
        }
        drawCenteredString(fontRendererObj, status, width / 2, 30, -1);
        drawCenteredString(fontRendererObj, EnumChatFormatting.RED + "OR", width / 2, 120, -1);
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 100 + 12, "Login"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 124 + 12, "Back"));
        username = new GuiTextField(eventButton, mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        username.setMaxStringLength(200);
        password = new PasswordField(1, mc.fontRendererObj, width / 2 - 100, 90, 200, 20);
        password.setMaxStringLength(200);
        combined = new GuiTextField(eventButton, mc.fontRendererObj, width / 2 - 100, 140, 200, 20);
        combined.setMaxStringLength(200);
    }

    protected void keyTyped(char par1, int par2) {
        username.textboxKeyTyped(par1, par2);
        password.textboxKeyTyped(par1, par2);
        combined.textboxKeyTyped(par1, par2);
        if ((par1 == '\t') && ((username.isFocused()) || (combined.isFocused()) || (password.isFocused()))) {
            username.setFocused(!username.isFocused());
            password.setFocused(!password.isFocused());
            combined.setFocused(!combined.isFocused());
        }
        if (par1 == '\r') {
            actionPerformed(buttonList.get(0));
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        username.mouseClicked(par1, par2, par3);
        password.mouseClicked(par1, par2, par3);
        combined.mouseClicked(par1, par2, par3);
    }
}
