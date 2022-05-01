package surajkumar.discord.commands.slash.music;

import surajkumar.discord.commands.slash.SlashCommand;
import surajkumar.discord.commands.text.music.PlayMusicCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class StopMusicCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        if(event.getGuild() != null) {
            PlayMusicCommand.getGuildAudioPlayer(event.getGuild()).scheduler.stop();
            event.reply("Music has been stopped.").queue();
        }
    }

    @Override
    public String getDescription() {
        return "Stops any music currently playing in the voice chat.";
    }
}
