package com.fbiclient.fbi.impl.gui.account;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fbiclient.fbi.client.account.thread.AddAltThread;
import com.sun.security.ntlm.Client;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.fbiclient.fbi.client.account.Account;
import com.fbiclient.fbi.client.account.thread.Authentication;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.client.framework.helper.game.RenderHelper;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.account.screens.GuiAddAlt;
import com.fbiclient.fbi.impl.gui.account.screens.GuiAltLogin;
import com.fbiclient.fbi.impl.gui.account.screens.GuiRenameAlt;
import me.xx.utility.render.GlRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import javax.swing.*;

/**
 * @author Kyle
 * @since 2/9/2018
 **/
public class GuiAccountManager extends GuiScreen {
    private GuiButton remove;
    private Authentication loginThread;
    private int offset;
    public Account selectedAlt = null;
    private String status = EnumChatFormatting.GRAY + "Idle...";


    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                if (loginThread == null)
                    mc.displayGuiScreen(null);
                else if ((!loginThread.getStatus().equals(EnumChatFormatting.AQUA + "Logging in..."))
                        && (!loginThread.getStatus().equals(EnumChatFormatting.RED + "Do not hit back!"
                        + EnumChatFormatting.AQUA + " Logging in..."))) {
                    mc.displayGuiScreen(null);
                } else {
                    loginThread.setStatus(
                            EnumChatFormatting.RED + "Do not hit back!" + EnumChatFormatting.AQUA + " Logging in...");
                }
                break;
            case 1:
                Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry().clear();
                try {
                    Brisk.INSTANCE.ACCOUNT_MANAGER.save();
                } catch (Exception e) {
                }
                status = "\2473Cleared";
                break;
            case 2:
                if (loginThread != null) {
                    loginThread = null;
                }
                Brisk.INSTANCE.ACCOUNT_MANAGER.remove(selectedAlt);
                status = "\247aRemoved.";
                try {
                    Brisk.INSTANCE.ACCOUNT_MANAGER.save();
                } catch (Exception e) {
                }
                selectedAlt = null;
                break;
            case 3:
                mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            case 5:
                Account randomAlt;
                if (Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry().size() > 0) {
                    randomAlt = Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry()
                            .get(new java.util.Random().nextInt(Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry().size()));
                    String user1 = randomAlt.getUsername();
                    String pass1 = randomAlt.getPassword();
                    loginThread = new Authentication(user1, pass1);
                    loginThread.start();
                }
                break;
            case 6:
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select File to Import (EMAIL:PASSWORD)");
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setVisible(true);

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    if (!fileChooser.getSelectedFile().getName().endsWith(".txt")) {
                        status = "\247cCould Not Import Alt List!";
                        return;
                    }

                    try {
                        String line;
                        BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));

                        while ((line = reader.readLine()) != null) {
                            String[] arguments = line.split(":");
                            int i2 = 0;

                            while (i2 < 2) {
                                arguments[i2].replace(" ", "");
                                ++i2;
                            }

                            new AddAltThread(arguments[0], arguments[1]).add();
                            status = "\247aImported Alt List Successfully!";
                        }
                        reader.close();
                    } catch (Exception e) {
                        status = "\247cCould Not Import Alt List!";
                    }
                }
                break;
            case 7:
                Account lastAlt = Brisk.INSTANCE.ACCOUNT_MANAGER.lastAlt;
                if (lastAlt == null) {
                    if (loginThread == null) {
                        status = "\247cThere is no last used alt!";
                    } else {
                        loginThread.setStatus("\247cThere is no last used alt!");
                    }
                } else {
                    String user2 = lastAlt.getUsername();
                    String pass2 = lastAlt.getPassword();
                    loginThread = new Authentication(user2, pass2);
                    loginThread.start();
                }
                break;
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                offset += 26;
                if (offset < 0) {
                    offset = 0;
                }
            } else if (wheel > 0) {
                offset -= 26;
                if (offset < 0) {
                    offset = 0;
                }
            }
        }
        IHelper.RENDER_HELPER.drawTexturedRect(RenderHelper.BACKGROUND);
        this.drawString(fontRendererObj, mc.session.getUsername(), 10, 10, 0xDDDDDD);
        this.drawCenteredString(fontRendererObj,
                "Account Manager - " + Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry().size() + " accounts", width / 2,
                10, -1);
        this.drawCenteredString(fontRendererObj, loginThread == null ? status : loginThread.getStatus(), width / 2, 20,
                -1);
        Gui.drawRect(50.0F, 33.0F, width - 50, height - 50, new Color(25, 25, 25, 255).getRGB());
        GL11.glPushMatrix();
        GlRender.prepareScissorBox(0.0F, 33.0F, width, height - 50);
        GL11.glEnable(3089);
        int y = 38;

        for (Account alt : Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry()) {
            if (isAltInArea(y)) {
                String name;
                if (alt.getMask().equals("")) {
                    name = alt.getUsername();
                } else
                    name = alt.getMask();
                String pass;
                if (alt.getPassword().equals("")) {
                    pass = "\247eCracked";
                } else {
                    pass = alt.getPassword().replaceAll(".", "*");
                }
                if (alt == selectedAlt) {
                    if ((isMouseOverAlt(par1, par2, y - offset)) && (Mouse.isButtonDown(0))) {
                        Gui.drawRect(52.0F, y - offset - 4, width - 52, y - offset + 20,
                                new Color(45, 45, 45, 255).getRGB());
                    } else if (isMouseOverAlt(par1, par2, y - offset)) {
                        Gui.drawRect(52.0F, y - offset - 4, width - 52, y - offset + 20,
                                new Color(45, 45, 45, 255).getRGB());
                    } else {
                        Gui.drawRect(52.0F, y - offset - 4, width - 52, y - offset + 20,
                                new Color(45, 45, 45, 255).getRGB());
                    }
                } else if ((isMouseOverAlt(par1, par2, y - offset)) && (Mouse.isButtonDown(0))) {
                    Gui.drawRect(52.0F, y - offset - 4, width - 52, y - offset + 20,
                            -new Color(45, 45, 45, 255).getRGB());
                } else if (isMouseOverAlt(par1, par2, y - offset)) {
                    Gui.drawRect(52.0F, y - offset - 4, width - 52, y - offset + 20,
                            new Color(45, 45, 45, 255).getRGB());
                }
                drawCenteredString(fontRendererObj, name, width / 2, y - offset, -1);
                drawCenteredString(fontRendererObj, pass, width / 2, y - offset + 10, 5592405);
                // if (!alt.getMask().isEmpty())
                // FlatRender.drawCustomImage(55F, y - height - 1, 17, 17, alt.getHead());
                y += 26;
            }
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
        if (selectedAlt == null) {
            remove.enabled = false;
        } else {
            remove.enabled = true;
        }
        if (Keyboard.isKeyDown(200)) {
            offset -= 26;
            if (offset < 0) {
                offset = 0;
            }
        } else if (Keyboard.isKeyDown(208)) {
            offset += 26;
            if (offset < 0) {
                offset = 0;
            }
        }
    }

    public void initGui() {
        Brisk.INSTANCE.ACCOUNT_MANAGER.reload();
        buttonList.add(new GuiButton(0, width / 2 + (80), height - 24, 75, 20, "Cancel"));
        buttonList.add(new GuiButton(1, width / 2 - (122 + 32), height - 48, 100, 20, "Clear"));
        buttonList.add(this.remove = new GuiButton(2, width / 2 - (40 + 32) + 4, height - 24, 70, 20, "Remove"));
        buttonList.add(new GuiButton(3, width / 2 + 58, height - 48, 100, 20, "Add"));
        buttonList.add(new GuiButton(4, width / 2 - (16 + 32), height - 48, 100, 20, "Direct Login"));
        buttonList.add(new GuiButton(5, width / 2 - (122 + 32) + 4, height - 24, 78, 20, "Random"));
        buttonList.add(new GuiButton(6, width / 2 + 6, height - 24, 70, 20, "Add Bulk"));

        remove.enabled = false;
    }

    private boolean isAltInArea(int y) {
        return y - offset <= height - 50;
    }

    private boolean isMouseOverAlt(int x, int y, int y1) {
        return (x >= 52) && (y >= y1 - 4) && (x <= width - 52) && (y <= y1 + 20) && (x >= 0) && (y >= 33)
                && (x <= width) && (y <= height - 50);
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        if (offset < 0) {
            offset = 0;
        }
        int y = 38 - offset;
        for (Account alt : Brisk.INSTANCE.ACCOUNT_MANAGER.getRegistry()) {
            if (isMouseOverAlt(par1, par2, y)) {
                if (alt == selectedAlt) {
                    loginThread = new Authentication(selectedAlt.getUsername(), selectedAlt.getPassword());
                    loginThread.start();
                    return;
                }
                selectedAlt = alt;
            }
            y += 26;
        }
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
