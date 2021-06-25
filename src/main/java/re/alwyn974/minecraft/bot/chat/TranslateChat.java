package re.alwyn974.minecraft.bot.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class TranslateChat {

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

    public static String appendChild(TextComponent component) {
        StringBuilder str = new StringBuilder(component.content());
        for (Component child : component.children())
            if (child instanceof TextComponent)
                str.append(((TextComponent) child).content());
        return str.toString();
    }

}
