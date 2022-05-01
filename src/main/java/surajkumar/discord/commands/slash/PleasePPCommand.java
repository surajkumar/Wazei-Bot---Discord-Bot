package surajkumar.discord.commands.slash;

import surajkumar.discord.commands.slash.music.SlashCommandOption;
import surajkumar.discord.listener.MessageListener;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class PleasePPCommand extends SlashCommand {

    public PleasePPCommand() {
        addOption(new SlashCommandOption("user", "The person to measure", OptionType.USER));
    }

    @Override
    public void handle(SlashCommandEvent event) {
        if(event.getTextChannel().isNSFW()) {
            var size = ThreadLocalRandom.current().nextInt(12);
            var say = String.format("%s%sD ~", String.format("%s has %d\" pp!\n8", event.getOption("user").getAsMentionable().getAsMention(), size), "=".repeat(size));
            var msg = MessageListener.create("Wazei's PP Detector", say, "https://discordemoji.com/assets/emoji/1205_winky_wanky.gif", Color.RED);
            event.replyEmbeds(msg).queue();
        } else {
            event.reply("You can only do this in a nsfw channel").queue();
        }
    }

    @Override
    public String getDescription() {
        return "A fun NSFW command that tells you the mentioned users pp size";
    }
}
