package club.shmoke.client.ui.account.components;

import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import club.shmoke.client.Client;
import club.shmoke.api.account.Account;
import club.shmoke.client.ui.account.GuiAccountManager;
import club.shmoke.client.util.GameLogger;
import club.shmoke.client.util.render.RenderUtils;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public class GuiAddAccount extends GuiScreen
{
    private final GuiAccountManager manager;
    private PasswordField password;

    private class AddAltThread extends Thread
    {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password)
        {
            this.username = username;
            this.password = password;
            status = (EnumChatFormatting.GRAY + "Idle...");
        }

        private final void checkAndAddAlt(String username, String password)
        {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                                               .createUserAuthentication(com.mojang.authlib.Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);

            try
            {
                auth.logIn();
                Client.INSTANCE.getAccountManager().getContents()
                .add(new Account(username, password, auth.getSelectedProfile().getName(), "Unchecked"));

                try
                {
                    //Client.INSTANCE.getFileManager().write("Accounts");
                }
                catch (Exception e)
                {
                }

                status = ("Alt added. (" + username + ")");
            }
            catch (AuthenticationException e)
            {
                status = (EnumChatFormatting.RED + "Alt failed!");
                e.printStackTrace();
            }
        }

        public void run()
        {
            if (password.equals("") && combined.getText().isEmpty())
            {
                Client.INSTANCE.getAccountManager().getContents().add(new Account(username, ""));
                status = (EnumChatFormatting.GREEN + "Alt added. (" + username + " - offline name)");
                return;
            }

            status = (EnumChatFormatting.AQUA + "Trying alt...");
            checkAndAddAlt(username, password);
        }
    }

    private String status = EnumChatFormatting.GRAY + "Idle...";
    private GuiTextField username;
    private GuiTextField combined;

    public GuiAddAccount(GuiAccountManager manager)
    {
        this.manager = manager;
    }

    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                AddAltThread login;

                if (combined.getText().isEmpty())
                {
                    login = new AddAltThread(username.getText(), password.getText());
                }
                else if (!combined.getText().isEmpty() && combined.getText().contains(":"))
                {
                    String u = combined.getText().split(":")[0];
                    String p = combined.getText().split(":")[1];
                    login = new AddAltThread(u.replaceAll(" ", ""), p.replaceAll(" ", ""));
                    GameLogger.log(u + " " + p, false, GameLogger.Type.SYSTEM);
                }
                else
                {
                    login = new AddAltThread(username.getText(), password.getText());
                }

                login.start();
                break;

            case 1:
                mc.displayGuiScreen(manager);
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        RenderUtils.drawClientBackground();
        username.drawTextBox();
        password.drawTextBox();
        combined.drawTextBox();
        drawCenteredString(fontRendererObj, "Add Alt", width / 2, 20, -1);

        if (username.getText().isEmpty())
        {
            drawString(mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }

        if (password.getText().isEmpty())
        {
            drawString(mc.fontRendererObj, "Password", width / 2 - 96, 96, -7829368);
        }

        if (combined.getText().isEmpty())
        {
            drawString(mc.fontRendererObj, "Email:Password", width / 2 - 96, 146, -7829368);
        }

        drawCenteredString(fontRendererObj, status, width / 2, 30, -1);
        drawCenteredString(fontRendererObj, EnumChatFormatting.RED + "OR", width / 2, 120, -1);
        super.drawScreen(i, j, f);
    }

    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 100 + 12, "Login"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 124 + 12, "Back"));
        username = new GuiTextField(eventButton, mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        username.setMaxStringLength(200);
        password = new PasswordField(mc.fontRendererObj, width / 2 - 100, 90, 200, 20);
        password.setMaxStringLength(200);
        combined = new GuiTextField(eventButton, mc.fontRendererObj, width / 2 - 100, 140, 200, 20);
        combined.setMaxStringLength(200);
    }

    protected void keyTyped(char par1, int par2)
    {
        username.textboxKeyTyped(par1, par2);
        password.textboxKeyTyped(par1, par2);
        combined.textboxKeyTyped(par1, par2);

        if ((par1 == '\t') && ((username.isFocused()) || (password.isFocused())))
        {
            username.setFocused(!username.isFocused());
            password.setFocused(!password.isFocused());
        }

        if (par1 == '\r')
        {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    protected void mouseClicked(int par1, int par2, int par3)
    {
        try
        {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        username.mouseClicked(par1, par2, par3);
        password.mouseClicked(par1, par2, par3);
        combined.mouseClicked(par1, par2, par3);
    }
}
