package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import surajkumar.discord.listener.MessageListener;

import static surajkumar.discord.listener.MessageListener.create;

public class SayEmbedCommand extends TextCommand {
    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            event.getMessage()
                    .delete()
                    .queue();
            event.getTextChannel()
                    .sendMessageEmbeds(MessageListener.create("", input, ""))
                    .queue();
        }
    }
}
