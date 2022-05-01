package surajkumar.discord.commands.text.music;

import surajkumar.discord.commands.text.TextCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LeaveCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        event.getGuild().getAudioManager().closeAudioConnection();
        PlayMusicCommand.getGuildAudioPlayer(event.getGuild()).scheduler.stop();
    }
}
