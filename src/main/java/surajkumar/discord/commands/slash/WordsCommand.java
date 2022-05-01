package surajkumar.discord.commands.slash;

import surajkumar.discord.database.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class WordsCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandEvent event) {
        var messages = Message.getMessages(event.getUser().getAsMention());
        event.reply("You have sent a total of " + messages.size() + " message(s) within this server.").queue();
    }

    @Override
    public String getDescription() {
        return "Shows you how many messages you have sent within this server";
    }
}