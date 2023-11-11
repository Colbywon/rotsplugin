package org.cinemacraftstudios.api;

import java.util.UUID;

import org.cinemacraftstudios.PlayerStats.PlayerStats;
import org.cinemacraftstudios.PlayerStats.Session;

/**
 * Handles storage of Sessions. Delegates
 * 
 * @author Simon
 *
 */
public interface APIInterface {
	public PlayerStats GetPlayerStats(UUID uuid);

	public void PostSession(UUID uuid, Session session);
}
