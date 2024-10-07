package me.teamaster.nopeeking;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class NoPeeking implements ModInitializer {
    @Override
    public void onInitialize() {
        AutoConfig.register(NoPeekingConfig.class, Toml4jConfigSerializer::new);
    }
}
