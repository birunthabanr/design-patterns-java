// This is the main abstraction in Bridge design pattern
abstract class AgroBot {
    // We will let each Agro Bot have 2
    // Sensor-Actuator-Control Modules (SACM)
    protected SACModule sacm1;
    protected SACModule sacm2;

    AgroBot(SACModule sacm1, SACModule sacm2) {
        this.sacm1 = sacm1; this.sacm2 = sacm2;
    }

    abstract public void doWork();

}

// This is the refinement of the abstraction
class WaterBot extends AgroBot {
    public WaterBot(SACModule sacm1, SACModule sacm2) {
        super(sacm1,sacm2);
    }

    public void doWork() {
        System.out.println("Bot is sensing the soil moisture");
        if(sacm1.doWork() < 5) {
            System.out.println("Bot is watering the plant");
                sacm2.doWork();
            }
        }
}

// This is another refinement of the abstraction
class FertilizerBot extends AgroBot {
    public FertilizerBot(SACModule sacm1, SACModule sacm2) {
        super(sacm1,sacm2);
    }

    public void doWork() {
        System.out.println("Bot is sensing the soil Nitrogen");
        if(sacm1.doWork() < 5) {
            System.out.println("Bot is fertilizing the plant");
            sacm2.doWork();
        }
    }
}

//This is the Implementor for Bridge design pattern
interface SACModule {
    abstract public int doWork();
}

// Concrete implementation of Moisture Sensor
class MoistureSensor implements SACModule {
    public int doWork() {
        System.out.println("Sensing the moisture level");
        return 4; // This is a sample value
    }
}

// Concrete implementation of Nitrogen Sensor
class NitrogenSensor implements SACModule {
    public int doWork() {
        System.out.println("Sensing the Nitrogen level");
        return 4; // This is a sample value
    }
}

// Concrete implementation of Solenoid Valve Actuator
class SolenoidValveActuator implements SACModule {
    public int doWork() {
        System.out.println("Opening the valve for 5 seconds");
        return 0;
    }
}

// Concrete implementation of Mororized Door Actuator
class MotorizedDoorActuator implements SACModule {
    public int doWork() {
        System.out.println("Opening the door for 10 seconds");
        return 0;
    }
}

// This is the Smart Green House application
public class SmartGreenHouse {
    public static void main(String [] args) {
        AgroBot bot1 = new WaterBot(new MoistureSensor(),
        new SolenoidValveActuator());
        bot1.doWork();
        AgroBot bot2 = new FertilizerBot(new NitrogenSensor(),
        new MotorizedDoorActuator());
        bot2.doWork();
    }
}