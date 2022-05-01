package surajkumar.discord;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpamHandler {
    static class Spam {
        private String mention;
        private long lastMessage;
        private int count;

        public Spam(String mention, long lastMessage, int count) {
            this.mention = mention;
            this.lastMessage = lastMessage;
            this.count = count;
        }

        public String getMention() {
            return mention;
        }

        public long getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(long lastMessage) {
            this.lastMessage = lastMessage;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
    private static final List<Spam> spamList = new ArrayList<>();

    public static Spam get(String mention) {
        Spam spam = null;
        for(Spam s : spamList) {
            if(s.getMention().equalsIgnoreCase(mention)) {
                spam = s;
                break;
            }
        }
        if(spam == null) {
            spam = new Spam(mention, System.currentTimeMillis(), 1);
            spamList.add(spam);
        }
        return spam;
    }

    private static Spam update(String mention) {
        Spam spam = get(mention);
        spam.setCount(spam.getCount() + 1);
        return spam;
    }

    public static void muteIfNeeded(MessageReceivedEvent event) {
        Spam spam = update(event.getAuthor().getAsMention());
        var last = spam.getLastMessage();
        var current = System.currentTimeMillis();
        spam.setLastMessage(System.currentTimeMillis());
        if((current - last) < 1000) {
            if(spam.getCount() >= 3) {
                Role mute = event.getJDA().getRolesByName("Muted", true).get(0);
                event.getGuild().addRoleToMember(event.getMember().getId(), mute).queue();
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " has been muted for 1 minute for spamming.").queue();
                new Thread(() -> {
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        /* Ignore */
                    }
                    spam.setCount(0);
                    event.getGuild().removeRoleFromMember(event.getMember().getId(), mute).queue();
                }).start();
            }
        } else {
            spam.setCount(0);
        }
    }
}
