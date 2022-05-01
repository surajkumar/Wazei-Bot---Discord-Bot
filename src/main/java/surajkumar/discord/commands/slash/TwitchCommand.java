package surajkumar.discord.commands.slash;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TwitchCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        event.reply("https://twitch.tv/wazeii").queue();
    }

    @Override
    public String getDescription() {
        return "The link to Wazeii's Twitch channel";
    }
}