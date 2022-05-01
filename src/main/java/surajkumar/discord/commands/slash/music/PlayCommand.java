package surajkumar.discord.commands.slash.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import surajkumar.discord.commands.slash.SlashCommand;
import surajkumar.discord.commands.text.music.PlayMusicCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.json.JSONObject;

import java.util.logging.Logger;

public class PlayCommand extends SlashCommand {
    private static final Logger L = Logger.getLogger(PlayCommand.class.getName());

    public PlayCommand() {
        addOption(new SlashCommandOption("song", "The name of the song you want to play.", OptionType.STRING));
    }

    @Override
    public void handle(SlashCommandEvent event) {
        String input = "";
        for(var option : event.getOptions()) {
            if(option.getName().equals("song")) {
                input = option.getAsString();
                break;
            }
        }
        var member = event.getMember();
        if (member != null) {
            var voiceState = member.getVoiceState();
            if(voiceState != null) {
                var channel = voiceState.getChannel();
                if (channel != null) {
                    if(event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
                        if(input.length() > 0) {
                            AudioSourceManagers.registerRemoteSources(PlayMusicCommand.playerManager);
                            AudioSourceManagers.registerLocalSource(PlayMusicCommand.playerManager);
                            var guild = channel.getGuild();
                            var musicManager = PlayMusicCommand.getGuildAudioPlayer(channel.getGuild());
                            var json = PlayMusicCommand.post("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=" + input.replaceAll(" ", "%20").trim() + "&type=video&key=AIzaSyBbm1UqXMYs7Iu6Us94BaG8B-Q9gx-fPfc");
                            var jsonObj = new JSONObject(json);
                            var title = jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
                            var videoId = jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
                            var trackUrl = "https://www.youtube.com/watch?v=" + videoId;

                            L.info("[title=" + title + "][videoId=" + videoId + "][url=" + trackUrl + "]");

                            String finalInput = input;
                            PlayMusicCommand.playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

                                @Override
                                public void trackLoaded(AudioTrack track) {
                                    event.reply("Playing " + title).queue();
                                    guild.getAudioManager().openAudioConnection(channel);
                                    musicManager.scheduler.play(track);
                                }

                                @Override
                                public void playlistLoaded(AudioPlaylist playlist) {
                                    var firstTrack = playlist.getSelectedTrack();
                                    if (firstTrack == null) {
                                        firstTrack = playlist.getTracks().get(0);
                                    }
                                    event.reply("Playing " + title).queue();
                                    guild.getAudioManager().openAudioConnection(channel);
                                    musicManager.scheduler.play(firstTrack);
                                }

                                @Override
                                public void noMatches() {
                                    event.reply("Nothing found for " + finalInput).queue();
                                }

                                @Override
                                public void loadFailed(FriendlyException ex) {
                                    event.reply("Failed to load track.").queue();
                                    L.severe(ex.getMessage());
                                }
                            });
                        } else {
                            event.reply("Please provide the song name you wish to play after the command e.g. !play Doja Cat - Streets").queue();
                        }
                    } else {
                        event.reply("I do not have permissions to access that voice channel.").queue();
                    }
                } else {
                    event.reply("You must be in a voice channel to play music.").queue();
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Plays music/video audio from YouTube in a voice channel";
    }
}
