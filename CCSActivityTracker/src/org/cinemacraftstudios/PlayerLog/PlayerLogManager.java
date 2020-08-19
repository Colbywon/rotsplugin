package org.cinemacraftstudios.PlayerLog;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles the contruction and destruction of the playerlogs instances. To
 * conserve memory and performance only the players that are logged in are
 * stored in memory.
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

	/**
	 * Remove the PlayerLog from the HashMap and set the end date for the session.
	 * 
	 * @param event
	 */
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
