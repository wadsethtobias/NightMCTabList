package xyz.nightmc.tappedlol.nightmctablist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final NightMCTabList main;

    public JoinListener(NightMCTabList main) {
        this.main = main;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        main.setTab(event.getPlayer());
    }

}
