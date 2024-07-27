import java.util.List;
import java.util.ArrayList;

// command interface
interface Order {
    void execute();
}

// concrete command
class Meal implements Order {
    private String meal;
    private Type type;

    public Meal(String meal, Type type) {
        this.meal=meal;
        this.type=type;
    }

    public void execute() {
        type.cook(meal);
    }

    public String getMeal() {
        return meal;
    }
}

// invoker class
class Waiter {
    private List<Order> orders = new ArrayList<>();

    public void takeOrder(Order order) {
        orders.add(order);
        // System.out.println("Waiter: Taking order for " + ((Meal) order).getMeal());
    }

    public void placeOrders() {
        for (Order order : orders) {
            order.execute();
        }

        orders.clear();

    }
}

// Receiver class
class Type {
    public void cook(String meal) {
        System.out.println("Chef: Cooking " + meal);
    }
}


public class example1 {
    public static void main(String[] args) {
        Type type = new Type();
        Waiter waiter = new Waiter();

        Order order1 = new Meal("Pasta", type);

        waiter.takeOrder(order1);

        waiter.placeOrders();

    }
    
}
