package surajkumar.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Rules {

    public static void run(MessageReceivedEvent event) {
        var e1 = create(
                "General Server Rules",
                "* No blank nicknames.\n" +
                        "* No inappropriate nicknames.\n" +
                        "* No sexually explicit nicknames.\n" +
                        "* No offensive nicknames.\n" +
                        "* No nicknames with unusual or unreadable Unicode.\n" +
                        "* No blank profile pictures.\n" +
                        "* No inappropriate profile pictures.\n" +
                        "* No sexually explicit profile pictures.\n" +
                        "* No offensive profile pictures.\n" +
                        "* Moderators reserve the right to change nicknames.\n" +
                        "* Moderators reserve the right to use their own discretion regardless of any rule.\n" +
                        "* No exploiting loopholes in the rules (please report them).\n" +
                        "* No DMing other members of the server.\n" +
                        "* Rules apply to DMing other members of the server.\n" +
                        "* No inviting unofficial bots.\n" +
                        "* No bugs, exploits, glitches, hacks, bugs, etc.",
                "");

        var e2 = create(
                "Text Chat Rules",
                "* No questioning the mods.\n" +
                        "* No @mentioning the mods.\n" +
                        "* No asking to be granted roles/moderator roles.\n" +
                        "* @mention the moderators for support.\n" +
                        "* No @mentioning spam.\n" +
                        "* No sexually explicit content.\n" +
                        "* No pornographic content.\n" +
                        "* No NSFW content.\n" +
                        "* No illegal content.\n" +
                        "* No piracy.\n" +
                        "* No modding.\n" +
                        "* No homebrew.\n" +
                        "* No hacking.\n" +
                        "* No publishing of personal information (including real names, addresses, emails, passwords, bank account and credit card information, etc.).\n" +
                        "* No personal attacks.\n" +
                        "* No witch hunting.\n" +
                        "* No harassment.\n" +
                        "* No sexism.\n" +
                        "* No racism.\n" +
                        "* No hate speech.\n" +
                        "* No offensive language/cursing.\n" +
                        "* No religious discussions.\n" +
                        "* No political discussions.\n" +
                        "* No sexual discussions.\n" +
                        "* No flirting.\n" +
                        "* No dating.\n" +
                        "* No flaming.\n" +
                        "* No flame wars.\n" +
                        "* Agree to disagree.\n" +
                        "* No trolling.\n" +
                        "* No spamming.\n" +
                        "* No excessive messaging (breaking up an idea in many posts instead of writing all out in just one post).\n" +
                        "* No walls of text (either in separate posts or as a single post).\n" +
                        "* No CAPS LOCK.\n" +
                        "* No overusing emojis.\n" +
                        "* No overusing reactions.\n" +
                        "* No external emojis.\n" +
                        "* Keep conversations in English.\n" +
                        "* Moderators reserve the right to delete any post.\n" +
                        "* Moderators reserve the right to edit any post.\n" +
                        "* No advertisement without permission.\n" +
                        "* No linking to other servers.\n" +
                        "* No offtopic/use the right text channel for the topic you wish to discuss.",
                "");

        var e3 = create(
                "Voice chat rules",
                "* No voice chat channel hopping.\n" +
                        "* No annoying, loud or high pitch noises.\n" +
                        "* Reduce the amount of background noise, if possible.\n" +
                        "* Moderators reserve the right to disconnect you from a voice channel if your sound quality is poor.\n" +
                        "* Moderators reserve the right to disconnect, mute, deafen, or move members to and from voice channels.\n" +
                        "* Stay on-topic for Valorant",
                "");

        event.getTextChannel().sendMessageEmbeds(e1).queue();
        event.getTextChannel().sendMessageEmbeds(e2).queue();
        event.getTextChannel().sendMessageEmbeds(e3).queue();
    }



    public static MessageEmbed create(String title, String caption, String imageURL) {
        EmbedBuilder eb = new EmbedBuilder();
        // eb.setImage(imageURL);
        eb.setAuthor(title, null, null);
        eb.setDescription(caption);
        return eb.build();
    }

}
