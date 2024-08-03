import java.util.*;

interface listener {
    public void update();
}

class MsgListener implements listener {
    private final String number;
    
    public MsgListener(String number) {
        this.number = number;
    }

    public void update() {
        System.out.println(number + " New item available");
    }
}

class Store {
    private final NotificationService notification;

    public Store() {
        notification = new NotificationService();
    }

    public void newItem() {
        notification.notifi();
    }

    public NotificationService get() {
        return notification;
    }
}

class NotificationService {
    private final List<MsgListener> customers;

    public NotificationService() {
        customers = new ArrayList<>();
    }

    public void subscribe(MsgListener listener) {
        customers.add(listener);
    }

    public void unsubscribe(MsgListener listener) {
        customers.remove(listener);
    }

    public void notifi() {
        for (MsgListener customer : customers) {
            customer.update();
        }
    }
}


public class StoreSystem {

    public static void main(String[] args) {
        Store store = new Store();
        MsgListener l1 = new MsgListener("123");

        NotificationService s1 = store.get();
        s1.subscribe(l1);

        store.newItem();
    }
    
}
