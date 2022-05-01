package surajkumar.discord.commands.slash;

import surajkumar.discord.commands.slash.music.SlashCommandOption;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.concurrent.ThreadLocalRandom;

public class RateCommand extends SlashCommand {

    public RateCommand() {
        addOption(new SlashCommandOption("user", "The user to rate", OptionType.USER));
    }

    @Override
    public void handle(SlashCommandEvent event) {
        var rating = ThreadLocalRandom.current().nextInt(10);
        event.reply("I rate " + event.getOption("user").getAsMentionable().getAsMention() + " " + rating + "/10 :heart:").queue();
    }

    @Override
    public String getDescription() {
        return "Rates the mentioned user between 1-10";
    }
}
