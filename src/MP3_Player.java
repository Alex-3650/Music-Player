import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// JayLayer imports
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3_Player {
    private final File mp3File;
    private Player player;
    private FileInputStream fileInputStream;
    private final long totalLength;
    private long pausePosition = 0;
    private Thread playerThread;
    private boolean isPlaying = false;

    /**
     * Constructor
     * @param file MP3 file to play
     */
    public  MP3_Player(File file) {
        this.mp3File = file;
        this.totalLength = file.length();
        System.out.println("MP3 Player initialized with: " + file.getName());
    }

    /**
     * Start or resume playback
     */
    public void play() {
        if (isPlaying) {
            System.out.println("Already playing");
            return;
        }

        playerThread = new Thread(() -> {
            try {
                fileInputStream = new FileInputStream(mp3File);

                // Skip to pause position if needed
                if (pausePosition > 0) {
                    fileInputStream.skip(pausePosition);
                }

                player = new Player(fileInputStream);
                isPlaying = true;
                player.play();
                isPlaying = false;
            } catch (JavaLayerException | IOException e) {
                System.out.println("Error playing MP3: " + e.getMessage());
                isPlaying = false;
            }
        });

        playerThread.start();
    }

    /**
     * Stop/pause playback
     */
    public void stop() {
        if (player != null && isPlaying) {
            try {
                // Store approximate position for resume
                pausePosition = fileInputStream.getChannel().position();
                player.close();
                isPlaying = false;
            } catch (IOException e) {
                System.out.println("Error stopping player: " + e.getMessage());
            }
        } else {
            System.out.println("Not currently playing");
        }
    }

    /**
     * Reset to the beginning of the track
     */
    public void reset() {
        stop();
        pausePosition = 0;
    }

    /**
     * Jump to approximate position in seconds
     * @param seconds Time in seconds to jump to
     */
    public void jumpTo(double seconds) {
        // This is an approximation as JLayer doesn't support precise seeking
        stop();

        try {
            // Rough approximation - assumes constant bitrate
            // 128kbps = 16KB per second
            long estimatedPosition = (long)(seconds * 16 * 1024);
            pausePosition = Math.min(estimatedPosition, totalLength);
        } catch (Exception e) {
            System.out.println("Error jumping: " + e.getMessage());
        }
    }


    public void close() {
        if (player != null) {
            player.close();
            isPlaying = false;
        }

        try {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

}
