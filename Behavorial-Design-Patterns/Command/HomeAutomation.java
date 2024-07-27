// Command Interface
interface Command {
    void execute();
    void undo();
}

// Concrete Command: Light On
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

// Concrete Command: Light Off
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}

// Receiver: Light
class Light {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

// Concrete Command: Fan On
class FanOnCommand implements Command {
    private Fan fan;

    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.start();
    }

    @Override
    public void undo() {
        fan.stop();
    }
}

// Concrete Command: Fan Off
class FanOffCommand implements Command {
    private Fan fan;

    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.stop();
    }

    @Override
    public void undo() {
        fan.start();
    }
}

// Receiver: Fan
class Fan {
    public void start() {
        System.out.println("Fan is ON");
    }

    public void stop() {
        System.out.println("Fan is OFF");
    }
}

// Invoker: Remote Control
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }

    public void pressUndo() {
        command.undo();
    }
}

// Client
public class HomeAutomation {
    public static void main(String[] args) {
        // Receivers
        Light livingRoomLight = new Light();
        Fan ceilingFan = new Fan();

        // Commands
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        Command fanOn = new FanOnCommand(ceilingFan);
        Command fanOff = new FanOffCommand(ceilingFan);

        // Invoker
        RemoteControl remote = new RemoteControl();

        // Test Light
        remote.setCommand(lightOn);
        remote.pressButton(); // Output: Light is ON
        remote.pressUndo();   // Output: Light is OFF

        // Test Fan
        remote.setCommand(fanOn);
        remote.pressButton(); // Output: Fan is ON
        remote.pressUndo();   // Output: Fan is OFF
    }
}
