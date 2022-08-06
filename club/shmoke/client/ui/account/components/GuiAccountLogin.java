package club.shmoke.client.ui.account.components;

import club.shmoke.client.Client;
import club.shmoke.client.util.AuthThread;
import club.shmoke.client.util.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public final class GuiAccountLogin extends GuiScreen {
    private final GuiScreen previousScreen;
    private PasswordField password;
    private AuthThread thread;
    private GuiTextField username, combined;

    public GuiAccountLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(previousScreen);
                break;

            case 0:
                if (combined.getText().isEmpty()) {
                    thread = new AuthThread(username.getText(), password.getText());
                } else if (!combined.getText().isEmpty() && combined.getText().contains(":")) {
                    String u = combined.getText().split(":")[0];
                    String p = combined.getText().split(":")[1];
                    thread = new AuthThread(u.replaceAll(" ", ""), p.replaceAll(" ", ""));
                } else {
                    thread = new AuthThread(username.getText(), password.getText());
                }

                thread.start();
        }
    }

    public void drawScreen(int x, int y, float z) {
        RenderUtils.drawClientBackground();
        Gui.drawBorderedRect(RenderUtils.getResolution().getScaledWidth() / 2 - 105, RenderUtils.getResolution().getScaledHeight() / 2 - 65, RenderUtils.getResolution().getScaledWidth() / 2 + 105, RenderUtils.getResolution().getScaledHeight() / 2 + 58, 2, -1, 0xaa111111);
        username.drawTextBox();
        password.drawTextBox();
        combined.drawTextBox();
        for (int i = 0; i < 3; i++) {
            Gui.drawBorderedRect(width / 2 - 100, height / 2 - 49, width / 2 - 1, height / 2 - 29, 1, -1, 0x000000);
            Gui.drawBorderedRect(width / 2 + 1, height / 2 - 49, width / 2 + 99, height / 2 - 29, 1, -1, 0x000000);
            Gui.drawBorderedRect(width / 2 - 100, height / 2 - 16, width / 2 + 99, height / 2 + 4, 1, -1, 0x000000);
        }
        Client.INSTANCE.getFontManager().c18.drawCenteredStringWithShadow("Mail", width / 2 - 54, height / 2 - 60, -1);
        Client.INSTANCE.getFontManager().c18.drawCenteredStringWithShadow("Pass", width / 2 + 46, height / 2 - 60, -1);
        Client.INSTANCE.getFontManager().c18.drawCenteredStringWithShadow("\247cOR \247rMail:Pass", width / 2, height / 2 - 26, -1);
        super.drawScreen(x, y, z);
    }

    public void initGui() {
        int var3 = height / 2 - 76;
        buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
        buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        username = new GuiTextField(var3, mc.fontRendererObj, width / 2 - 97, height / 2 - 40, 98, 20);
        password = new PasswordField(mc.fontRendererObj, width / 2 + 4, height / 2 - 40, 98, 20);
        combined = new GuiTextField(var3, mc.fontRendererObj, width / 2 - 97, height / 2 - 6, 200, 20);
        username.setEnableBackgroundDrawing(false);
        combined.setEnableBackgroundDrawing(false);
        password.setEnableBackgroundDrawing(false);
        username.setMaxStringLength(100);
        password.setMaxStringLength(100);
        combined.setMaxStringLength(200);
        Keyboard.enableRepeatEvents(true);
    }

    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ((character == '\t') && ((username.isFocused()) || (combined.isFocused()) || (password.isFocused()))) {
            username.setFocused(!username.isFocused());
            password.setFocused(!password.isFocused());
            combined.setFocused(!combined.isFocused());
        }

        if (character == '\r') {
            actionPerformed((GuiButton) buttonList.get(0));
        }

        username.textboxKeyTyped(character, key);
        password.textboxKeyTyped(character, key);
        combined.textboxKeyTyped(character, key);
    }

    protected void mouseClicked(int x, int y, int button) {
        try {
            super.mouseClicked(x, y, button);
        } catch (IOException e) {
            e.printStackTrace();
        }

        username.mouseClicked(x, y, button);
        password.mouseClicked(x, y, button);
        combined.mouseClicked(x, y, button);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        username.updateCursorCounter();
        password.updateCursorCounter();
        combined.updateCursorCounter();
    }
}
