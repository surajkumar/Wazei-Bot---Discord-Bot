package surajkumar.discord.database;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "discord_messages")
public class Message extends Persistable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "channelName")
    private String channelName;

    @Column(name = "message")
    private String message;

    @Column(name = "mention")
    private String mention;

    @Column(name = "timestamp")
    private Date timestamp;

    public Message() {}

    @SuppressWarnings("unchecked")
    public static List<Message> getMessages(String mention) {
        return (List<Message>) Hibernate.queryDatabase("from Message WHERE mention=:mention ORDER BY timestamp",
                new HashMap<>() {{
                    put("mention", mention);
                }});
    }

    @SuppressWarnings("unchecked")
    public static Message getLast(String mention) {
        var messages = (List<Message>) Hibernate.queryDatabase("from Message WHERE mention=:mention AND id = (SELECT max(id) FROM Message)  ORDER BY timestamp",
                new HashMap<>() {{
                    put("mention", mention);
                }});
        if(messages.size() > 0) {
            return messages.get(0);
        }
        return null;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }
}
