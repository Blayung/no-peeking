package me.teamaster.nopeeking.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.objectweb.asm.Opcodes;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.dialog.DialogScreen;

import me.teamaster.nopeeking.NoPeeking;

import java.util.Arrays;
import java.util.Locale;

@Mixin(EditBox.class)
public class EditBoxMixin {
    @Redirect(method = "extractWidgetRenderState", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/gui/components/EditBox;value:Ljava/lang/String;"))
    private String passwordObfuscationProxy(EditBox editBox) {
        String text = editBox.getValue();

        Screen currentScreen = Minecraft.getInstance().gui.screen();

        if (currentScreen instanceof ChatScreen) {
            return obfuscateChatCommandArgument(text);
        }

        if (currentScreen instanceof DialogScreen<?> dialogScreen && isSensitiveDialog(dialogScreen)) {
            return obfuscateEntireValue(text);
        }

        return text;
    }

    private boolean isSensitiveDialog(DialogScreen<?> dialogScreen) {
        String title = dialogScreen.getTitle().getString().toLowerCase(Locale.ROOT);

        for (String keyword : NoPeeking.config.dialogTitleKeywordsToObfuscate) {
            if (!keyword.isEmpty() && title.contains(keyword.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }

        return false;
    }

    private String obfuscateEntireValue(String text) {
        if (text.isEmpty()) {
            return text;
        }

        char obfuscationChar = NoPeeking.config.obfuscationChar.isEmpty() ? '*' : NoPeeking.config.obfuscationChar.charAt(0);
        StringBuilder obfuscatedText = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            obfuscatedText.append(obfuscationChar);
        }

        return obfuscatedText.toString();
    }

    private String obfuscateChatCommandArgument(String text) {
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

        return text;
    }
}