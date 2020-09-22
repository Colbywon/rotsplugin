package org.cinemacraftstudios.PlayerStats;

import java.util.Date;

/**
 * The place where all of the data for each session is stored.
 * 
 * @author Simon U.
 * 
 */
public class Session {
	public Date start;
	public Date end;
	public int blocksPlaced = 0;
	public int bukkitCommandsUsed = 0;
	public int blocksChangedWorldEdit = 0;
}
