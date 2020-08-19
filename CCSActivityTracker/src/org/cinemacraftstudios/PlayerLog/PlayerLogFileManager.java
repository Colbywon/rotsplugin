package org.cinemacraftstudios.PlayerLog;

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
 * This class handles saving and loading PlayerLog instances.
 * 
 * @author Simon U.
 * 
 */
public abstract class PlayerLogFileManager {
	// This location is relative to the jar file (I believe).
	public static final String FOLDER_LOCATION = "./PlayerLogs/";
	private static Gson gson = new Gson();

	/**
	 * Saves a PlayerLog to a folder Works by converting the PlayerLog to json and
	 * then storing it in the specified location This can be replaced with a real
	 * database in the future.
	 * 
	 * @param log
	 */
	public static void SavePlayerLogToFile(PlayerLog log) {
		// Convert PlayerLog object to JSON
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
	 * Loads the PlayerLog that matches the uuid. Converts the json to a PlayerLog
	 * instance. If no file is found, then it creates a new instance of PlayerLog
	 * 
	 * @param uuid
	 * @return
	 */
	public static PlayerLog ReadPlayerLogFromFile(UUID uuid) {
		File playerFile = new File(pathToFile(uuid));
		if (!playerFile.exists()) {
			Bukkit.getLogger().fine("Player has no log file. Creating new file.");
			PlayerLog log = new PlayerLog(uuid);
//			SavePlayerLogToFile(log);
			return log;
		}
		InputStreamReader isReader;
		try {
			isReader = new InputStreamReader(new FileInputStream(playerFile), "UTF-8");
			JsonReader jsonReader = new JsonReader(isReader);
			PlayerLog log = gson.fromJson(jsonReader, PlayerLog.class);
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
