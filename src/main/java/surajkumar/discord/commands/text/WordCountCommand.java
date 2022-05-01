package surajkumar.discord.commands.text;

import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.logging.Logger;

public class WordCountCommand extends TextCommand {
    private static final Logger L = Logger.getLogger(WordCountCommand.class.getName());

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        try {
            String name;
            if(input.length() > 0) {
                name = input;
            } else {
                name = event.getAuthor().getAsMention();
            }
            System.out.println(name + ":" + input);
            var messages = Message.getMessages(name);
            event
                    .getTextChannel()
                    .sendMessage(name + " has sent a total of " + messages.size() + " message(s) within this server.")
                    .queue();

        } catch (Exception e) {
            L.severe(e.getMessage());
        }
    }
}
