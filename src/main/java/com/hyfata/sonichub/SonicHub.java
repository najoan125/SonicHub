package com.hyfata.sonichub;

import com.hyfata.json.exceptions.JsonEmptyException;
import com.hyfata.sonichub.terminal.TerminalManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class SonicHub {
    public final static Logger LOG = LoggerFactory.getLogger(SonicHub.class);
    private static TerminalManager terminalManager;

    public final static Permission[] RECOMMENDED_PERMS = {Permission.MESSAGE_SEND, Permission.MESSAGE_EMBED_LINKS, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK};
    public final static GatewayIntent[] INTENTS = {GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES};

    public static void main(String[] args) throws IOException {
        // init terminal manager
        terminalManager = new TerminalManager();

        // load config
        BotConfig config = new BotConfig();
        LOG.info("Loading config.json...");
        try {
            config.load();
        } catch (IOException e) {
            LOG.error("Error during load config.json", e);
            return;
        } catch (JsonEmptyException e) {
            LOG.error("config.json is empty.");
            return;
        }
        if (!config.isLoadSuccess()) {
            LOG.warn("Please set the token and ownerId correctly.");
            LOG.info("Shutting down...");
            System.exit(-1);
            return;
        }
        LOG.info("Config load successfully!");

        Bot bot = new Bot();
        terminalManager.startInputThread(bot);

        JDA jda = JDABuilder.createLight(config.getToken(), Arrays.asList(INTENTS))
                .addEventListeners(new Listener())
                .setActivity(Activity.playing("test"))
                .build();
    }

    public static TerminalManager getTerminalManager() {
        return terminalManager;
    }
}
