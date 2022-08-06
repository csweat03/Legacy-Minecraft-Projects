package club.shmoke.main.gui.alt;

import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.AuthenticationUtility;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GuiLogin extends GuiScreen {

    private GuiTextField username, password;
    private Utility utility = new Utility();

    protected void actionPerformed(GuiButton button) {
        AuthenticationUtility auth;
        switch (button.id) {
            case 0:
                auth = new AuthenticationUtility(username.getText(), password.getText());
                auth.start();
                break;
            case 1:
                String uname = "", pword = "";
                try {
                    String agent = "Lunar-Agent|325";
                    System.setProperty("http.agent", agent);
                    URL url = new URL("https://lunaclient.app/redirect.php");
                    System.setProperty("http.agent", agent);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.addRequestProperty("User-Agent", agent);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] split = line.split(":");
                        uname = split[0];
                        pword = split[1];
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                auth = new AuthenticationUtility(uname, pword);
                System.out.println(uname + " : " + pword);
                auth.start();
                break;
            case 2:
                mc.displayGuiScreen(new GuiMainMenu());
                break;
        }
    }

    public void drawScreen(int x, int y, float z) {
        utility.renderUtility.drawClientBackground();
        utility.font.drawStringWithShadow("I'm retarded, Zhn gave me cupcakes", 1, utility.renderUtility.getResolution().getScaledHeight() - 10, -1);
        username.drawTextBox();
        password.drawTextBox();
        super.drawScreen(x, y, z);
    }

    public void initGui() {
        int var3 = height / 2 + 8;
        buttonList.add(new GuiButton(0, width / 2 - 100, var3, "Login"));
        buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 24, "Generate"));
        buttonList.add(new GuiButton(2, width / 2 - 100, var3 + 48, "Back"));
        username = new GuiTextField(var3, mc.fontRendererObj, width / 2 - 100, height / 2 - 40, 200, 20);
        password = new GuiTextField(var3, mc.fontRendererObj, width / 2 - 100, height / 2 - 16, 200, 20);
        username.setMaxStringLength(100);
        password.setMaxStringLength(100);
        Keyboard.enableRepeatEvents(true);
    }

    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ((character == '\t') && ((username.isFocused()) || (password.isFocused()))) {
            username.setFocused(!username.isFocused());
            password.setFocused(!password.isFocused());
        }

        if (character == '\r') {
            actionPerformed(buttonList.get(0));
        }

        username.textboxKeyTyped(character, key);
        password.textboxKeyTyped(character, key);
    }

    protected void mouseClicked(int x, int y, int button) {
        try {
            super.mouseClicked(x, y, button);
        } catch (IOException e) {
            e.printStackTrace();
        }

        username.mouseClicked(x, y, button);
        password.mouseClicked(x, y, button);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        username.updateCursorCounter();
        password.updateCursorCounter();
    }
}
