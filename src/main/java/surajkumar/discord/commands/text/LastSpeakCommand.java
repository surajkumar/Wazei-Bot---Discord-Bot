package surajkumar.discord.commands.text;

import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;

public class LastSpeakCommand extends TextCommand {
    private static final SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        var message = Message.getLast(input);
        if(message != null) {
            event.getTextChannel().sendMessage(input + " last spoke on the " + dt.format(message.getTimestamp())).queue();
        } else {
            event.getTextChannel().sendMessage(input + " has never spoken in this server").queue();
        }
    }
}
