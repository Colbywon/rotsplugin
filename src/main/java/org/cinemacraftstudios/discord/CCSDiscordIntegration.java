package org.cinemacraftstudios.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import org.cinemacraftstudios.PlayerStats.PlayerStatsManager;
import org.cinemacraftstudios.main.CCSActivityTracker;

import java.awt.Color;
import java.time.OffsetDateTime;

public class CCSDiscordIntegration extends ListenerAdapter {

    private final JDA jda;
    private final FileConfiguration conf;
    private TextChannel activityChannel;

    public CCSDiscordIntegration(CCSActivityTracker plugin) {
        conf = plugin.getConfig();

        JDABuilder builder = JDABuilder.createDefault(conf.getString("dc.token", "idiot"));

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.customStatus("having an existential crisis"));
        builder.setEventManager(new AnnotatedEventManager());
        builder.addEventListeners(this); //wait for JDA to finish setup (non-blocking)

        jda = builder.build();
    }

    @SubscribeEvent
    public void onReadyEvent(ReadyEvent event) {
        //wait for JDA to finish setup (non-blocking)
        setupActivityChannel();
    }

    /**
     * reads the channel ID from the config and updates the activityChannel
     */
    public void setupActivityChannel() {
        long channelID = conf.getLong("dc.channel");
        if(channelID == 0) {
            System.err.println("No channel ID was provided");
            return;
        }
        activityChannel = jda.getTextChannelById(channelID);
        sendStatus(true);
    }

    /**
     * Sends a message into the #activity channel
     * @param name Player name
     * @param type Kind of Event
     */
    public void sendPlayerMessage(String name, PlayerMsgType type) {
        if(activityChannel == null) return;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(String.format("Player %s (%d/%d)", type,
                PlayerStatsManager.getInstance().getCurrentSessionCount(), 40), "https://cinemacraftstudios.org/");
        eb.setDescription(String.format("%s %s the build server.", name, type));
        eb.setColor(type == PlayerMsgType.JOINED ? Color.green : Color.red);
        eb.setAuthor(name, null, "https://github.com/N0ah-S/cinemacraftstudios.com/blob/main/img/mc_logo_thumb.png?raw=true");
        eb.setTimestamp(OffsetDateTime.now());
        eb.setFooter("mc.cinemacraftstudios.org", "https://cinemacraftstudios.org/img/logo-white.PNG");
        activityChannel.sendMessageEmbeds(eb.build()).queue();
    }

    /**
     * Sends a message into the #activity channel and gracefully shuts down JDA when online == false
     * @param online whether the mc server is now online/offline
     */
    public void sendStatus(boolean online) {
        if(activityChannel == null) return; //obligatory NullPointer check

        EmbedBuilder eb = new EmbedBuilder();
        if(online) {
            eb.setTitle("The server is online now.", "https://cinemacraftstudios.org/");
            eb.setDescription("Have fun folks <:hypers:808888728889851944>");
        } else {
            eb.setTitle("The server is offline now. ", "https://cinemacraftstudios.org/");
            eb.setDescription("I'm really down rn <:sadge:797884241068425266>");
        }
        eb.setColor(online ? Color.blue : Color.red);
        eb.setTimestamp(OffsetDateTime.now());
        eb.setFooter("mc.cinemacraftstudios.org", "https://cinemacraftstudios.org/img/logo-white.PNG");
        activityChannel.sendMessageEmbeds(eb.build()).queue();

        if(!online) jda.shutdown();
    }
}
