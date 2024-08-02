import java.util.List;
import java.util.ArrayList;

// command interface
interface Order {
    void execute();
}

// concrete command
class Meal implements Order {
    private String meal;
    private Chef chef;

    public Meal(String meal, Chef chef) {
        this.meal=meal;
        this.chef=chef;
    }

    public void execute() {
        chef.cook(meal);
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
class Chef {
    public void cook(String meal) {
        System.out.println("Chef: Cooking " + meal);
    }
}


public class example1 {
    public static void main(String[] args) {
        Chef chef = new Chef();
        Waiter waiter = new Waiter();

        Order order1 = new Meal("Pasta", chef);

        waiter.takeOrder(order1);

        waiter.placeOrders();

    }
    
}
