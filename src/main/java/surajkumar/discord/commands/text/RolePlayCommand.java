package surajkumar.discord.commands.text;

import surajkumar.discord.Tenor;
import surajkumar.discord.listener.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RolePlayCommand extends TextCommand {

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        String content = event.getMessage().getContentRaw();
        String[] split = content.split("-");
        String action = split[1].trim();
        String name = event.getMessage().getMentionedMembers().get(0).getAsMention();
        String from = event.getAuthor().getAsMention();

        name = event.getMessage().getMentionedMembers().get(0).getNickname();
        from = event.getMember().getNickname();

        if(name == null || name.equalsIgnoreCase("null")) {
            name = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
        }

        if(from == null || from.equalsIgnoreCase("null")) {
            from = event.getMember().getEffectiveName();
        }

        if(name.equalsIgnoreCase("null")) {
            name = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
        }

        if(from.equalsIgnoreCase("null")) {
            from = event.getMember().getEffectiveName();
        }

        String searchTerm = action.replaceAll(" ", "%20") + "%20anime";
        JSONObject json = Tenor.search(searchTerm, 50);

        JSONArray results = json.getJSONArray("results");
        int random = ThreadLocalRandom.current().nextInt(results.length());

        JSONObject result = results.getJSONObject(random);
        JSONObject media = result.getJSONArray("media").getJSONObject(0);
        JSONObject gif = media.getJSONObject("gif");
        String url = gif.getString("url");

        var msg = MessageListener.create("", "**" + from + "** " + action + " **" + name + "**", url, Color.RED);
        event.getTextChannel().sendMessageEmbeds(msg).queue();
    }
}
