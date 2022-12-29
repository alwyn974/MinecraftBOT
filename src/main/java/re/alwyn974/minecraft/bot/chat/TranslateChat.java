package re.alwyn974.minecraft.bot.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.regex.Pattern;

/**
 * Translate the {@link Component} to some Human readable text
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class TranslateChat {

    public static final Pattern STRIP_EXTRAS_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORX]");

    /**
     * Translate the component to some human-readable text
     *
     * @param component the component
     * @return the readable text
     */
    public static String translateComponent(Component component) {
        String text = PlainTextComponentSerializer.plainText().serialize(component);
        return STRIP_EXTRAS_PATTERN.matcher(text).replaceAll("");
    }

}
