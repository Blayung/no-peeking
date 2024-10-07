package me.teamaster.nopeeking;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "no-peeking")
public class NoPeekingConfig implements ConfigData {
    public ArrayList<String> commands = new ArrayList<>(List.of("l", "login", "register"));
}