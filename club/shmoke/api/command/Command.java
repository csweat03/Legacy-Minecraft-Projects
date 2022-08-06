package club.shmoke.api.command;

import java.util.ArrayList;

import club.shmoke.client.util.manage.Module;

/**
 * @author Kyle
 * @since 8/2017
 **/
public class Command extends Module
{
    private String syntax;
    private ArrayList<String> alias;

    public Command(String label, String description, String syntax, ArrayList<String> alias)
    {
        this.label = label;
        this.alias = alias;
        this.description = description;
        this.syntax = syntax;
    }

    public String syntax()
    {
        return syntax;
    }

    public String description()
    {
        return description;
    }

    public String id()
    {
        return label.toLowerCase();
    }

    public String label()
    {
        return label;
    }

    public ArrayList<String> aliases()
    {
        return alias;
    }

    public void setLabel(String name)
    {
        this.label = name;
    }

    public String syntaxMsg()
    {
        return "Syntax: " + this.syntax();
    }

    public void dispatch(String[] args, String message) { }
}