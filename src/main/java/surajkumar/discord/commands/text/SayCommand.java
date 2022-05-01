package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SayCommand extends TextCommand {
    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage(input).queue();
        }
    }
}
