package surajkumar.discord.commands.text;

import surajkumar.discord.Roles;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.logging.Logger;

public class ClearChannelCommand extends TextCommand {
    private static final Logger L = Logger.getLogger(ClearChannelCommand.class.getName());

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        if(hasRole(event, Roles.ADMINISTRATORS)) {
            try {
                int limit;
                try {
                    limit = Integer.parseInt(input);
                } catch (Exception e) {
                    limit = 100;
                }
                var history = event
                        .getTextChannel()
                        .getHistoryBefore(event.getMessageId(), limit)
                        .submit()
                        .get()
                        .getRetrievedHistory();
                if (history.size() > 0) {
                    event.getTextChannel()
                            .deleteMessages(history)
                            .queue();
                }
                event.getMessage().delete().queue();
                L.info("Cleared messages in #" + event.getTextChannel().getName());
            } catch (Exception e) {
                L.severe("Failed to clear history in " + event.getTextChannel().getName() + ": " + e.getMessage());
            }
        }
    }
}
