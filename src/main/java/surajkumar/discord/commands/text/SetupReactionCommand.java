package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import surajkumar.discord.database.ReactionRole;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Formatter;
import java.util.logging.Logger;

public class SetupReactionCommand extends TextCommand {
    private static final Logger L = Logger.getLogger(SetupReactionCommand.class.getName());

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            var parts = input.split("\\|");
            var messageId = parts[0];
            var emoji = parseEmoji(parts[1]);
            var role = parts[2];
            L.info(String.format("Registering rr: [role=%s][emoji=%s][messageId=%s]", role, emoji, messageId));
            var react = new ReactionRole();
            react.setMessageId(messageId);
            react.setEmoji(emoji);
            react.setRole(role.trim());
            react.save();
            react.commit();
            if (emoji.startsWith("<")) {
                var id = emoji.substring(emoji.lastIndexOf(":") + 1, emoji.length() - 1);
                var emote = event.getGuild().getEmoteById(id);
                if (emote != null) {
                    event.getTextChannel().addReactionById(messageId, emote).queue();
                }
            } else {
                event.getTextChannel().addReactionById(messageId, parts[1]).queue();
            }
            event.getMessage().delete().queue();
        }
    }

    private String parseEmoji(String input) {
        if(input.startsWith("<")) {
            return input;
        }
        return escapeUnicode(input);
    }

    public String escapeUnicode(String input) {
        StringBuilder b = new StringBuilder(input.length());
        Formatter f = new Formatter(b);
        for (char c : input.toCharArray()) {
            if (c < 128) {
                b.append(c);
            } else {
                f.format("\\u%04x", (int) c);
            }
        }
        return b.toString();
    }
}