package me.teamaster.nopeeking;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.ConfigData;

import java.util.List;
import java.util.Arrays;

@Config(name = "no-peeking")
public class NoPeekingConfig implements ConfigData {
    public List<String> commandsToObfuscate = Arrays.asList("/l", "/login", "/register");
    public String obfuscationString = "*";
    public Boolean shouldObfuscateLetterByLetter = true;
}
