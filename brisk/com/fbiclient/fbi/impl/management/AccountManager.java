package com.fbiclient.fbi.impl.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fbiclient.fbi.client.account.Account;
import com.fbiclient.fbi.client.management.types.ArrayListManager;
import me.xx.utility.save.DataFile;

/**
 * @author Kyle
 * @since 2/9/2018
 **/
public class AccountManager extends ArrayListManager<Account> {

    public static Account lastAlt;
    private File ACCOUNT_DIR = DataFile.getFile("accounts");

    public AccountManager() {
    	setup();
    }
    
    public void setup() {
    	super.setup();
        load();
    }

    public void load() {
        List<String> fileContent = DataFile.read(ACCOUNT_DIR);
        getRegistry().clear();
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String email = split[0].replace("\u02a1", "");
                if (split.length == 1) {
                    include(new Account(email, "", ""));
                } else {
                    String pass = split[1].replace("\u02a1", "");
                    String name = "";
                    if (split.length > 2) {
                        name = split[2].replace("\u02a1", "");
                        include(new Account(email, pass, name));
                    } else {
                        include(new Account(email, pass));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        List<String> fileContent = new ArrayList();
        DataFile.write(ACCOUNT_DIR, fileContent);
        for (Account alt : getRegistry()) {
            String email = (alt.getUsername() == "") ? "\u02a1" : alt.getUsername();
            String pass = (alt.getPassword().length() < 1) ? "\u02a1" : alt.getPassword();
            String name = (alt.getMask() == "") ? "\u02a1" : alt.getMask();
            if (name.length() > 0) {
                fileContent.add(String.format("%s:%s:%s", email, pass, name));
            } else {
                fileContent.add(String.format("%s:%s", email, pass));
            }
        }
        DataFile.write(ACCOUNT_DIR, fileContent);
    }

    public void reload() {
        this.getRegistry().clear();
        this.load();
    }
    
    public void setLastAlt(Account alt) {
        lastAlt = alt;
    }
}
