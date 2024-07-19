class RemoteControl {
    protected Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if (device.isEnabled()) {
            device.disable();
        }
        else device.disable();
    }

    public void volumeDown() {
        device.setVolume(device.getVolume() - 10);
    }

    public void volumeUp() {
        device.setVolume(device.getVolume() + 10);
    }

    public void channelUp() {
        device.setChannel(device.getChannel() + 10);
    }

    public void channelDown() {
        device.setChannel(device.getChannel() - 10);
    }
}

class AdvancedRemoteControl extends RemoteControl {

    public AdvancedRemoteControl(Device device) {
        super(device);
    }

    public void mute() {
        device.setVolume(0);
    }
}

interface Device {
    public boolean isEnabled();
    public void enable();
    public void disable();
    public int getVolume();
    public void setVolume(int percent);
    public int getChannel();
    public void setChannel(int channel);
}


class Tv implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;

    public boolean isEnabled() {
        return on;
    }

    public void enable() {
        on = true;
        System.out.println("TV is now ON");
    }

    public void disable() {
        on = false;
        System.out.println("TV is now OFF");
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int percent) {
        if (percent >= 0 && percent <= 100) {
            volume = percent;
        }
        System.out.println("TV volume set to " + volume);
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        if (channel > 0) {
            this.channel = channel;
        }
        System.out.println("TV channel set to " + this.channel);
    }
}

class Radio implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;

    public boolean isEnabled() {
        return on;
    }

    public void enable() {
        on = true;
        System.out.println("Radio is now ON");
    }

    public void disable() {
        on = false;
        System.out.println("Radio is now OFF");
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int percent) {
        if (percent >= 0 && percent <= 100) {
            volume = percent;
        }
        System.out.println("Radio volume set to " + volume);
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        if (channel > 0) {
            this.channel = channel;
        }
        System.out.println("Radio channel set to " + this.channel);
    }
}


public class remote {
    public static void main(String[] args) {
        Device tv = new Tv();
        RemoteControl remote = new RemoteControl(tv);
        remote.togglePower();
        remote.volumeUp();
        remote.channelUp();
        
        Device radio = new Radio();
        AdvancedRemoteControl advancedRemote = new AdvancedRemoteControl(radio);
        advancedRemote.togglePower();
        advancedRemote.mute();
    }
}