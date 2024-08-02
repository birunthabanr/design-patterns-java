import java.util.*;

class TextArea {
    private String text;

    public void set(String text) {
        this.text = text;
    }

    public Memento takeSnapshot() {
        return new Memento(this.text);
    }

    public void restore(Memento memento) {
        this.text = memento.getSavedText();
    }

    public static class Memento {
        private final String text;
    
        private Memento(String textSaved) {
            text = textSaved;
        }
    
        private String getSavedText() {
            return text;
        }
    }
}

class Editor {
    private Deque<Memento> stateHistory;
    private TextArea textArea;

    public Editor() {
        stateHistory = new LinkedList<>();
        textArea = new TextArea();
    }

    public void write(String text) {
        textArea.set(text);
        stateHistory.add(textArea.takeSnapshot());
    }

    public void undo() {
        textArea.restore(stateHistory.pop());
    }
}




public class textEditor {
    
}
