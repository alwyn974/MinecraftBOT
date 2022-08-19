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

    private final File lifetimesFile = new File("C:\\Users\\herrp\\Desktop\\Obama", "lifetimes.txt");
    private final File pointsFile = new File("C:\\Users\\herrp\\Desktop\\Obama", "points.txt");

    private final List<String> lifetimes = new ArrayList<>();
    private final Map<String, Integer> points = new HashMap<>();
    private final Map<String, String> expires = new HashMap<>();

    public boolean hasLifetime(String ign) {
        return lifetimes.contains(ign);
    }

    public boolean hasHourly(String ign) {
        return expires.containsKey(ign);
    }

    public String getExpire(String ign) {
        return expires.get(ign);
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public int getPoints(String ign) {
        return points.getOrDefault(ign, 0);
    }

    public void addPoints(String ign, int amount) {
        int p = getPoints(ign);
        points.remove(ign);
        points.put(ign, p + amount);
        savePoints();
    }

    public void setPoints(String ign, int amount) {
        points.remove(ign);
        points.put(ign, amount);
        savePoints();
    }

    public void removePoints(String ign, int amount) {
        int p = getPoints(ign);
        points.remove(ign);
        points.put(ign, p - amount);
        savePoints();
    }

    public void addExpire(String ign, String time) {
        expires.put(ign, time);
    }

    public void removeExpire(String ign) {
        expires.remove(ign);
    }

    public void addLifetime(String ign) {
        lifetimes.add(ign);
        saveLifetimes();
    }

    public void removeLifetime(String ign) {
        lifetimes.remove(ign);
        saveLifetimes();
    }

    public static ObamaRegistry getRegistry() {
        if (registry == null) registry = new ObamaRegistry();
        return registry;
    }

    public void savePoints() {
        try {
            FileWriter writer = new FileWriter(pointsFile);

            for (String str : points.keySet()) {
                writer.write(str + ":" + points.get(str));
                writer.write("\n");
            }

            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLifetimes() {
        try {
            FileWriter writer = new FileWriter(lifetimesFile);

            for (String str : lifetimes) {
                writer.write(str);
                writer.write("\n");
            }

            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPoints() {
        try {
            Scanner reader = new Scanner(pointsFile);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                String ign = data.split(":")[0];
                int points = Integer.parseInt(data.split(":")[1]);

                setPoints(ign, points);
            }

            reader.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadLifetimes() {
        try {
            Scanner reader = new Scanner(lifetimesFile);

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
