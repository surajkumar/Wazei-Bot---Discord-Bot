package surajkumar.discord.commands.slash.music;

import surajkumar.discord.commands.slash.SlashCommand;
import surajkumar.discord.commands.text.music.PlayMusicCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class RewindTrackCommand extends SlashCommand {

    public RewindTrackCommand() {
        addOption(new SlashCommandOption("seconds", "The number of seconds to rewind the track", OptionType.INTEGER));
    }

    @Override
    public void handle(SlashCommandEvent event) {
        var amount = (int) event.getOption("seconds").getAsLong();
        var player = PlayMusicCommand.getGuildAudioPlayer(event.getGuild());
        player.scheduler.rewind(amount);
        event.reply("Buffering").queue();
    }

    @Override
    public String getDescription() {
        return "Rewinds the current playing song by x seconds";
    }
}
