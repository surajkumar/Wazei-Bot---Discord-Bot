package surajkumar.discord.commands.slash;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class PingCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        event.reply("Pong!").queue();
    }

    @Override
    public String getDescription() {
        return "Pings the bot to see if we're alive.";
    }
}
