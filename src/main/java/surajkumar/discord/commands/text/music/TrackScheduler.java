package surajkumar.discord.commands.text.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public int size() {
        return queue.size();
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public void play(AudioTrack track) {
        if (!player.startTrack(track, false)) {
            queue.offer(track);
        }
    }

    public void stop() {
        AudioTrack track = player.getPlayingTrack();
        if(track != null) {
            track.stop();
            player.stopTrack();
            queue.remove(track);
        }
    }

    public void fastforward(int amount) {
        AudioTrack track = player.getPlayingTrack();
        if(track != null) {
            long current = track.getPosition();
            track.setPosition(current + amount);
        }
    }

    public void rewind(int amount) {
        AudioTrack track = player.getPlayingTrack();
        if(track != null) {
            long current = track.getPosition();
            long amt = current - amount;
            if(amt < 0) {
                amt = 0;
            }
            track.setPosition(amt);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public AudioTrack nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
        return player.getPlayingTrack();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
}