package org.cinemacraftstudios.main;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;

public class CCSDiscordIntegration {

    public CCSDiscordIntegration(CCSActivityTracker plugin) {
        FileConfiguration conf = plugin.getConfig();

        JDABuilder builder = JDABuilder.createLight(conf.getString("dc.token", "<token>"));

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.customStatus("having an existential crisis"));

        builder.build().getGuildChannelById(0);
    }
}
