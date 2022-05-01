package surajkumar.discord.listener;

import com.vdurmont.emoji.EmojiParser;
import surajkumar.discord.SpamHandler;
import surajkumar.discord.commands.text.TextCommandRepository;
import surajkumar.discord.commands.text.RolePlayCommand;
import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.Logger;

public class MessageListener extends ListenerAdapter {
    private static final Logger L = Logger.getLogger(MessageListener.class.getName());

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!event.getAuthor().isBot()) {
            var content = event
                    .getMessage()
                    .getContentRaw()
                    .trim();
            if (content.startsWith("!")) {
                var cmd = content.split(" ")[0].replace("!", "").trim();
                var input = content.replace(String.format("!%s", cmd), "").trim();
                var command = TextCommandRepository.get(cmd);
                if (command != null) {
                    L.info("Received command [" + cmd + "] from "
                            + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator());
                    command.handle(event, input);
                }
            } else if(containsRolePlayAction(content) && event.getMessage().getMentionedMembers().size() > 0) {
                new RolePlayCommand().handle(event, content);
            } else {
                SpamHandler.muteIfNeeded(event);
                var message = new Message();
                message.setChannelName(EmojiParser.removeAllEmojis(event.getChannel().getName()));
                message.setMessage(EmojiParser.removeAllEmojis(content));
                message.setUserId(event.getAuthor().getIdLong());
                message.setTimestamp(new Date());
                message.setMention(event.getAuthor().getAsMention());
                message.save();
                message.commit();
            }
        }
    }

    private static boolean containsRolePlayAction(String message) {
        return message.matches(".*-.*-.*");
    }

    public static MessageEmbed create(String title, String caption, String imageURL) {
        EmbedBuilder eb = new EmbedBuilder();
        if(imageURL.length() > 0) {
            eb.setImage(imageURL);
        }
        eb.setAuthor(title, null, null);
        eb.setDescription(caption);
        return eb.build();
    }

    public static MessageEmbed create(String title, String caption, String imageURL, Color color) {
        EmbedBuilder eb = new EmbedBuilder();
        if(imageURL.length() > 0) {
            eb.setImage(imageURL);
        }
        eb.setAuthor(title, null, null);
        eb.setDescription(caption);
        eb.setColor(color);
        return eb.build();
    }

    public static String escapeUnicode(String input) {
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
