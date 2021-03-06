package org.cinemacraftstudios.PlayerStats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * This class handles saving and loading PlayerStats instances.
 * 
 * @author Simon U.
 * 
 */
public abstract class PlayerStatsFileManager {
	// This location is relative to the jar file (I believe).
	public static final String FOLDER_LOCATION = "./PlayerStats/";
	private static Gson gson = new Gson();

	/**
	 * Saves a PlayerStats to a folder Works by converting the PlayerStats to json and
	 * then storing it in the specified location This can be replaced with a real
	 * database in the future.
	 * 
	 * @param log
	 */
	public static void SavePlayerStatsToFile(PlayerStats log) {
		// Convert PlayerStats object to JSON
		String json = gson.toJson(log);

		// Creates a new File thats called the players uuid.
		File playerFile = new File(pathToFile(log.GetUUID()));
		// Make sure to create a file if one already doesn't exist.
		if (!playerFile.exists()) {
			try {
				// Checks to make sure the folder exist, and if it doesn't then we create all
				// the necessary directories.
				File folder = new File(playerFile.getParent());
				if (!folder.exists()) {
					folder.mkdirs();
				}
				playerFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getLogger().severe("Error saving player log: " + e.toString());
			}
		}

//		Bukkit.getLogger().finer(playerFile.getAbsolutePath());

		try {
			FileWriter playerWriter;
			playerWriter = new FileWriter(playerFile.getAbsoluteFile(), false);

			BufferedWriter bufferWriter = new BufferedWriter(playerWriter);
			bufferWriter.write(json.toString());
			bufferWriter.close();

			Bukkit.getLogger().finer("Saving player " + log.GetUUID());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Loads the PlayerStats that matches the uuid. Converts the json to a PlayerStats
	 * instance. If no file is found, then it creates a new instance of PlayerStats
	 * 
	 * @param uuid
	 * @return
	 */
	public static PlayerStats ReadPlayerStatsFromFile(UUID uuid) {
		File playerFile = new File(pathToFile(uuid));
		if (!playerFile.exists()) {
			Bukkit.getLogger().fine("Player has no log file. Creating new file.");
			PlayerStats log = new PlayerStats(uuid);
//			SavePlayerStatsToFile(log);
			return log;
		}
		InputStreamReader isReader;
		try {
			isReader = new InputStreamReader(new FileInputStream(playerFile), "UTF-8");
			JsonReader jsonReader = new JsonReader(isReader);
			PlayerStats log = gson.fromJson(jsonReader, PlayerStats.class);
			return log;
		} catch (Exception e) {
			Bukkit.getLogger().severe("Couldn't load player log:" + e.toString());
		}
		return null;
	}

	private static String pathToFile(UUID uuid) {
		return FOLDER_LOCATION + uuid.toString() + ".json";
	}
}
