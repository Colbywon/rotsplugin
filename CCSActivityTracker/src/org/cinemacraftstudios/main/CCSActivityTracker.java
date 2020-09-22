package org.cinemacraftstudios.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.cinemacraftstudios.PlayerStats.PlayerStatsManager;

/**
 * 
 * @author Noah S., Simon U., [add-yourself] Main class of the project. Please
 *         use CommandListeners, EventListeners and add NOTHING unnecessarily
 *         merge-conflicatble here
 */
public class CCSActivityTracker extends JavaPlugin {

	public void onEnable() {
		System.out.println("Hello there");
		getServer().getPluginManager().registerEvents(new PlayerStatsManager(), this);
	}

	@Override
	public void onDisable() {
		System.out.println("General Kenobi");
	}

}
