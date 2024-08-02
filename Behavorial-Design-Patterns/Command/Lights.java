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
    protected OnLights lightCommand;

    public Room() {}

    public void setCommand(OnLights lightCommand) {
        this.lightCommand = lightCommand; 
    }

    public void executeCommand() {
        lightCommand.execute()
    }
}

class Light {

}



public class Lights {
    public static void main(String[] args) {

    }
    
}
