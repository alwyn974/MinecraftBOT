package me.herrphoenix.obamabot.utils;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import me.herrphoenix.obamabot.ObamaBOT;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HerrPhoenix
 */
public class Utils {
    /**
     * @link <a href="https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values">...</a>
     * @param unsortMap
     * @param order
     * @return
     */
    public static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    public static String serializeDate(Date date) {
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder
                .append(date.getHours() - 2) //subtract 2 to sync with creative server time
                .append(":")
                .append(date.getMinutes())
                .append(":")
                .append(date.getSeconds());

        return bobTheBuilder.toString();
    }

    public static Date deserializeDate(String str) {
        Date date = Date.from(Instant.now());

        String[] array = str.split(":");
        date.setHours(Integer.parseInt(array[0]));
        date.setMinutes(Integer.parseInt(array[1]));
        date.setSeconds(Integer.parseInt(array[2]));

        return date;
    }

    public static boolean compareCurrentTimeTo(Date date) {
        Date now = Date.from(Instant.now());
        return (now.getHours() - 2) == date.getHours() && now.getMinutes() == date.getMinutes();
    }

    public static boolean isOnline(String player) {
        return ObamaBOT.getBot().getPlayers().contains(new PlayerListEntry(new GameProfile(UUID.randomUUID(), player)));
    }
}
