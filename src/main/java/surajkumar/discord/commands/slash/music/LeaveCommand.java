package surajkumar.discord.commands.slash.music;

import surajkumar.discord.commands.slash.SlashCommand;
import surajkumar.discord.commands.text.music.PlayMusicCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class LeaveCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        event.getGuild().getAudioManager().closeAudioConnection();
        PlayMusicCommand.getGuildAudioPlayer(event.getGuild()).scheduler.stop();
        event.reply(":wave:").queue();
    }

    @Override
    public String getDescription() {
        return "Leaves the voice chat";
    }
}
