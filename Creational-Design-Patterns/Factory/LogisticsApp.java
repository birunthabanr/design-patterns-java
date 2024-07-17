interface Transport {
    void deliver();
}

class Truck implements Transport {
    public void deliver() {
        System.out.println("Delivered by using Truck");
    }
}

class Boat implements Transport {
    public void deliver() {
        System.out.println("Delivered by using Boat");
    }
}


abstract class Logistics {
    abstract Transport createTransport(); 
}

class RoadLogistics extends Logistics {
    Transport createTransport() {
        return new Truck();
    }
}

class SeaLogistics extends Logistics {
    Transport createTransport() {
        return new Boat();
    }
}

public class LogisticsApp {
    public static void DeliverVia(Logistics ModeLogistics) {
        Transport t = ModeLogistics.createTransport();
        t.deliver();
    }

    public static void main(String[] args) {
        DeliverVia(new RoadLogistics());
        DeliverVia(new SeaLogistics());
    }
    
}
