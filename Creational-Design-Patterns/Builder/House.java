class House {
    private int walls;
    private int doors;
    private int windows;
    private String roof;

    // Getters and toString() method

    public static class Builder {
        private int walls;
        private int doors;
        private int windows;
        private String roof;

        public Builder setWalls(int walls) {
            this.walls = walls;
            return this;
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

        public House build() {
            return new House(this);
        }
    }

    private House(Builder builder) {
        this.walls = builder.walls;
        this.doors = builder.doors;
        this.windows = builder.windows;
        this.roof = builder.roof;
    }
}
