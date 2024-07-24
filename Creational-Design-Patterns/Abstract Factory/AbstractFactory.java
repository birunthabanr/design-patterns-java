interface SmartLight {
    void onLight();
}

interface SmartThermostat {
    void onThermo();
}

interface SmartCamera {
    void onCamers();
}

class ZigbeeLight implements SmartLight {
    public void onLight() {
        System.out.println("on zbl light");
    }
}

class ZWaveLight implements SmartLight {
    public void onLight() {
        System.out.println("on zwl light");
    }
}

class ZigbeeThermo implements SmartThermostat {
    public void onThermo() {
        System.out.println("on zbt Thermo");
    }
}

class ZWaveThermo implements SmartThermostat {
    public void onThermo() {
        System.out.println("on zwt Thermo");
    }
}

interface Devices {
    SmartLight createLight();
    SmartThermostat createThermo();
}

class ZigbeeFactory implements Devices {
    public SmartLight createLight() {
        return new ZigbeeLight();
    }

    public SmartThermostat createThermo() {
        return new ZigbeeThermo();
    }
}

class ZWaveFactory implements Devices {
    public SmartLight createLight() {
        return new ZWaveLight();
    }

    public SmartThermostat createThermo() {
        return new ZWaveThermo();
    }
}


public class AbstractFactory {
    public static void main(String[] args) {

        Devices zigbee = new ZigbeeFactory();
        SmartLight zbl = zigbee.createLight();
        SmartThermostat zbt = zigbee.createThermo();

        zbl.onLight();
        zbt.onThermo();

        Devices zwave = new ZWaveFactory();
        SmartLight zwl = zwave.createLight();
        SmartThermostat zwt = zwave.createThermo();

        zwl.onLight();
        zwt.onThermo();
    }
    
}