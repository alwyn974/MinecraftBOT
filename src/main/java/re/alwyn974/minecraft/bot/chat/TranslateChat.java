package re.alwyn974.minecraft.bot.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

/**
 * Translate the {@link Component} to some Human readable text
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @since 1.0.0
 * @version 1.0.0
 */
public class TranslateChat {

    /**
     * Translate the component to some human readable text
     * @param component the component
     * @return the readable text
     */
    public static String translateComponent(Component component) {
        StringBuilder builder = new StringBuilder();
        if (component instanceof TextComponent) {
            TextComponent text = (TextComponent) component;
            builder.append(text.content());
            for (Component child : text.children())
                if (child instanceof TextComponent)
                    builder.append(appendChild((TextComponent) child));
        }
        return builder.toString();
    }

    /**
     * Append all child
     * @param component the component
     * @return the content of all appened child
     */
    public static String appendChild(TextComponent component) {
        StringBuilder str = new StringBuilder(component.content());
        for (Component child : component.children())
            if (child instanceof TextComponent)
                str.append(((TextComponent) child).content());
        return str.toString();
    }

}
