package me.teamaster.nopeeking.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import me.teamaster.nopeeking.NoPeekingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.objectweb.asm.Opcodes;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
    @Unique
    NoPeekingConfig config = AutoConfig.getConfigHolder(NoPeekingConfig.class).getConfig();

    @Redirect(method = "renderButton", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;text:Ljava/lang/String;"))
    private String passwordObfuscationProxy(TextFieldWidget textFieldWidget) {
        String text = textFieldWidget.getText();
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) {
            String[] splitText = text.split(" ");
            if (splitText.length > 1 && config.commands.contains(splitText[0].replaceFirst("/", ""))) {
                StringBuilder obfuscatedText = new StringBuilder();
                boolean firstPart = true;
                for (String part : splitText) {
                    if (firstPart) {
                        obfuscatedText.append(part);
                        firstPart = false;
                    } else {
                        obfuscatedText.append("*".repeat(part.length()));
                    }
                    obfuscatedText.append(' ');
                }
                return obfuscatedText.toString();
            }
        }
        return text;
    }
}
