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
            if (splitText.length > 1) {
                int firstWordIndex = 0;
                try {
                    while (splitText[firstWordIndex].isEmpty()) {
                        firstWordIndex++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    firstWordIndex = -1;
                }
                if (firstWordIndex != -1 && splitText[firstWordIndex].charAt(0) == '/' && (NoPeeking.config.commandsToObfuscate.contains(splitText[firstWordIndex]) || NoPeeking.config.commandsToObfuscate.contains(splitText[firstWordIndex].substring(1)))) {
                    StringBuilder obfuscatedText = new StringBuilder();
                    for (int i = 0; i < firstWordIndex; i++) {
                        obfuscatedText.append(' ');
                    }
                    obfuscatedText.append(splitText[firstWordIndex]);
                    char obfuscationChar = NoPeeking.config.obfuscationChar.isEmpty() ? '*' : NoPeeking.config.obfuscationChar.charAt(0);
                    for (String part : Arrays.copyOfRange(splitText, firstWordIndex + 1, splitText.length)) {
                        obfuscatedText.append(' ');
                        for (int i = 0; i < part.length(); i++) {
                            obfuscatedText.append(obfuscationChar);
                        }
                    }
                    return obfuscatedText.toString();
                }
            }
        }
        return text;
    }
}
