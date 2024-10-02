package me.teamaster.nopeeking;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.objectweb.asm.Opcodes;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
    @Redirect(method = "renderButton", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;text:Ljava/lang/String;"))
    private String passwordObfuscationProxy(TextFieldWidget textFieldWidget) {
        String text = textFieldWidget.getText();

        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) {
            boolean isCommand = (text.startsWith("/login ") || text.startsWith("/register ") || text.startsWith("/l ")) && text.split(" ").length > 1;
            if (isCommand) {
                StringBuilder obfuscatedText = new StringBuilder();
                boolean firstPart = true;
                for (String part : text.split(" ")) {
                    if (firstPart) {
                        obfuscatedText.append(part);
                        firstPart = false;
                    } else {
                        obfuscatedText.append("*".repeat(part.length()));
                    }
                    obfuscatedText.append(' ');
                }
                return obfuscatedText.toString().trim();
            }
        }

        return textFieldWidget.getText();
    }
}
