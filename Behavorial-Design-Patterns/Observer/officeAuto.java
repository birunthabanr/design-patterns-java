import java.util.ArrayList;
import java.util.*;

interface listeners {
    public void update(String state);
}

class lightListener implements listeners {
    private String light;

    public lightListener(String light) {
        this.light = light;
    }


    public void update(String state) {
        System.out.println("Light " + state);
    }
}

class lightSensors {
    private List<listeners> observers = new ArrayList<>();
    private String state;
    private final int threshold;

    public lightSensors(int threshold) {
        this.threshold=threshold;
    }

    public void add(listeners listener) {
        observers.add(listener);
    }

    public void remove(listeners listener) {
        observers.remove(listener);
    }

    public void setLightLevel(String lightLevel) {
        this.state = lightLevel;
        notifyListeners();
    }

    public void notifyListeners(){
        for (listeners observer : observers) {
            observer.update(state);
        }
    }

    public int getThreshold() {
        return threshold;
    }
}

public class officeAuto {
    
}
