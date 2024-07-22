abstract class State{
    protected Player player;

    public State(Player player) {
        this.player = player;
    }

    abstract void clickLock();
    abstract void clickPlay();
    abstract void clickNext();
    abstract void clickPrevious();
}

class ReadyState extends State {

    public ReadyState(Player player) {
        super(player);
    }

    public void clickLock() {
        
    }

    void clickPlay() {

    }

    void clickNext() {

    }

    void clickPrevious() {

    }
}

class Player {
    private State state;
    private UserInterface UI;
    private int volume;
    private String playlist;
    private String currentSong;

    public Player() {
        this.state = new ReadyState(this);
        this.UI = new UserInterface();
        this.UI.lockButton.onClick(() -> clickLock());
        this.UI.playButton.onClick(() -> clickPlay());
        this.UI.nextButton.onClick(() -> clickNext());
        this.UI.prevButton.onClick(() -> clickPrevious());
    }

    // Method to change the state of the audio player
    public void changeState(State state) {
        this.state = state;
    }

    // UI methods delegate execution to the active state
    public void clickLock() {
        state.clickLock();
    }

    public void clickPlay() {
        state.clickPlay();
    }

    public void clickNext() {
        state.clickNext();
    }

    public void clickPrevious() {
        state.clickPrevious();
    }

    // Service methods for the context
    public void startPlayback() {
        System.out.println("Starting playback...");
    }

    public void stopPlayback() {
        System.out.println("Stopping playback...");
    }

    public void nextSong() {
        System.out.println("Skipping to next song...");
    }

    public void previousSong() {
        System.out.println("Going back to previous song...");
    }

    public void fastForward(int time) {
        System.out.println("Fast forwarding " + time + " seconds...");
    }

    public void rewind(int time) {
        System.out.println("Rewinding " + time + " seconds...");
    }

}

class UserInterface {
    public Button lockButton = new Button();
    public Button playButton = new Button();
    public Button nextButton = new Button();
    public Button prevButton = new Button();
}

class Button {
    private Runnable onClick;

    public void onClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public void press() {
        if (onClick != null) {
            onClick.run();
        }
    }
}

// Main class to simulate the AudioPlayer behavior
public class example2 {
    public static void main(String[] args) {
        Player player = new Player();

        // Simulate button presses
        player.clickLock();
        player.clickPlay();
        player.clickNext();
        player.clickPrevious();
        player.clickLock();
        player.clickPlay();
    }
}
