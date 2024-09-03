package me.teamaster.nopeeking;

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

        if (text.startsWith("/l ") || text.startsWith("/login ") || text.startsWith("/register ")) {
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
            return obfuscatedText.toString();
        }

        return text;
    }
}
