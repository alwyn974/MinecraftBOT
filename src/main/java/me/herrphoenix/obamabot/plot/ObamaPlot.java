package me.herrphoenix.obamabot.plot;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import me.herrphoenix.obamabot.utils.Utils;

import java.time.Instant;
import java.util.*;

/**
 * @author HerrPhoenix
 */
public class ObamaPlot {
    private static ObamaPlot instance;

    private final Map<String, Long> players = new HashMap<>();
    private final List<String> toDeny = new ArrayList<>();

    public void startCounting() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (ObamaBOT.isBegCooldown) ObamaBOT.lastBegTime += 60 * 1000;

                    if (ObamaBOT.lastBegTime == ObamaBOT.BEG_COOLDOWN) {
                        ObamaBOT.isBegCooldown = false;
                        ObamaBOT.lastBegTime = 0L;
                    }

                    for (String tax : ObamaRegistry.getRegistry().getHourlyTaxPlayers()) {
                        Date expire = Utils.deserializeDate(ObamaRegistry.getRegistry().getExpire(tax));

                        if (Utils.compareCurrentTimeTo(expire)) {
                            if (ObamaPlot.getInstance().isInPlot(tax)) {
                                ObamaBOT.chat("/msg " + tax + " Hey, your hourly tax has expired so you should pay us another 50$ with /pay TheORS 50");
                                Thread.sleep(ObamaBOT.CHAT_COOLDOWN);
                                ObamaBOT.chat("/msg " + tax + " If you do not pay in 1 minute, you will be denied.");
                            }
                            ObamaRegistry.getRegistry().removeExpire(tax);
                            toDeny.add(tax);
                        }
                    }

                    for (String deny : toDeny) {
                        if (ObamaPlot.getInstance().isInPlot(deny)) {
                            ObamaBOT.chat("/msg " + deny + " You have not renewed your hourly tax, therefore you will be denied.");
                            Thread.sleep(ObamaBOT.CHAT_COOLDOWN);
                        }
                        ObamaBOT.chat("/p deny " + deny);
                    }

                    toDeny.clear();

                    for (String player : players.keySet()) {
                        if (Utils.isOnline(player)) {
                            addTime(player, 60 * 1000);
                            ObamaRegistry.getRegistry().addPoints(player, 10);
                        } else {
                            onPlayerLeave(player);
                        }
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 60 * 1000, 60 * 1000);
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

    public void onPlayerPayment(String player, int amount) {
        if (ObamaRegistry.getRegistry().hasPendingPayment(player) || toDeny.contains(player)) {
            if (amount < 50) {
                ObamaBOT.chat("/pay "+ player + amount);
                return;
            }

            ObamaBOT.chat("/msg HerrPhoenix " + player);
            ObamaBOT.chat("/pay HerrPhoenix " + amount);

            ObamaRegistry.getRegistry().removePending(player);
            toDeny.remove(player);

            if (amount == 50) {
                String date = Utils.serializeDate(Date.from(Instant.now()));
                ObamaRegistry.getRegistry().addExpire(player, date);
                ObamaBOT.chat("Thank you, " + player + " for paying your tax! It will expire on " + date + ".");
                ObamaBOT.chat("Keep in mind that this is Server Time, that you can check by pressing tab.");
            } else if (amount == 500) {
                ObamaRegistry.getRegistry().addLifetime(player);
                ObamaBOT.chat("Thank you, " + player + " for paying lifetime!");
            }

            return;
        }
        if (ObamaRegistry.getRegistry().hasHourly(player) && (amount == 500 || amount == 450)) {
            ObamaRegistry.getRegistry().removeExpire(player);
            ObamaRegistry.getRegistry().addLifetime(player);
            ObamaBOT.chat("Thank you, " + player + " for paying lifetime!");

            return;
        }
    }
}
