package org.cinemacraftstudios.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.utils.Timestamp;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import org.cinemacraftstudios.discord.PlayerMsgType;
import org.cinemacraftstudios.main.CCSActivityTracker;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;

public class CCSDiscordIntegration implements EventListener {

    private final JDA jda;
    private TextChannel activityChannel;

    public CCSDiscordIntegration(CCSActivityTracker plugin) {
        FileConfiguration conf = plugin.getConfig();

        JDABuilder builder = JDABuilder.createDefault(conf.getString("dc.token", "idiot"));

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.customStatus("having an existential crisis"));
        builder.addEventListeners(this); //wait for JDA to finish setup

        jda = builder.build();
    }

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {

        if (!(genericEvent instanceof ReadyEvent)) {
            System.out.println("genericEvent = " + genericEvent);
            return;
        }

        activityChannel = jda.getTextChannelById(730178710262513774L);
        sendStatus(true);
    }

    public void sendPlayerMessage(String name, PlayerMsgType type, int currentPlayers, int maxPlayers) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(String.format("Player %s (%d/%d)", type,
                currentPlayers, maxPlayers), "https://cinemacraftstudios.org/");
        eb.setDescription(String.format("%s %s the build server.", name, type));
        eb.setColor(type == PlayerMsgType.JOINED ? Color.green : Color.red);
        eb.setAuthor(name, null, "https://github.com/N0ah-S/cinemacraftstudios.com/blob/main/img/mc_logo_thumb.png?raw=true");
        eb.setTimestamp(OffsetDateTime.now());
        eb.setFooter("mc.cinemacraftstudios.org", "https://cinemacraftstudios.org/img/logo-white.PNG");
        activityChannel.sendMessageEmbeds(eb.build()).queue();
    }

    public void sendStatus(boolean online) {
        EmbedBuilder eb = new EmbedBuilder();
        if(online) {
            eb.setTitle("The server is online now.", "https://cinemacraftstudios.org/");
            eb.setDescription("Have fun folks <:hypers:808888728889851944>");
        } else {
            eb.setTitle("The server is offline now. ", "https://cinemacraftstudios.org/");
            eb.setDescription("I'm really down rn <:sadge:797884241068425266>");
        }
        eb.setColor(online ? Color.blue : Color.red);
        //eb.setAuthor("Gonk", null, "https://github.com/N0ah-S/cinemacraftstudios.com/blob/main/img/mc_logo_thumb.png?raw=true");
        eb.setTimestamp(OffsetDateTime.now());
        eb.setFooter("mc.cinemacraftstudios.org", "https://cinemacraftstudios.org/img/logo-white.PNG");
        activityChannel.sendMessageEmbeds(eb.build()).queue();
    }


}
