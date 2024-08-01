public class House {
    private int walls;
    private int doors;
    private int windows;
    private String roof;

    // Private constructor to prevent direct instantiation
    private House(Builder builder) {
        this.walls = builder.walls;
        this.doors = builder.doors;
        this.windows = builder.windows;
        this.roof = builder.roof;
    }

    // Getters for each field
    public int getWalls() {
        return walls;
    }

    public int getDoors() {
        return doors;
    }

    public int getWindows() {
        return windows;
    }

    public String getRoof() {
        return roof;
    }

    // toString() method for easy representation
    @Override
    public String toString() {
        return "House{" +
                "walls=" + walls +
                ", doors=" + doors +
                ", windows=" + windows +
                ", roof='" + roof + '\'' +
                '}';
    }

    // Static Builder class
    public static class Builder {
        private int walls;
        private int doors;
        private int windows;
        private String roof;

        public Builder setWalls(int walls) {
            this.walls = walls;
            return this; // returning the Builder instance to allow method chaining
        }

        public Builder setDoors(int doors) {
            this.doors = doors;
            return this;
        }

        public Builder setWindows(int windows) {
            this.windows = windows;
            return this;
        }

        public Builder setRoof(String roof) {
            this.roof = roof;
            return this;
        }

        // Method to create a House instance
        public House build() {
            return new House(this);
        }
    }

    public static void main(String[] args) {
        // Using the builder pattern to create a House object
        House house = new House.Builder()
                .setWalls(4)
                .setDoors(2)
                .setWindows(8)
                .setRoof("Gabled")
                .build();

        System.out.println(house); // Output: House{walls=4, doors=2, windows=8, roof='Gabled'}
    }
}
