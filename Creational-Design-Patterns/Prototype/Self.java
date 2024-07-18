interface Prototype {
    Prototype clone();
}

class Circle implements Prototype{
    
    private int radius;


    public Circle(int radius) {
        this.radius = radius;
    }

    public Circle(Circle prototype) {
        this.radius = prototype.radius;
    }

    public Prototype clone() {
        return new Circle(this);
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Circle of radius "+ radius + ".";
    }

}


public class Self {

    public static void main(String[] args) {
        // Create original prototypes
        Circle prototype1 = new Circle(5);

        // Clone prototypes
        Circle clone1 = (Circle) prototype1.clone();

        // Display clones
        System.out.println(clone1);
        System.out.println(prototype1);
        
    }
}
