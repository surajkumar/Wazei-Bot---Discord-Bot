package surajkumar.discord.commands.text.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * This is a wrapper around AudioPlayer which makes it behave as an AudioSendHandler for JDA. As JDA calls canProvide
 * before every call to provide20MsAudio(), we pull the frame in canProvide() and use the frame we already pulled in
 * provide20MsAudio().
 */
public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;

    /**
     * @param audioPlayer Audio player to wrap.
     */
    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        // returns true if audio was provided
        return audioPlayer.provide(frame);
    }

    /**
     * If {@link #canProvide()} returns true JDA will call this method in an attempt to retrieve audio data from the
     * handler. This method need to provide 20 Milliseconds of audio data as a byte array.
     * <p>
     * Considering this system needs to be low-latency / high-speed, it is recommended that the loading of audio data
     * be done before hand or in parallel and not loaded from disk when this method is called by JDA. Attempting to load
     * all audio data from disk when this method is called will most likely cause issues due to IO blocking this thread.
     * <p>
     * The provided audio data needs to be in the format: 48KHz 16bit stereo signed BigEndian PCM.
     * <br>Defined by: {@link net.dv8tion.jda.core.audio.AudioSendHandler#INPUT_FORMAT AudioSendHandler.INPUT_FORMAT}.
     * <br>If {@link #isOpus()} is set to return true, then it should be in pre-encoded Opus format instead.
     *
     * @return Should return a byte[] containing 20 Milliseconds of audio.
     */
    @Override
    public ByteBuffer provide20MsAudio() {
        // flip to make it a read buffer
        ((Buffer) buffer).flip();
        return buffer;
    }

    /**
     * If this method returns true JDA will treat the audio data provided by {@link #provide20MsAudio()} as a pre-encoded
     * 20 Millisecond packet of Opus audio. This means that JDA <b>WILL NOT</b> attempt to encode the audio as Opus, but
     * will provide it to Discord <b>exactly as it is given</b>.
     *
     * @return If true, JDA will not attempt to encode the provided audio data as Opus.
     *         <br>Default - False.
     */
    @Override
    // https://opus-codec.org
    public boolean isOpus() {
        return true;
    }
}