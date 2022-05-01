package surajkumar.discord.commands.text;

import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WordsCommand extends TextCommand {
    @Override
    public void handle(MessageReceivedEvent event, String input) {
        var messages = Message.getMessages(event.getAuthor().getAsMention());
        event.getTextChannel().sendMessage("You have sent a total of " + messages.size() + " message(s) within this server.").queue();
    }
}
