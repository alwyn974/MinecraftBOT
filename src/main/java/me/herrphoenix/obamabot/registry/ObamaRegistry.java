package me.herrphoenix.obamabot.registry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author HerrPhoenix
 */
public class ObamaRegistry {
    private static ObamaRegistry registry;

    private final File saveFile = new File("C:\\Users\\herrp\\Desktop\\Obama", "save.txt");

    private final Map<String, Thread> pendingPayments = new HashMap<>();
    private final List<String> lifetimes = new ArrayList<>();
    private final Map<String, String> expires = new HashMap<>();

    public boolean hasPending(String ign) {
        return pendingPayments.containsKey(ign);
    }

    public boolean hasLifetime(String ign) {
        return lifetimes.contains(ign);
    }

    public boolean hasHourly(String ign) {
        return expires.containsKey(ign);
    }

    public String getExpire(String ign) {
        return expires.get(ign);
    }

    public void addExpire(String ign, String time) {
        expires.put(ign, time);
    }

    public void removeExpire(String ign) {
        expires.remove(ign);
    }

    public void addLifetime(String ign) {
        lifetimes.add(ign);
        save();

    }

    public void removeLifetime(String ign) {
        lifetimes.remove(ign);
        save();

    }

    public void addPending(String ign, Thread thread) {
        pendingPayments.put(ign, thread);
    }

    public void removePending(String ign) {
        pendingPayments.remove(ign);
    }

    public static ObamaRegistry getRegistry() {
        if (registry == null) registry = new ObamaRegistry();
        return registry;
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(saveFile);

            for (String str : lifetimes) {
                writer.write(str);
                writer.write("\n");
            }

            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            Scanner reader = new Scanner(saveFile);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                addLifetime(data);
            }

            reader.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
