


// Product interface
interface Vehicle {
    void rent();
}

// Concrete products
class Car implements Vehicle {
    @Override
    public void rent() {
        System.out.println("Renting Car");
    }
}
class Motorcycle implements Vehicle {
    @Override
    public void rent() {
        System.out.println("Renting Motorcycle");
    }
}

class Truck implements Vehicle {
    @Override
    public void rent() {
        System.out.println("Renting Truck");
    }
}

// Client code without using factory method
public class RentalAppWithoutFactory {
    public static void main(String[] args) {
        // Without factory method
        Vehicle car = new Car();
        Vehicle motorcycle = new Motorcycle();
        Vehicle truck = new Truck();

        // Client code uses concrete objects directly
        System.out.println("Without Using Factory Method:");
        car.rent();
        motorcycle.rent();
        truck.rent();
    }
}