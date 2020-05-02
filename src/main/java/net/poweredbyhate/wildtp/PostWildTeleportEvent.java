package net.poweredbyhate.wildtp;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Lax on 5/9/2016.
 */
public class PostWildTeleportEvent extends Event {
    private static final HandlerList panHandlers = new HandlerList();

    Player wildLing;
    WorldConfig wc;

    public PostWildTeleportEvent(Player p, WorldConfig c) {
        wildLing = p;
        wc = c;
    }

    public static HandlerList getHandlerList() {
        return panHandlers;
    }

    @Override
    public HandlerList getHandlers() {
        return panHandlers;
    }

    public Player getPlayer() {
        return wildLing;
    }
}
