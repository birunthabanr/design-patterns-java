class Director {
    public void makeManual(CarBuilder builder) {
        builder.id(1000);
        builder.brand("B");
        builder.model("Tesla");
        builder.color("Red");
    }

    public void makeAuto(CarBuilder builder) {
        builder.id(2000);
        builder.brand("A");
        builder.model("Nissan");
        builder.color("Black");
    }
}



class CarBuilder {
    private int id;
    private String brand;
    private String model;
    private String color;

    public void id(int id) {
        this.id = id;
    } 

    public void brand(String brand) {
        this.brand = brand;
    }

    public void model(String model) {
        this.model = model;
    }

    public void color(String color) {
        this.color = color;
    }

    public Car build() {
        return new Car(id, brand, model, color);
    }
}

class Car {
    private final int id;
    private final String brand;
    private final String model;
    private final String color;

    public Car(int id, String brand, String model, String color){
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public String toString() {
        return "Car{" +
               "id=" + id +
               ", brand='" + brand + '\'' +
               ", model='" + model + '\'' +
               ", color='" + color + '\'' +
               '}';
    }
}




public class CarWithDirector {
    public static void main(String[] args) {
        Director director = new Director();

        CarBuilder builder = new CarBuilder();
        director.makeAuto(builder);
        Car car = builder.build();

        System.out.println(car);

        System.out.println(" ");

        director.makeManual(builder);
        Car car2 = builder.build();

        System.out.println(car2);
    }
}
