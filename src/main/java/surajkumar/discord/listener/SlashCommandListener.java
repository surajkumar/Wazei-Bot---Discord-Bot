package surajkumar.discord.listener;

import surajkumar.discord.commands.slash.SlashCommandRepository;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.logging.Logger;

public class SlashCommandListener extends ListenerAdapter {
    private static final Logger L = Logger.getLogger(SlashCommandListener.class.getName());

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        var author = event.getUser();
        if (!author.isBot()) {
            var input = event.getName();
            var command = SlashCommandRepository.get(input);
            if (command != null) {
                L.info("Received slash command [" + input + "] from " + author.getName());
                command.handle(event);
            } else {
                L.warning("Unknown slash command [" + input + "] from " + author.getName());
                event.reply("Unknown command " + input).queue();
            }
        }
    }
}
