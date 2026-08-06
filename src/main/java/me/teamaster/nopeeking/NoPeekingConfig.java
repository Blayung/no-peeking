package me.teamaster.nopeeking;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.Arrays;

@Config(name = "no-peeking")
public class NoPeekingConfig implements ConfigData {
    public ArrayList<String> commandsToObfuscate = new ArrayList<>(Arrays.asList("/l", "/login", "/register"));
    @ConfigEntry.Gui.Tooltip
    public String obfuscationChar = "*";
    @ConfigEntry.Gui.Tooltip
    public ArrayList<String> dialogTitleKeywordsToObfuscate = new ArrayList<>(Arrays.asList(
            "login", "log in", "register", "sign in", "sign up", "password", "forgot password",
            "anmelden", "einloggen", "registrieren", "passwort", "passwort vergessen"
    ));
}