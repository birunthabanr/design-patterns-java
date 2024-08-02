import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Memento class to store the state of the canvas
class CanvasMemento {
    private final List<String> shapes;

    public CanvasMemento(List<String> shapes) {
        this.shapes = new ArrayList<>(shapes);
    }

    public List<String> getShapes() {
        return shapes;
    }
}

// Originator class: Canvas
class Canvas {
    private List<String> shapes = new ArrayList<>();

    public void addShape(String shape) {
        shapes.add(shape);
    }

    public List<String> getShapes() {
        return shapes;
    }

    public void setShapes(List<String> shapes) {
        this.shapes = shapes;
    }

    // Save the current state to a memento
    public CanvasMemento save() {
        return new CanvasMemento(shapes);
    }

    // Restore the state from a memento
    public void undo(CanvasMemento memento) {
        shapes = memento.getShapes();
    }
}

// Caretaker class
class CanvasCaretaker {
    private Stack<CanvasMemento> history = new Stack<>();
    private Stack<CanvasMemento> redoStack = new Stack<>();

    public void save(Canvas canvas) {
        history.push(canvas.save());
    }

    public void undo(Canvas canvas) {
        if (!history.isEmpty()) {
            redoStack.push(canvas.save());
            canvas.undo(history.pop());
        } else {
            System.out.println("No actions to undo!");
        }
    }

    public void redo(Canvas canvas) {
        if (!redoStack.isEmpty()) {
            history.push(canvas.save());
            canvas.undo(redoStack.pop());
        } else {
            System.out.println("No actions to redo!");
        }
    }
}

// Main class
public class DrawingApplicationExample {
    public static void main(String[] args) {
        Canvas canvas = new Canvas();
        CanvasCaretaker caretaker = new CanvasCaretaker();

        canvas.addShape("Circle");
        caretaker.save(canvas);

        canvas.addShape("Square");
        caretaker.save(canvas);

        canvas.addShape("Triangle");
        System.out.println("Current Shapes: " + canvas.getShapes());

        caretaker.undo(canvas);
        System.out.println("After Undo: " + canvas.getShapes());

        caretaker.redo(canvas);
        System.out.println("After Redo: " + canvas.getShapes());

        caretaker.undo(canvas);
        caretaker.undo(canvas);
        System.out.println("After Undo: " + canvas.getShapes());
    }
}
