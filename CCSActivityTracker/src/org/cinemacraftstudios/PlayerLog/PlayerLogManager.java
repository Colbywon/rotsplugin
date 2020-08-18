package org.cinemacraftstudios.PlayerLog;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * 
 * @author Simon U.
 * 
 */
public class PlayerLogManager implements Listener {
	private HashMap<UUID, PlayerLog> playerLogs = new HashMap<UUID, PlayerLog>();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerLog log = PlayerLogFileManager.ReadPlayerLogFromFile(event.getPlayer().getUniqueId());
		// Every time a player logs in update the name
		// This isn't strictly necessary since you can get
		// the player name from the uuid. But this makes
		// it easier when going through the logs.
		log.SetName(event.getPlayer().getDisplayName());
		Session ps = new Session();
		ps.start = new Date();
		log.GetSessions().add(ps);

		playerLogs.put(log.GetUUID(), log);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		PlayerLog log = playerLogs.remove(uuid);
		log.getCurrentSession().end = new Date();
		PlayerLogFileManager.SavePlayerLogToFile(log);
	}

	public PlayerLog GetPlayerLog(UUID uuid) {
		return playerLogs.get(uuid);
	}
}
