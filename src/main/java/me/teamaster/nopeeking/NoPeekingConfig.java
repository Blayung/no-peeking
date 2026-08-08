package me.teamaster.nopeeking;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.Arrays;

@Config(name = "no-peeking")
public class NoPeekingConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public ArrayList<String> commandsToObfuscate = new ArrayList<>(Arrays.asList("/l", "/login", "/register"));

    @ConfigEntry.Gui.Tooltip
    public ArrayList<String> dialogTitleKeywordsToObfuscate = new ArrayList<>(Arrays.asList("login", "log in", "register", "password", "account", "sign up", "sign in"));

    @ConfigEntry.Gui.Tooltip
    private String obfuscationChar = "*";

    public char getObfuscationChar() {
        return obfuscationChar.isEmpty() ? '*' : obfuscationChar.charAt(0);
    }
}
