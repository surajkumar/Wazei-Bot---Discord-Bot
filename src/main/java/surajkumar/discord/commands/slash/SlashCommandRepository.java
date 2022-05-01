package surajkumar.discord.commands.slash;

import surajkumar.discord.commands.slash.music.*;

import java.util.HashMap;
import java.util.Map;

public class SlashCommandRepository {
    private static final Map<String, SlashCommand> COMMANDS = new HashMap<>();
    static {
        COMMANDS.put("twitch", new TwitchCommand());
        COMMANDS.put("ping", new PingCommand());
        COMMANDS.put("pp", new PleasePPCommand());
        COMMANDS.put("memes", new MemeCommand());
        COMMANDS.put("rate", new RateCommand());
        COMMANDS.put("messages", new WordsCommand());

        /* Music */
        COMMANDS.put("play", new PlayCommand());
        COMMANDS.put("stop", new StopMusicCommand());
        COMMANDS.put("forward", new ForwardTrackCommand());
        COMMANDS.put("rewind", new RewindTrackCommand());
        COMMANDS.put("next", new NextSongCommand());
        COMMANDS.put("queue", new QueueMusicCommand());
        COMMANDS.put("leave", new LeaveCommand());
    }

    public static SlashCommand get(String key) {
        return COMMANDS.get(key);
    }

    public static Map<String, SlashCommand> getCommands() {
        return COMMANDS;
    }
}