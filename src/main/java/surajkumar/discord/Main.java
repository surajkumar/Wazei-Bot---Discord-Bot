package surajkumar.discord;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import surajkumar.discord.commands.slash.SlashCommandRepository;
import surajkumar.discord.commands.slash.music.SlashCommandOption;
import surajkumar.discord.database.Hibernate;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import surajkumar.discord.listener.MessageListener;
import surajkumar.discord.listener.ReactionListener;
import surajkumar.discord.listener.SlashCommandListener;
import surajkumar.discord.listener.UserJoinListener;

public class Main {
    private static final Logger L = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        if(Hibernate.isReady()) {
            L.info(initiateConnectionWithDiscord(Files.readString(Path.of("token.txt")))
                    ? "Connection to Discord successful."
                    : "Failed to initiate a connection with Discord.");
        } else {
            L.severe("Unable to connect to database");
        }
    }

    private static boolean initiateConnectionWithDiscord(String botToken) {
        boolean connected;
        try {
            JDA jda = JDABuilder.createDefault(botToken)
                    .addEventListeners(new MessageListener())
                    .addEventListeners(new ReactionListener())
                    .addEventListeners(new SlashCommandListener())
                    .addEventListeners(new UserJoinListener())
                    .setStatus(OnlineStatus.ONLINE)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build()
                    .awaitReady();
            connected = jda.getStatus() == JDA.Status.CONNECTED;
            if(connected) {
                SlashCommandRepository.getCommands().forEach((k,v) -> {
                    for(SlashCommandOption option : v.getOptions()) {
                        jda.upsertCommand(new CommandData(k, v.getDescription()).addOption(option.getType(), option.getName(), option.getDescription())).queue();
                    }
                });
            }
        } catch (Exception e) {
            L.info("Failed to setup: " + e.getMessage());
            connected = false;
        }
        return connected;
    }
}