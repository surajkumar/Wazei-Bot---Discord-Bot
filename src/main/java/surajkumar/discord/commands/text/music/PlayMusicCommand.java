package surajkumar.discord.commands.text.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import surajkumar.discord.commands.text.TextCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlayMusicCommand extends TextCommand {
    public static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static final Logger L = Logger.getLogger(PlayMusicCommand.class.getName());
    private static final Map<Long, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();

    @Override
    public void handle(MessageReceivedEvent event, String input) {
        var member = event.getMember();
        if (member != null) {
            var voiceState = member.getVoiceState();
            if(voiceState != null) {
                var channel = voiceState.getChannel();
                if (channel != null) {
                    if(event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
                        if(input.length() > 0) {
                            AudioSourceManagers.registerRemoteSources(playerManager);
                            AudioSourceManagers.registerLocalSource(playerManager);
                            var guild = channel.getGuild();
                            var musicManager = getGuildAudioPlayer(channel.getGuild());
                            var json = post("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=" + input.replaceAll(" ", "%20").trim() + "&type=video&key=AIzaSyBbm1UqXMYs7Iu6Us94BaG8B-Q9gx-fPfc");
                            var jsonObj = new JSONObject(json);
                            var title = jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
                            var videoId = jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
                            var trackUrl = "https://www.youtube.com/watch?v=" + videoId;

                            L.info("[title=" + title + "][videoId=" + videoId + "][url=" + trackUrl + "]");

                            playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

                                @Override
                                public void trackLoaded(AudioTrack track) {
                                    event.getChannel().sendMessage("Playing " + title).queue();
                                    guild.getAudioManager().openAudioConnection(channel);
                                    musicManager.scheduler.play(track);
                                }

                                @Override
                                public void playlistLoaded(AudioPlaylist playlist) {
                                    var firstTrack = playlist.getSelectedTrack();
                                    if (firstTrack == null) {
                                        firstTrack = playlist.getTracks().get(0);
                                    }
                                    event.getChannel().sendMessage("Playing " + title).queue();
                                    guild.getAudioManager().openAudioConnection(channel);
                                    musicManager.scheduler.play(firstTrack);
                                }

                                @Override
                                public void noMatches() {
                                    event.getChannel().sendMessage("Nothing found for " + input).queue();
                                }

                                @Override
                                public void loadFailed(FriendlyException ex) {
                                    event.getChannel().sendMessage("Failed to load track.").queue();
                                    L.severe(ex.getMessage());
                                }
                            });
                        } else {
                            event.getTextChannel().sendMessage("Please provide the song name you wish to play after the command e.g. !play Doja Cat - Streets").queue();
                        }
                    } else {
                        event.getTextChannel().sendMessage("I do not have permissions to access that voice channel.").queue();
                    }
                } else {
                    event.getTextChannel().sendMessage("You must be in a voice channel to play music.").queue();
                }
            }
        }
    }

    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        var guildId = Long.parseLong(guild.getId());
        var musicManager = MUSIC_MANAGERS.get(guildId);
        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            MUSIC_MANAGERS.put(guildId, musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public static String post(String url) {
        var result = new StringBuilder();
        try {
            var url2 = new URL(url);
            var http = (HttpURLConnection) url2.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/text");
            http.setRequestProperty("Content-Length", "10");
            var reader = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));
            int read;
            while ((read = reader.read()) >= 0) {
                result.append((char) read);
            }
            return result.toString();
        } catch (Exception e) {
            L.severe(e.getMessage());
            return result.toString();
        }
    }
}
