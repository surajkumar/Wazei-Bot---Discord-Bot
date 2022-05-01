package surajkumar.discord.commands.slash.music;

import surajkumar.discord.commands.slash.SlashCommand;
import surajkumar.discord.commands.text.music.PlayMusicCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class NextSongCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        var player = PlayMusicCommand.getGuildAudioPlayer(event.getGuild());
        if(player.scheduler.size() > 0) {
            event.reply("Playing " + player.scheduler.nextTrack().getInfo().title).queue();
        } else {
            event.reply("No music in queue left to play.").queue();
        }
    }

    @Override
    public String getDescription() {
        return "Skips to the next song within the queue";
    }
}
