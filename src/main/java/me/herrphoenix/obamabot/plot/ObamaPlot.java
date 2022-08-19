package me.herrphoenix.obamabot.plot;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.registry.ObamaRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author HerrPhoenix
 */
public class ObamaPlot {
    private static ObamaPlot instance;

    private final Map<String, Long> players = new HashMap<>();

    public void startCounting() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (String player : players.keySet()) {

                    /*players.remove(player);
                    players.put(player, time);
                    if (getTime(player) > 60000 && getTime(player) % 60000 == 0) ObamaRegistry.getRegistry().addPoints(player, 10);*/

                    addTime(player, 60 * 1000);
                    ObamaRegistry.getRegistry().addPoints(player, 10);
                }
            }
        }, 60 * 1000, 60 * 1000);

        /*while(count) {
            long lastTime = System.currentTimeMillis();

            for (String player : players.keySet()) {
                long time = getTime(player);

                players.remove(player);
                players.put(player, time + (System.currentTimeMillis() - lastTime));
                if (getTime(player) > 60000 && getTime(player) % 60000 == 0) ObamaRegistry.getRegistry().addPoints(player, 10);
            }
        }*/
    }

    public void addTime(String player, long time) {
        long oldTime = 0L;
        if (players.containsKey(player)) oldTime = players.get(player);

        players.put(player, oldTime + time);
    }

    public void addPlayer(String player) {
        addPlayer(player, 0);
    }

    public void addPlayer(String player, long playtime) {
        players.put(player, playtime);
    }

    public void removePlayer(String player) {
        players.remove(player);
    }

    public static ObamaPlot getInstance() {
        if (instance == null) instance = new ObamaPlot();
        return instance;
    }

    public boolean isInPlot(String player) {
        return players.containsKey(player);
    }

    public long getTime(String player) {
        return players.get(player);
    }

    //join & leave events
    public void onPlayerJoin(String player) {
        addPlayer(player);
        //ObamaBOT.handlePlayerJoin(player);
    }

    public void onPlayerLeave(String player) {
        removePlayer(player);
    }
}
