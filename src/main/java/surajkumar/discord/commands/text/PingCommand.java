package surajkumar.discord.commands.text;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        var current = System.currentTimeMillis();
        long msg = event.getMessage().getTimeCreated().toInstant().toEpochMilli();
        long elapsed  = current - msg;
        event.getTextChannel().sendMessage("Pong! " + elapsed + "ms").queue();
    }
}
