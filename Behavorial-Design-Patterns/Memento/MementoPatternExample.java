import java.util.Stack;

// Memento class to store the state of the text editor
class TextMemento {
    private final String state;

    public TextMemento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

// Originator class: TextEditor
class TextEditor {
    private StringBuilder content = new StringBuilder();

    public void addText(String text) {
        content.append(text);
    }

    public String getContent() {
        return content.toString();
    }

    // Save the current state to a memento
    public TextMemento save() {
        return new TextMemento(content.toString());
    }

    // Restore the state from a memento
    public void undoToLastSave(TextMemento memento) {
        content = new StringBuilder(memento.getState());
    }
}

// Caretaker class
class TextEditorCaretaker {
    private Stack<TextMemento> history = new Stack<>();

    public void save(TextEditor textEditor) {
        history.push(textEditor.save());
    }

    public void undo(TextEditor textEditor) {
        if (!history.isEmpty()) {
            textEditor.undoToLastSave(history.pop());
        } else {
            System.out.println("No states to undo!");
        }
    }
}

// Main class
public class MementoPatternExample {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        TextEditorCaretaker caretaker = new TextEditorCaretaker();

        editor.addText("Hello");
        caretaker.save(editor);

        editor.addText(" World");
        caretaker.save(editor);

        editor.addText("!");
        System.out.println("Current Content: " + editor.getContent());

        caretaker.undo(editor);
        System.out.println("After Undo: " + editor.getContent());

        caretaker.undo(editor);
        System.out.println("After Undo: " + editor.getContent());
    }
}
