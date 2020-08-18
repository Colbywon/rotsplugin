package org.cinemacraftstudios.PlayerLog;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerLog {
	private UUID uuid;
	private String name;
	private ArrayList<Session> sessions = new ArrayList<Session>();

	public PlayerLog(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID GetUUID() {
		return this.uuid;
	}

	public String GetName() {
		return name;
	}

	public void SetName(String name) {
		this.name = name;
	}

	public ArrayList<Session> GetSessions() {
		return sessions;
	}

	public void SetSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}

	public Session getCurrentSession() {
		// Gets the last session. This should be the last one
		// But depends on if one was created.
		return this.sessions.get(this.sessions.size() - 1);
	}
}
