package surajkumar.discord.listener;

import surajkumar.discord.database.Hibernate;
import surajkumar.discord.database.Member;
import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild()
                .getTextChannelById("963821598966698034")
                .sendMessage(event.getUser().getAsMention() + " has joined the server");

        var member = new Member();
        member.setUserId(event.getUser().getIdLong());
        member.setMention(event.getUser().getAsMention());
        member.save();
        member.commit();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        try {
            var msg = Message.getMessages(event.getUser().getAsMention());
            for (var m : msg) {
                m.delete();
            }
            Hibernate.commit();
        } catch (Exception e) {
            /* Ignore */
        }
        try {
            var member = Member.get(event.getUser().getAsMention());
            if (member != null) {
                member.delete();
                member.commit();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        event.getGuild()
                .getTextChannelById("963821598966698034")
                .sendMessage(event.getUser().getAsMention() + " has left the server");
    }
}