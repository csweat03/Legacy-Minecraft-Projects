package club.shmoke.api.utility.utilities;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

/**
 * @author Christian
 */
public class AuthenticationUtility extends Thread {

    private final String password;
    private final String username;
    private String status;
    private Minecraft mc = Minecraft.getMinecraft();

    public AuthenticationUtility(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(java.net.Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);

        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
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
            return;
        }

        Session auth = createSession(username, password);



        if (auth != null)
            mc.session = auth;
    }
}
