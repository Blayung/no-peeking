package me.teamaster.nopeeking;

import net.fabricmc.api.ClientModInitializer;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class NoPeeking implements ClientModInitializer {
    public static NoPeekingConfig config;

    public void onInitializeClient() {
        AutoConfig.register(NoPeekingConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(NoPeekingConfig.class).getConfig();
    }
}
