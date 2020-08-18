package org.cinemacraftstudios.main;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Noah S., [add-yourself]
 * Main class of the project. Please use CommandListeners, EventListeners and add NOTHING unnecessarily merge-conflicatble here
 */
public class CCSActivityTracker extends JavaPlugin {

    public void onEnable() {
    	System.out.println("Hello there");
    }

    @Override
    public void onDisable() {
    	System.out.println("General Kenobi");
    }
	
}
