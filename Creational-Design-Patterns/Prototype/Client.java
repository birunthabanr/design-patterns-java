import java.util.HashMap;
import java.util.Map;

interface Prototype {
    String getColor();
    Prototype clone();
}

class Button implements Prototype {
    private int x;
    private int y;
    private String color;

    public Button(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Button(Button prototype) {
        this.x = prototype.x;
        this.y = prototype.y;
        this.color = prototype.color;
    }
    
    public String getColor() {
        return color;
    }

    public Prototype clone() {
        return new Button(this);
    }

    public String toString() {
        return "Button [x=" + x + ", y=" + y + ", color=" + color + "]";
    }
} 

class PrototypeRegistry {
    private Map<String, Prototype> items = new HashMap<>();

    public void addItem(String id, Prototype p) {
        items.put(id, p);
    }

    public Prototype getById(String id) {
        Prototype prototype = items.get(id);
        return prototype.clone();
    }

    public Prototype getByColor(String color) {
        for (Prototype item : items.values()) {
            if (item.getColor().equals(color)) {
                return item.clone();
            }
        }
        return null;
    }
}

// Client.java
public class Client {
    public static void main(String[] args) {
        PrototypeRegistry registry = new PrototypeRegistry();

        Button button1 = new Button(10, 40, "red");
        registry.addItem("LandingButton", button1);
        System.out.println(button1);

        Button button2 = (Button) registry.getByColor("blue");
        System.out.println(button2);
    }
}