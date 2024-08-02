interface Order {
    public void execute();
}

class OnLights implements Order {
    private Light light;

    public OnLights(Light light) {
        this.light = light;
    }

    public void execute() {
        light.switchLights();
    }
}

class Room{
    public Order lightCommand;

    public Room() {}

    public void setCommand(Order lightCommand) {
        this.lightCommand = lightCommand; 
    }

    public void executeCommand() {
        lightCommand.execute();
    }
}

class Light {
    private boolean switchedOn;
    
    public void switchLights() {
        switchedOn = !switchedOn;
    }
}



public class Lights {
    public static void main(String[] args) {

        Light light = new Light();
        Room room = new Room();

        Order lightCommand = new  OnLights(light);
        
        room.setCommand(lightCommand);

        room.executeCommand();

    }
    
}
