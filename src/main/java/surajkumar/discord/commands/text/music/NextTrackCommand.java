package surajkumar.discord.commands.text.music;

import surajkumar.discord.commands.text.TextCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NextTrackCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        var player = PlayMusicCommand.getGuildAudioPlayer(event.getGuild());
        if(player.scheduler.size() > 0) {
            event.getTextChannel().sendMessage("Playing " + player.scheduler.nextTrack().getInfo().title).queue();
        } else {
            event.getTextChannel().sendMessage("No music in queue left to play.").queue();
        }
    }
}
