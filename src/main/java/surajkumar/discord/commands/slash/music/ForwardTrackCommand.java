package surajkumar.discord.commands.slash.music;

import surajkumar.discord.commands.slash.SlashCommand;
import surajkumar.discord.commands.text.music.PlayMusicCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Objects;

public class ForwardTrackCommand extends SlashCommand {

    public ForwardTrackCommand() {
        addOption(new SlashCommandOption("seconds", "The number of seconds to forward into the track", OptionType.INTEGER));
    }

    @Override
    public void handle(SlashCommandEvent event) {
        var amount = (int) Objects.requireNonNull(event.getOption("seconds")).getAsLong();
        var player = PlayMusicCommand.getGuildAudioPlayer(Objects.requireNonNull(event.getGuild()));
        player.scheduler.fastforward(amount);
        event.getTextChannel().sendMessage("Buffering").queue();
    }

    @Override
    public String getDescription() {
        return "Forwards the current playing song by x seconds";
    }
}
