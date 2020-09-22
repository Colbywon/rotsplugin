package org.cinemacraftstudios.PlayerStats;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.cinemacraftstudios.api.APIInterface;

/**
 * Handles storing and interfacing with {@link Session}
 * 
 * @author Simon
 *
 */
public class PlayerStatsManager {
	private static PlayerStatsManager instance = null;

	private APIInterface api = new PlayerStatsFileManager();

	private HashMap<UUID, Session> playerSessions = new HashMap<UUID, Session>();

	public Session AddSession(UUID uuid) {
		// When a player joins start a new Session for that player.
		// And store it in the PlayerSession variable.
		Session session = new Session();
		session.start = new Date();

		return AddSession(uuid, session);
	}

	public Session AddSession(UUID uuid, Session session) {
		return playerSessions.put(uuid, session);
	}

	public Session GetSession(UUID uuid) {
		Session session = playerSessions.get(uuid);
		if (session == null)
			session = AddSession(uuid);
		return session;
	}

	public Session RemoveSession(UUID uuid) {
		Session session = playerSessions.remove(uuid);
		session.end = new Date();

		// Sends the session to the api interface to be send to the backend or file.
		api.PostSession(uuid, session);

		return playerSessions.remove(uuid);
	}

	private PlayerStatsManager() {

	}

	public static PlayerStatsManager getInstance() {
		if (instance == null) {
			instance = new PlayerStatsManager();
		}

		return instance;
	}
}
