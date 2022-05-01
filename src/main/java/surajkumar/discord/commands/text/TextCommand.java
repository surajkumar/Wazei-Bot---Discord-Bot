package surajkumar.discord.commands.text;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class TextCommand {
    public abstract void handle(MessageReceivedEvent event, String input);

    public boolean hasRole(MessageReceivedEvent event, String roleName) {
        var hasRole = new AtomicBoolean(false);
        if(event.getMember() != null) {
            if(event.getMember().isOwner()) {
                hasRole.set(true);
            } else {
                event.getMember().getRoles().forEach(role -> {
                    if (role.getName().equalsIgnoreCase(roleName)) {
                        hasRole.set(true);
                    }
                });
            }
        }
        return hasRole.get();
    }

}
