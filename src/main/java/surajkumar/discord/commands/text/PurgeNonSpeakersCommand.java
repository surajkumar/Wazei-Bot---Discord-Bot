package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import surajkumar.discord.database.Member;
import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class PurgeNonSpeakersCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            var count = new AtomicInteger(0);
            var members = Member.getMembers();
            members.forEach(member -> {
                var messages = Message.getMessages(member.getMention());
                if (messages.size() <= 0) {
                    event.getTextChannel().sendMessage("Kicking " + member.getMention());
                    var memb = event.getGuild().getMemberById(member.getUserId());
                    if(memb != null) {
                        memb.kick("User has not spoken within this Discord server.").queue();
                    }
                    member.delete();
                    member.commit();
                    count.getAndIncrement();
                } else {
                    event.getTextChannel().sendMessage("Kicking " + member.getMention());
                }
            });
            event.getTextChannel().sendMessage("Kicked a total of " + count + " for never speaking within this server.").queue();
        }
    }
}
