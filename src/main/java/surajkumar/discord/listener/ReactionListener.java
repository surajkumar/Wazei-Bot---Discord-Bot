package surajkumar.discord.listener;

import surajkumar.discord.database.ReactionRole;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ReactionListener extends ListenerAdapter {
    private static final Logger L = Logger.getLogger(ReactionListener.class.getName());

    @Override
    public void onGenericMessageReaction(GenericMessageReactionEvent event) {
        if(event.getMember() != null && !(event.getUser() == null && event.getUser().isBot())) {
            String reaction;
            if(event.getReactionEmote().isEmoji()) {
                reaction = MessageListener.escapeUnicode(event.getReactionEmote().getEmoji());
            } else {
                reaction = "<:" + event.getReactionEmote().getEmote().getName() + ":" + event.getReactionEmote().getEmote().getId() + ">";
            }
            var rr = ReactionRole.getReaction(event.getMessageId(), reaction);
            if (rr != null) {
                update(event, rr.getRole());
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {

    }

    private static void update(GenericMessageReactionEvent event, String roleName) {
        if(event.getMember() != null) {
            String memberId = event.getMember().getId();
            AtomicBoolean hasRole = new AtomicBoolean(false);
            event.getMember().getRoles().forEach(role -> {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    hasRole.set(true);
                }
            });
            Role memberRole = event.getJDA().getRolesByName(roleName, true).get(0);
            if (hasRole.get()) {
                event.getGuild().removeRoleFromMember(memberId, memberRole).queue();
            } else {
                event.getGuild().addRoleToMember(memberId, memberRole).queue();
                if(roleName.equalsIgnoreCase("Member")) {
                    event.getGuild().getTextChannelById("962633171990618154").sendMessage("\uD83C\uDF1F Everybody, please welcome to our newest member " + event.getMember().getAsMention() + " to the server! \uD83C\uDF20").queue();
                    event.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(MessageListener.create("Welcome to the server " + event.getUser().getName(), "Welcome to Wazei's \uD835\uDE51\uD835\uDE56\uD835\uDE61\uD835\uDE64\uD835\uDE67\uD835\uDE56\uD835\uDE63\uD835\uDE69 Server! :heart_on_fire:\n" +
                            "\n" +
                            "* Please ensure you check out the #\uD83D\uDC6Erules section\n" +
                            "* Grab some roles from #\uD83E\uDD39roles\n" +
                            "* Play some Valorant with our members :100:\n" +
                            "\n" +
                            "The server invite link is: https://discord.com/invite/smzEXmJuVv\n" +
                            "The DISBOARD page: https://disboard.org/server/962633171298553926\n" +
                            "\n" +
                            ":star:Thank you for joining the server!:star:", "")).queue());
                }
            }
        }
    }
}
