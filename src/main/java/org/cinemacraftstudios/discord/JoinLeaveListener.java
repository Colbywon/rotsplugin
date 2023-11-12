package org.cinemacraftstudios.discord;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    private final CCSDiscordIntegration dc;

    public JoinLeaveListener(CCSDiscordIntegration dc) {
        this.dc = dc;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        dc.sendPlayerMessage(event.getPlayer().getDisplayName(), PlayerMsgType.JOINED);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        dc.sendPlayerMessage(event.getPlayer().getDisplayName(), PlayerMsgType.LEFT);
    }

}
