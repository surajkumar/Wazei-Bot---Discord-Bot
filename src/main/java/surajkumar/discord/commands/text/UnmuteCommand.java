package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UnmuteCommand extends TextCommand {
    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            var role = event.getJDA().getRolesByName("Muted", true).get(0);
            var member = event.getMessage().getMentionedMembers().get(0);
            if(member != null) {
                event.getGuild().removeRoleFromMember(member, role).queue();
                event.getTextChannel().sendMessage(input + " has been un-muted.").queue();
            }
        }
    }
}
