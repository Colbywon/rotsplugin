package org.cinemacraftstudios.main;

import org.bukkit.plugin.java.JavaPlugin;

public class CCSActivityTracker extends JavaPlugin {

    public void onEnable() {
    	System.out.println("Hello there");
    }

    @Override
    public void onDisable() {
    	System.out.println("General Kenobi");
    }
	
}
