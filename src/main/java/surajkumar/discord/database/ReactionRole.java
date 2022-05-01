package surajkumar.discord.database;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "discord_rr")
public class ReactionRole extends Persistable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "messageId")
    private String messageId;

    @Column(name = "emoji")
    private String emoji;

    @Column(name = "role")
    private String role;

    public ReactionRole() {}

    @SuppressWarnings("unchecked")
    public static ReactionRole getReaction(String messageId, String emoji) {
        try {
            return (ReactionRole) Hibernate.queryDatabase("from ReactionRole WHERE messageId=:messageId AND emoji=:emoji",
                    new HashMap<>() {{
                        put("messageId", messageId);
                        put("emoji", emoji);
                    }}).get(0);
        } catch (Exception e) {
           System.err.println(e.getMessage());
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
