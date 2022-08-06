package com.fbiclient.fbi.client.account.thread;

import com.fbiclient.fbi.client.account.Account;
import com.fbiclient.fbi.impl.Brisk;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import java.net.Proxy;

public class AddAltThread extends Thread {
    private final String password;
    private final String username;

    public AddAltThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void check(YggdrasilUserAuthentication auth) {
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public void add(YggdrasilUserAuthentication auth) {
        Brisk.INSTANCE.ACCOUNT_MANAGER
                .include(new Account(username, password, auth.getSelectedProfile().getName()));
        try {
            Brisk.INSTANCE.ACCOUNT_MANAGER.save();
        } catch (Exception e) {
        }
    }

    public void add() {
        Brisk.INSTANCE.ACCOUNT_MANAGER
                .include(new Account(username, password));
        try {
            Brisk.INSTANCE.ACCOUNT_MANAGER.save();
        } catch (Exception e) {
        }
    }

    private final void checkAdd() {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(com.mojang.authlib.Agent.MINECRAFT);
        check(auth);
        add(auth);
    }

    public void run() {
        checkAdd();
    }
}