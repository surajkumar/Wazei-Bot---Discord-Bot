package surajkumar.discord.commands.text;

import java.util.HashMap;
import java.util.Map;

public class TextCommandRepository {
    private static final Map<String, TextCommand> COMMANDS = new HashMap<>();
    static {
        /* Admin */
        COMMANDS.put("embed", new SayEmbedCommand());
        COMMANDS.put("say", new SayCommand());
        COMMANDS.put("mute", new MuteCommand());
        COMMANDS.put("unmute", new UnmuteCommand());
        COMMANDS.put("clear", new ClearChannelCommand());
        COMMANDS.put("rr", new SetupReactionCommand());
        COMMANDS.put("update", new UpdateStatisticsCommand());
        COMMANDS.put("words", new WordCountCommand());
        COMMANDS.put("all", new AllUserWordsCommand());
        COMMANDS.put("purge", new PurgeNonSpeakersCommand());

        /* Everyone */
        COMMANDS.put("lastspoke", new LastSpeakCommand());
    }

    public static TextCommand get(String key) {
        return COMMANDS.get(key);
    }
}