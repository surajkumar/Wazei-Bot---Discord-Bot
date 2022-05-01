package surajkumar.discord.commands.text;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;

public class UpdateStatisticsCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        var first = event.getGuild().getCategories().get(1);
        if(first.getName().startsWith("Total")) {
            first.delete().queue();
        }
        event.getGuild().createCategory("Total members " + (event.getGuild().getMemberCount()-2)).setPosition(1).queue();
    }

    public static void handle(GenericMessageReactionEvent event) {
        var first = event.getGuild().getCategories().get(1);
        if(first.getName().startsWith("Total")) {
            first.delete().queue();
        }
        event.getGuild().createCategory("Total members " + (event.getGuild().getMemberCount()-2)).setPosition(1).queue();
    }
}
