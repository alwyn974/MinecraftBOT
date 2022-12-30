package re.alwyn974.minecraft.bot.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;

import java.io.*;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translate the {@link Component} to some Human readable text
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class TranslateChat {

    private static TranslateChat instance = null;
    private static final Pattern PATTERN = Pattern.compile("%[dsfg]");
    private final GlobalTranslator translator = GlobalTranslator.translator();

    public TranslateChat() {}

    public static TranslateChat getInstance() {
        if (instance == null)
            instance = new TranslateChat();
        return instance;
    }

    public void setLangFile(String langFilePath) {
        File langFile = new File("lang", langFilePath);
        if (!langFile.isFile())
            throw new IllegalArgumentException("The lang file " + langFilePath + " is not a file !");
        if (!langFile.canRead())
            throw new IllegalArgumentException("The lang file " + langFilePath + " is not readable !");

        try (BufferedReader bfrd = Files.newBufferedReader(langFile.toPath())) {
            JsonReader reader = new JsonReader(bfrd);
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();

            Key translationKey = Key.key("minecraftbot", "lang");
            TranslationRegistry registry = TranslationRegistry.create(translationKey);
            Map<String, MessageFormat> translations = new HashMap<>();

            for (String translation : json.keySet())
                translations.put(translation, new MessageFormat(transformString(json.get(translation).getAsString())));
            registry.registerAll(Locale.US, translations);
            translator.addSource(registry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: Rework this, by replacing GlobalTranslator by a custom one using String.format {@see net.kyori.adventure.translation.TranslationRegistry/Impl}

    /**
     * Transform %sdfg to indexeable {0} {1} {2} {3}...
     * @param str the string to transform
     * @return the transformed string
     */
    public String transformString(String str) {
        Matcher matcher = PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; matcher.find(); i++) {
            matcher.appendReplacement(sb, "{" + i + "}");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Translate the component to some human-readable text
     *
     * @param component the component
     * @return the readable text
     */
    public String translateComponent(Component component) {
        MiniMessage miniMessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.newline())
                        .resolver(StandardTags.translatable())
                        .build())
                .build();
        component = GlobalTranslator.render(component, Locale.US);
        return miniMessage.serialize(component).replaceAll("\\\\<", "<");
    }

    //https://launchermeta.mojang.com/mc/game/version_manifest.json
    //https://piston-meta.mojang.com/v1/packages/23b6e0e0d0f87da36075a4290cd98df1a76e2415/1.18.2.json
    //https://piston-meta.mojang.com/v1/packages/92aa15a18127b6f9e3f0e5a7d018be7ebb389703/1.18.json
    //https://resources.download.minecraft.net/%s/%s 4a 4a92aae174615c0d9cc9f572e0532588caa1c7da (fr_fr.json)
    //https://resources.download.minecraft.net/4a/4a92aae174615c0d9cc9f572e0532588caa1c7da (fr_fr.json)
}
