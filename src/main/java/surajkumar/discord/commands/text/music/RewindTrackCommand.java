package surajkumar.discord.commands.text.music;

import surajkumar.discord.commands.text.TextCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RewindTrackCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        int amount;
        try {
            amount = Integer.parseInt(input) * 1000;
        } catch (Exception e) {
            amount = 5000;
        }
        var player = PlayMusicCommand.getGuildAudioPlayer(event.getGuild());
        player.scheduler.rewind(amount);
        event.getTextChannel().sendMessage("Buffering").queue();
    }
}
