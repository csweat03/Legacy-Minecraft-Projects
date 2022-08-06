package club.shmoke.client.ui.account;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import club.shmoke.client.Client;
import club.shmoke.api.account.Account;
import club.shmoke.client.ui.account.components.GuiAccountLogin;
import club.shmoke.client.ui.account.components.GuiAddAccount;
import club.shmoke.client.ui.account.components.GuiRenameAccount;
import club.shmoke.client.util.AuthThread;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public class GuiAccountManager extends GuiScreen {
    public static Account account = null;
    public static HashMap<String, String> names = new HashMap<>();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int x = 0;
    private GuiButton login;
    private GuiButton remove;
    private AuthThread loginThread;
    private int offset;
    private String status = EnumChatFormatting.GRAY + "Idle...";
    private int buttonoffset = 0;
    private boolean needingInfo = false;

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                if (loginThread == null) {
                    mc.displayGuiScreen(null);
                    break;
                }

                if (!loginThread.getStatus()
                        .equals(EnumChatFormatting.YELLOW + "Attempting to log in")
                        && !loginThread.getStatus()
                        .equals(EnumChatFormatting.RED + "You can not go back!"
                                + EnumChatFormatting.YELLOW + " Logging in...")) {
                    mc.displayGuiScreen(null);
                    break;
                }

                loginThread.setStatus(EnumChatFormatting.RED + "Failed to login! Please try again!"
                        + EnumChatFormatting.YELLOW + " Logging in...");
                break;
            }

            case 2: {
                if (loginThread != null)
                    loginThread = null;
                if (Client.INSTANCE.getAccountManager().getContents().size() > 0)
                    Client.INSTANCE.getAccountManager().getContents().remove(account);
                status = "\u00a7aRemoved.";
                // Client.INSTANCE.getFileManager().write("Accounts");
                account = null;
                break;
            }

            case 3: {
                mc.displayGuiScreen(new GuiAddAccount(this));
                break;
            }

            case 4: {
                mc.displayGuiScreen(new GuiAccountLogin(this));
                break;
            }

            case 6: {
                mc.displayGuiScreen(new GuiRenameAccount(this));
                break;
            }

            case 7: {
                Client.INSTANCE.getAccountManager().getContents().clear();

//                try {
//                    // Client.INSTANCE.getFileManager().write("Accounts");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                status = "\247bCleared";
                break;
            }

            case 8: {
                try {
                    Display.setFullscreen(false);
                    Display.setLocation(-9, 0);
                    Display.setDisplayMode(new DisplayMode(1280 / 2, 720 / 2));
                } catch (LWJGLException lwjglEX) {
                }
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
                        BufferedReader variable9 = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));

                        while ((line = variable9.readLine()) != null) {
                            String[] arguments = line.split(":");
                            int i2 = 0;

                            while (i2 < 2) {
                                arguments[i2].replace(" ", "");
                                ++i2;
                            }

                            if (arguments.length > 2) {
                                Client.INSTANCE.getAccountManager()
                                        .add(new Account(arguments[0], arguments[1], arguments[2], "Unchecked"));
                                status = "\247aImported Alt List Successfully!";
                                continue;
                            }

                            Client.INSTANCE.getAccountManager().add(new Account(arguments[0], arguments[1]));
                            status = "\247aImported Alt List Successfully!";
                            // Client.INSTANCE.getFileManager().write("Accounts");
                        }
                        variable9.close();
                    } catch (Exception e) {
                        status = "\247cCould Not Import Alt List!";
                    }
                }
                try {
                    Display.setDisplayMode(new DisplayMode((int) screenSize.getWidth(), (int) screenSize.getHeight()));
                } catch (LWJGLException e) {
                }

                break;
            }

            case 10: {
                Account randomAlt = null;

                try {
                    randomAlt = Client.INSTANCE.getAccountManager().getContents().get(new Random().nextInt(Client.INSTANCE.getAccountManager().getContents().size()));
                } catch (Exception e) {
                }

                if (randomAlt == null)
                    return;

                String user1 = randomAlt.getUsername();
                String pass1 = randomAlt.getPassword();
                loginThread = new AuthThread(user1, pass1);
                loginThread.start();
                break;
            }
            case 1: {
                String user = account.getUsername();
                String pass = account.getPassword();
                loginThread = new AuthThread(user, pass);
                loginThread.start();
            }
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawClientBackground();
        Client.INSTANCE.getFontManager().c18
                .drawString(loginThread == null ? status : loginThread.getStatus(), 5, 15, -1);
        rect(width / 2 - 260, 33, width / 2 + 260, height - 50, 0x90000000, 0x70ffffff);
        GL11.glPushMatrix();
        RenderUtils.prepareScissorBox(0.0f, 33.0f, width, height - 50);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        int y2 = 38;

        for (Account alt2 : Client.INSTANCE.getAccountManager().getContents()) {
            if (!isAltInArea(y2))
                continue;

            if (!names.containsKey(alt2.getUsername()))
                names.put(alt2.getUsername(), alt2.getUsername());

            String str = (names.get(alt2.getUsername()).contains("@") ? "\2476Email: \247r" : "\2476Username: \247r") + names.get(alt2.getUsername());

            if (alt2 == account)
                Gui.drawBorderedRect(width / 2 - 255.5, y2 - offset - 2, width / 2 - 235.5, y2 - offset + 18.5, 0xff00ff00, 0);
            if (isMouseOverAlt(mouseX, mouseY, y2 - offset))
                Gui.drawRect(width / 2 - 233, y2 - offset + 14, width / 2 - 250 + Client.INSTANCE.getFontManager().c18.getStringWidth(str), y2 - offset + 15, -1);
            ResourceLocation head = null;

            if (head == null)
                head = alt2.getHead();

            GLUtils.preState();
            RenderUtils.drawCustomImage(width / 2 - 255, y2 - offset - 1.5, 19, 19, head);
            GLUtils.postState();
            Client.INSTANCE.getFontManager().c18.drawString(str, width / 2 - 232, y2 - offset + 4, -1);
            infoIcon(width / 2 + 240, y2 - offset - 3);
            y2 += 25;
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (account == null)
            login.enabled = remove.enabled = false;
        else
            login.enabled = remove.enabled = true;

        if (offset < 0)
            offset = 0;
        if (!needingInfo) {
            if (Keyboard.isKeyDown(200))
                offset -= 26;
            else if (Keyboard.isKeyDown(208))
                offset += 26;
            if (Mouse.hasWheel()) {
                int wheel = Mouse.getDWheel();
                if (wheel < 0)
                    offset += 26;
                else if (wheel > 0)
                    offset -= 26;
            }
        }

        ////////////////////////////////////////////////////////////

        if (isMouseOverInfo(mouseX, mouseY) && !needingInfo && Mouse.isButtonDown(0) && account != null)
            needingInfo = true;

        renderInfo(mouseX, mouseY);
    }

    private void renderInfo(int mouseX, int mouseY) {
        if (needingInfo) {
            if (x >= 659) x = 659;
            else x += 20;
        } else {
            if (x <= 0) x = 0;
            else x -= 20;
        }
        try {
            if (x <= 0) return;
            RenderUtils.drawBorderedRect(x - 359, height / 2 - 95, x, height - 170, 1, 0xff22313F, 0x60394B55);
            Client.INSTANCE.getFontManager().c36.drawStringWithShadow((account.getMask().equals("") ? account.getUsername() : account.getMask()), x - 351, height / 2 - 90, -1);
            Client.INSTANCE.getFontManager().c24.drawStringWithShadow("Status: " + account.getStatus(), x - 349, height / 2 - 62, -1);
            GLUtils.preState();
            RenderUtils.drawCustomImage(x - 106, height / 2 - 12, 96, 96, account.getHead());
            GLUtils.postState();
            Gui.drawRect(x - 351, height / 2 - 68, x - 9, height / 2 - 67, -1);
            addClosingButton(mouseX, mouseY, x - 18, height / 2 - 89, 12);
            Client.INSTANCE.getFontManager().c24.drawStringWithShadow("E-mail: " + account.getUsername(), x - 351, height / 2 + 60, -1);
            Client.INSTANCE.getFontManager().c24.drawStringWithShadow("Password: " + account.getPassword(), x - 351, height / 2 + 76, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void infoIcon(double x, double y) {
        GLUtils.preState();
        RenderUtils.drawCustomImage(x, y, 22, 22, new ResourceLocation("client/alt/info.png"));
        GLUtils.postState();
    }

    private void removeIcon(double x, double y) {
        GLUtils.preState();
        RenderUtils.drawCustomImage(x, y - 0.5, 8, 8, new ResourceLocation("client/alt/x.png"));
        GLUtils.postState();
    }

    private void addIcon(double x, double y) {
        GLUtils.preState();
        RenderUtils.drawCustomImage(x, y - 0.5, 8, 8, new ResourceLocation("client/alt/+.png"));
        GLUtils.postState();
    }

    private void rect(int x, int y, int width, int height, int color, int border) {
        RenderUtils.drawBorderedRect(x, y, width, height, 1, color, border);
    }

    public void initGui() {
        addButton(3, "Add");
        login = new GuiButton(1, width - 108, height / 2 - 113, 98, 20, "Login");
        addButton(10, "Random");
        addButton(4, "Direct Login");
        addButton(0, "Cancel");
        addButton(7, "Clear");
        remove = new GuiButton(2, width - 108, height / 2 + 46, 98, 20, "Remove");
        addButton(8, "Import");
        buttonList.add(login);
        buttonList.add(remove);
        login.enabled = remove.enabled = false;
    }

    private void addButton(int id, String text) {
        buttonoffset = (buttonList.size()) * 23;
        buttonList.add(new GuiButton(id, width - 108, height / 2 - 91 + buttonoffset, 98, 20,
                I18n.format(text)));
    }

    private boolean isAltInArea(int y) {
        return y - offset <= height - 30;
    }

    private boolean isMouseOverAlt(int x2, int y2, int y1) {
        if (x2 >= 220 && y2 >= y1 - 4 && x2 <= width - 221 && y2 <= y1 + 20 && x2 >= 0 && y2 >= 33 && x2 <= width
                && y2 <= height - 50 && !needingInfo) {
            return true;
        }

        return false;
    }

    private boolean isMouseOverInfo(int x2, int y2) {
        if (x2 > width - 235 && x2 < width - 220 && y2 > 36 && y2 < 479) {
            return true;
        }
        return false;
    }

    private void addClosingButton(int mouseX, int mouseY, double xPosition, double yPosition, int width) {
        GLUtils.preState();
        RenderUtils.drawCustomImage(xPosition, yPosition, width, width, new ResourceLocation("client/alt/x.png"));
        GLUtils.postState();
        if (mouseX >= xPosition && mouseX <= xPosition + width && mouseY >= yPosition && mouseY <= yPosition + width && Mouse.isButtonDown(0))
            needingInfo = false;
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        if (offset < 0)
            offset = 0;

        int y = 38 - offset;

        for (Account alt : Client.INSTANCE.getAccountManager().getContents()) {
            if (isMouseOverAlt(par1, par2, y)) {
                account = alt;
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
