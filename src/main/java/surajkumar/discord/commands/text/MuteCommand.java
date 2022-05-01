package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.logging.Logger;

public class MuteCommand extends TextCommand {
    private static final Logger L = Logger.getLogger(MuteCommand.class.getName());

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            var role = event.getJDA().getRolesByName("Muted", true).get(0);
            var member = event.getMessage().getMentionedMembers().get(0);
            if(member != null) {
                event.getGuild().addRoleToMember(member, role).queue();
                event.getTextChannel().sendMessage(input + " has been muted.").queue();
            } else {
                event.getTextChannel().sendMessage(input + " not found.").queue();
            }
        }
    }
}
