package surajkumar.discord.commands.slash;

import surajkumar.discord.Reddit;
import surajkumar.discord.listener.MessageListener;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;

public class MemeCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        var msg = MessageListener.create("Memes", "", Reddit.getRandomMeme(), Color.RED);
        event.replyEmbeds(msg).queue();
    }

    @Override
    public String getDescription() {
        return "Sends a random meme from Reddit";
    }
}
