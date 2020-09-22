package org.cinemacraftstudios.PlayerStats;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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
public class PlayerStatsListener implements Listener {

	PlayerStatsManager playerStatsManager = PlayerStatsManager.getInstance();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		playerStatsManager.AddSession(event.getPlayer().getUniqueId());
	}

	/**
	 * Remove the PlayerStats from the HashMap and set the end date for the session.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		playerStatsManager.RemoveSession(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		playerStatsManager.GetSession(uuid).blocksPlaced++;
	}

	/**
	 * Gets called every time a player executes a command if two slashes are found
	 * in the beginning of a message then increase the bukkitCommandsUsed count
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String msg = event.getMessage();
		if (msg.startsWith("//")) {
			UUID uuid = event.getPlayer().getUniqueId();
			playerStatsManager.GetSession(uuid).worldEditCommandsUsed++;
		}
	}
}
