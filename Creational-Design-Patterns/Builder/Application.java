// Car.java
class Car {
    private int seats;
    private String engine;
    private boolean tripComputer;
    private boolean gps;

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setTripComputer(boolean tripComputer) {
        this.tripComputer = tripComputer;
    }

    public void setGPS(boolean gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        return "Car [seats=" + seats + ", engine=" + engine + ", tripComputer=" + tripComputer + ", gps=" + gps + "]";
    }
}

// Manual.java
class Manual {
    private String seats;
    private String engine;
    private String tripComputer;
    private String gps;

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setTripComputer(String tripComputer) {
        this.tripComputer = tripComputer;
    }

    public void setGPS(String gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        return "Manual [seats=" + seats + ", engine=" + engine + ", tripComputer=" + tripComputer + ", gps=" + gps + "]";
    }
}

// Builder.java
interface Builder {
    void reset();
    void setSeats(int number);
    void setEngine(String engine);
    void setTripComputer(boolean tripComputer);
    void setGPS(boolean gps);
}

// CarBuilder.java
 class CarBuilder implements Builder {
    private Car car;

    @Override
    public void reset() {
        this.car = new Car();
    }

    @Override
    public void setSeats(int number) {
        car.setSeats(number);
    }

    @Override
    public void setEngine(String engine) {
        this.car.setEngine(engine);
    }

    @Override
    public void setTripComputer(boolean tripComputer) {
        this.car.setTripComputer(tripComputer);
    }

    @Override
    public void setGPS(boolean gps) {
        this.car.setGPS(gps);
    }

    public Car getProduct() {
        Car product = this.car;
        this.reset();
        return product;
    }
}

// CarManualBuilder.java
 class CarManualBuilder implements Builder {
    private Manual manual;

    @Override
    public void reset() {
        this.manual = new Manual();
    }

    @Override
    public void setSeats(int number) {
        this.manual.setSeats("Seats: " + number);
    }

    @Override
    public void setEngine(String engine) {
        this.manual.setEngine("Engine: " + engine);
    }

    @Override
    public void setTripComputer(boolean tripComputer) {
        this.manual.setTripComputer("Trip Computer: " + (tripComputer ? "Enabled" : "Disabled"));
    }

    @Override
    public void setGPS(boolean gps) {
        this.manual.setGPS("GPS: " + (gps ? "Enabled" : "Disabled"));
    }

    public Manual getProduct() {
        Manual product = this.manual;
        this.reset();
        return product;
    }
}

// Director.java
class Director {
    
    public void constructSportsCar(Builder builder) {
        builder.reset();
        builder.setSeats(2);
        builder.setEngine("SportEngine");
        builder.setTripComputer(true);
        builder.setGPS(true);
    }

    public void constructSUV(Builder builder) {
        builder.reset();
        builder.setSeats(5);
        builder.setEngine("SUVEngine");
        builder.setTripComputer(true);
        builder.setGPS(true);
    }
}

// Application.java
public class Application {
    public static void main(String[] args) {
        Director director = new Director();

        CarBuilder carBuilder = new CarBuilder();
        director.constructSportsCar(carBuilder);
        Car car = carBuilder.getProduct();
        System.out.println(car);

        CarManualBuilder manualBuilder = new CarManualBuilder();
        director.constructSportsCar(manualBuilder);
        Manual manual = manualBuilder.getProduct();
        System.out.println(manual);

        director.constructSUV(carBuilder);
        Car car2 = carBuilder.getProduct();
        System.out.println(car2);
    }
}