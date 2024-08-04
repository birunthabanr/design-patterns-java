// Color interface
interface Color {
    void applyColor();
}

// Red color implementation
class Red implements Color {
    @Override
    public void applyColor() {
        System.out.println("Applying red color");
    }
}

// Blue color implementation
class Blue implements Color {
    @Override
    public void applyColor() {
        System.out.println("Applying blue color");
    }
}

// Shape abstract class
abstract class Shape {
    // Bridge reference to the Color
    protected Color color;

    // Constructor
    public Shape(Color color) {
        this.color = color;
    }

    // Abstract method to draw a shape
    abstract void draw();
}

// Circle class extending Shape
class Circle extends Shape {
    // Constructor
    public Circle(Color color) {
        super(color);
    }

    // Draw implementation for Circle
    @Override
    void draw() {
        System.out.print("Drawing Circle with ");
        color.applyColor();
    }
}

// Square class extending Shape
class Square extends Shape {
    // Constructor
    public Square(Color color) {
        super(color);
    }

    // Draw implementation for Square
    @Override
    void draw() {
        System.out.print("Drawing Square with ");
        color.applyColor();
    }
}

// Client class to demonstrate the Bridge Pattern
public class Shapes {
    public static void main(String[] args) {
        // Creating shapes with different colors
        Shape redCircle = new Circle(new Red());
        Shape blueCircle = new Circle(new Blue());

        Shape redSquare = new Square(new Red());
        Shape blueSquare = new Square(new Blue());

        // Drawing shapes
        redCircle.draw();   // Output: Drawing Circle with Applying red color
        blueCircle.draw();  // Output: Drawing Circle with Applying blue color
        redSquare.draw();   // Output: Drawing Square with Applying red color
        blueSquare.draw();  // Output: Drawing Square with Applying blue color
    }
}
