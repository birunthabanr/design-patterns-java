import java.util.ArrayList;
import java.util.List;

// Memento class to store the state of the game
class GameMemento {
    private final int level;
    private final int score;

    public GameMemento(int level, int score) {
        this.level = level;
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }
}

// Originator class: Game
class Game {
    private int level;
    private int score;

    public void set(int level, int score) {
        this.level = level;
        this.score = score;
    }

    public String getState() {
        return "Level: " + level + ", Score: " + score;
    }

    // Save the current state to a memento
    public GameMemento save() {
        return new GameMemento(level, score);
    }

    // Restore the state from a memento
    public void load(GameMemento memento) {
        this.level = memento.getLevel();
        this.score = memento.getScore();
    }
}

// Caretaker class
class GameCaretaker {
    private List<GameMemento> savedStates = new ArrayList<>();

    public void saveState(Game game) {
        savedStates.add(game.save());
    }

    public void loadState(Game game, int index) {
        if (index < savedStates.size()) {
            game.load(savedStates.get(index));
        } else {
            System.out.println("Invalid index!");
        }
    }
}

// Main class
public class GameSaveLoadExample {
    public static void main(String[] args) {
        Game game = new Game();
        GameCaretaker caretaker = new GameCaretaker();

        game.set(1, 100);
        caretaker.saveState(game);

        game.set(2, 200);
        caretaker.saveState(game);

        game.set(3, 300);
        System.out.println("Current State: " + game.getState());

        caretaker.loadState(game, 1);
        System.out.println("After Load: " + game.getState());

        caretaker.loadState(game, 0);
        System.out.println("After Load: " + game.getState());
    }
}
