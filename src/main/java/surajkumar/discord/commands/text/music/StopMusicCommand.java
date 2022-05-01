package surajkumar.discord.commands.text.music;

import surajkumar.discord.commands.text.TextCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StopMusicCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        PlayMusicCommand.getGuildAudioPlayer(event.getGuild()).scheduler.stop();
        event.getTextChannel().sendMessage("Music has been stopped.").queue();
    }
}
