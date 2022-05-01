package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AllUserWordsCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            var sb = new StringBuilder();
            Role memberRole = event.getJDA().getRolesByName("Member", true).get(0);
            event.getGuild().getMembersWithRoles(memberRole).forEach(member -> {
                var messages = Message.getMessages(member.getAsMention());
                sb.append(member.getUser().getName() + "#" + member.getUser().getDiscriminator() + " has sent " + messages.size() + " messages\n");
            });
            event.getTextChannel().sendMessage(sb).queue();
        }
    }
}