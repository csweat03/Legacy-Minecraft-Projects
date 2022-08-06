package club.shmoke.api.account;

import club.shmoke.client.util.manage.ListManager;

public class AccountManager extends ListManager<Account>
{
    public Account lastAlt;

    public void setLastAlt(Account alt)
    {
        lastAlt = alt;
    }

    public Account getLastAlt()
    {
        return lastAlt;
    }

    public void setup() { }
}
