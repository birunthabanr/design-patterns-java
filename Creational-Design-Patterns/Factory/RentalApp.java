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

// Creator class
abstract class VehicleRentalService {
    abstract Vehicle createVehicle();
}

// Concrete creators
class CarRentalService extends VehicleRentalService {
    @Override
    Vehicle createVehicle() {
        return new Car();
    }
}
class MotorcycleRentalService extends VehicleRentalService {
    @Override
    Vehicle createVehicle() {
        return new Motorcycle();
    }
}
class TruckRentalService extends VehicleRentalService {
    @Override
    Vehicle createVehicle() {
        return new Truck();
    }
}

// Client code
public class RentalApp {
    public static void rentVehicle(VehicleRentalService rentalService) {
        Vehicle vehicle = rentalService.createVehicle();
        vehicle.rent();
    }

    public static void main(String[] args) {
        System.out.println("Using Factory Method Pattern:");
        rentVehicle(new CarRentalService());
        rentVehicle(new MotorcycleRentalService());
        rentVehicle(new TruckRentalService());
    }
}