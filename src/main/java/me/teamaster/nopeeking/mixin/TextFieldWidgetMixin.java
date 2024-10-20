package me.teamaster.nopeeking.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.objectweb.asm.Opcodes;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

import me.teamaster.nopeeking.NoPeeking;

import java.util.Arrays;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
    @Redirect(method = "renderWidget", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;text:Ljava/lang/String;"))
    private String passwordObfuscationProxy(TextFieldWidget textFieldWidget) {
        String text = textFieldWidget.getText();
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) {
            String[] splitText = text.split(" ", -1);
            if (splitText.length > 1 && splitText[0].length() > 0 && splitText[0].charAt(0) == '/' && (NoPeeking.config.commandsToObfuscate.contains(splitText[0]) || NoPeeking.config.commandsToObfuscate.contains(splitText[0].substring(1)))) {
                StringBuilder obfuscatedText = new StringBuilder(splitText[0]);
                for (String part : Arrays.copyOfRange(splitText, 1, splitText.length)) {
                    obfuscatedText.append(' ').append("*".repeat(part.length()));
                }
                return obfuscatedText.toString();
            }
        }
        return text;
    }
}
