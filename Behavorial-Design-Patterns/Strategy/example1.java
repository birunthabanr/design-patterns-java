interface TransportationStrategy {
    void getToAirport();
}

class Bus implements TransportationStrategy {
    public void getToAirport() {
        System.out.println("go to airport using bus");
    }
}

class Car implements TransportationStrategy {
    public void getToAirport() {
        System.out.println("go to airport using Car");
    }
}


class Cycle implements TransportationStrategy {
    public void getToAirport() {
        System.out.println("go to airport using Cycle");
    }
}

class TransportationStrategyContext {
    private TransportationStrategy strategy;

    public void setStrategy(TransportationStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        this.strategy.getToAirport();
    }
}



public class example1 {
    public static void main(String[] args) {
        TransportationStrategyContext context = new TransportationStrategyContext();

        // Set strategy to Bus and execute
        context.setStrategy(new Bus());
        context.execute();

        context.setStrategy(new Car());
        context.execute();

        context.setStrategy(new Cycle());
        context.execute();
    };
    
}
