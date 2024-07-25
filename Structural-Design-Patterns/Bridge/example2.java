// Implementor interface for Manufacturing
interface Manufacturing {
    void produce();
}

// Concrete Implementor for Assembling Process
class Assembling implements Manufacturing {
    @Override
    public void produce() {
        System.out.println("Assembling the vehicle components.");
    }
}

// Concrete Implementor for Painting Process
class Painting implements Manufacturing {
    @Override
    public void produce() {
        System.out.println("Painting the vehicle.");
    }
}

// Abstraction for Vehicle
abstract class Vehicle {
    protected Manufacturing manufacturingProcess;

    public Vehicle(Manufacturing manufacturingProcess) {
        this.manufacturingProcess = manufacturingProcess;
    }

    public abstract void manufacture();
}

// Refined Abstraction for Car
class Car extends Vehicle {
    public Car(Manufacturing manufacturingProcess) {
        super(manufacturingProcess);
    }

    @Override
    public void manufacture() {
        System.out.println("Manufacturing Car:");
        manufacturingProcess.produce();
    }
}

// Refined Abstraction for Bike
class Bike extends Vehicle {
    public Bike(Manufacturing manufacturingProcess) {
        super(manufacturingProcess);
    }

    @Override
    public void manufacture() {
        System.out.println("Manufacturing Bike:");
        manufacturingProcess.produce();
    }
}

// Main class to demonstrate the Bridge Pattern
public class example2 {
    public static void main(String[] args) {
        // Create a Car with Assembling process
        Vehicle carWithAssembly = new Car(new Assembling());
        carWithAssembly.manufacture();

        // Create a Bike with Painting process
        Vehicle bikeWithPainting = new Bike(new Painting());
        bikeWithPainting.manufacture();

        // Create a Car with Painting process
        Vehicle carWithPainting = new Car(new Painting());
        carWithPainting.manufacture();

        // Create a Bike with Assembling process
        Vehicle bikeWithAssembly = new Bike(new Assembling());
        bikeWithAssembly.manufacture();
    }
}
