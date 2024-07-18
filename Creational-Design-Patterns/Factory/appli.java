interface Shape {
    void draw();
}

class Circle implements Shape {

    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle implements Shape {

    public void draw() {
        System.out.println("Drawing Rectangle");
    }
}

abstract class shapeFactory {
    abstract Shape createShape();
}

class CircleFactory extends shapeFactory {
    public Circle createShape() {
        return new Circle();
    }
}

class RectangleFactory extends shapeFactory {
    public Rectangle createShape() {
        return new Rectangle();
    }
}

public class appli {

    public static void shapeCreator(shapeFactory Factory) {
        Shape s = Factory.createShape();
        s.draw();
    }

    public static void main(String[] args) {
        shapeCreator(new CircleFactory());
        shapeCreator(new RectangleFactory());
    }

}