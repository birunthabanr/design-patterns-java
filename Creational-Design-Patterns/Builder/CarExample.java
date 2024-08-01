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
}


public class CarExample {
    public static void main(String[] args){
        CarBuilder builder = new CarBuilder();
        builder.id(2000);
        builder.brand("nissan");
        builder.model("2x");
        builder.color("red");
        Car car = builder.build();
        System.out.println(car);
    }
}