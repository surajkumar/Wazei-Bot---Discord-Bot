package surajkumar.discord.database;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "discord_members")
public class Member extends Persistable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "mention")
    private String mention;

    public Member() {}

    @SuppressWarnings("unchecked")
    public static List<Member> getMembers() {
        return (List<Member>) Hibernate.queryDatabase("from Members",  new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public static Member get(String mention) {
        var messages = (List<Member>) Hibernate.queryDatabase("from Member WHERE mention=:mention",
                new HashMap<>() {{
                    put("mention", mention);
                }});
        if(messages.size() > 0) {
            return messages.get(0);
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }
}
