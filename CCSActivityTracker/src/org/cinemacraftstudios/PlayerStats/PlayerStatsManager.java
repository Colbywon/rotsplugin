package org.cinemacraftstudios.PlayerStats;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles the contruction and destruction of the PlayerStats instances. To
 * conserve memory and performance only the players that are logged in are
 * stored in memory.
 * 
 * @author Simon U.
 * 
 */
public class PlayerStatsManager implements Listener {
	private HashMap<UUID, PlayerStats> PlayerStats = new HashMap<UUID, PlayerStats>();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerStats log = PlayerStatsFileManager.ReadPlayerStatsFromFile(event.getPlayer().getUniqueId());
		// Every time a player logs in update the name
		// This isn't strictly necessary since you can get
		// the player name from the uuid. But this makes
		// it easier when going through the logs.
		log.SetName(event.getPlayer().getDisplayName());
		Session ps = new Session();
		ps.start = new Date();
		log.GetSessions().add(ps);

		PlayerStats.put(log.GetUUID(), log);
	}

	/**
	 * Remove the PlayerStats from the HashMap and set the end date for the session.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		PlayerStats log = PlayerStats.remove(uuid);
		log.getCurrentSession().end = new Date();
		PlayerStatsFileManager.SavePlayerStatsToFile(log);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		UUID uuid = p.getUniqueId();
		PlayerStats log = GetPlayerStats(uuid);
		
		log.getCurrentSession().blocksPlaced++;
	}
	
	public PlayerStats GetPlayerStats(UUID uuid) {
		return PlayerStats.get(uuid);
	}
}
