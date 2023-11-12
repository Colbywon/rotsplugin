package org.cinemacraftstudios.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.cinemacraftstudios.PlayerStats.PlayerStatsListener;
import org.cinemacraftstudios.WorldEditIntegration.WorldEditHook;

import com.sk89q.worldedit.WorldEdit;
import org.cinemacraftstudios.discord.CCSDiscordIntegration;
import org.cinemacraftstudios.discord.JoinLeaveListener;


/**
 * 
 * @author Noah S., Simon U., [add-yourself] Main class of the project. Please
 *         use CommandListeners, EventListeners and add NOTHING unnecessarily
 *         merge-conflicatble here
 */
public class CCSActivityTracker extends JavaPlugin {

	private CCSDiscordIntegration discord;

	public void onEnable() {
		System.out.println("Hello there");

		saveDefaultConfig();

		discord = new CCSDiscordIntegration(this);

		getServer().getPluginManager().registerEvents(new PlayerStatsListener(), this);
		getServer().getPluginManager().registerEvents(new JoinLeaveListener(discord), this);

		WorldEdit.getInstance().getEventBus().register(new WorldEditHook());
	}


	@Override
	public void onDisable() {
		discord.sendStatus(false);

		System.out.println("General Kenobi");
	}

}
