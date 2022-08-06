package club.shmoke.client.util;

import club.shmoke.api.account.Account;
import club.shmoke.client.Client;
import club.shmoke.client.ui.account.GuiAccountManager;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public final class AuthThread extends Thread {
    private final String password;
    private final String username;
    private String status;
    private Minecraft mc = Minecraft.getMinecraft();

    public AuthThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        status = (EnumChatFormatting.GRAY + "Waiting...");
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(java.net.Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);

        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            System.out.println("Removed Current Alt");
            Client.INSTANCE.getAccountManager().getContents().remove(new Account(username, password));
            localAuthenticationException.printStackTrace();
        }

        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void run() {
        if (password.equals("")) {
            mc.session = new Session(username, "", "", "mojang");
            status = (EnumChatFormatting.GREEN + "Logged in. (" + username + " - offline name)");
            return;
        }

        status = (EnumChatFormatting.AQUA + "Logging in...");
        Session auth = createSession(username, password);
        Account cur = new Account(username, password);

        if (auth == null) {
            cur.setStatus("Not Working");
            status = (EnumChatFormatting.RED + "Login failed!");
        } else {
            Client.INSTANCE.getAccountManager().setLastAlt((new Account(username, password)));
            cur.setStatus("Working");
            status = (EnumChatFormatting.GREEN + "Logged in. (" + auth.getUsername() + ")");
            mc.session = auth;
            GuiAccountManager.names.put(GuiAccountManager.account.getUsername(), mc.session.getUsername());
            GuiAccountManager.account.setMask(mc.session.getUsername());
        }
    }
}
