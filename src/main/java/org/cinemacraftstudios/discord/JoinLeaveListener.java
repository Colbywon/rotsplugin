package org.cinemacraftstudios.discord;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.cinemacraftstudios.PlayerStats.PlayerStatsManager;

public class JoinLeaveListener implements Listener {

    private final CCSDiscordIntegration dc;

    public JoinLeaveListener(CCSDiscordIntegration dc) {
        this.dc = dc;
    }

    // TODO: Replace Bukkit.getServer().getOnlinePlayers().length with sth less overkill

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        dc.sendPlayerMessage(event.getPlayer().getDisplayName(), PlayerMsgType.JOINED,
                PlayerStatsManager.getInstance().getCurrentSessionCount(), 40);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        dc.sendPlayerMessage(event.getPlayer().getDisplayName(), PlayerMsgType.LEFT,
                PlayerStatsManager.getInstance().getCurrentSessionCount(), 40);
    }

}
