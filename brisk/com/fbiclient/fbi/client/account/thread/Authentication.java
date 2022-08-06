package com.fbiclient.fbi.client.account.thread;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

/**
 * @author Kyle
 */
public class Authentication extends Thread {

    public String status;
    private String pass;
    private String email;

    public Authentication(String email, String pass) {
        super("Authentication Thread");
        this.email = email;
        this.pass = pass;
        status = "\u00a7aWaiting...";
    }

    private Session createSession(String email, String pass) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(pass);
        try {
            authentication.logIn();
            return new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "legacy");
        }
        catch (AuthenticationException e) {
            return null;
        }
    }

    @Override
    public void run() {
        if (this.pass.equals("")) {
            Minecraft.getMinecraft().session = new Session(this.email, "", "", "legacy");
            status = "\u00a7cLogged in with Null token";
            return;
        }
        Session session = this.createSession(this.email, this.pass);
        if (session == null) {
            status = "\u00a7cError logging in!";
        } else {
            Minecraft.getMinecraft().session = session;
            status = "\u00a7aLogged in as \u00a72" + session.getUsername();
            System.out.println("--------------------------------------------------" + this.email + " " + this.pass);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
