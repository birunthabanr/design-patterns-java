import java.util.ArrayList;
import java.util.List;

interface Subscriber {
    void update(String magazine);
}

class concreteSubscriber implements Subscriber {
    private String name;

    public concreteSubscriber(String name) {
        this.name = name;
    }

    public void update(String magazine) {
        System.out.println(name + " a new magazine " + magazine + "is available");
    } 

    public String getName() {
        return name;
    }
}

interface publisher {
    void subscribe(Subscriber subscriber);
    void unsubscribe(Subscriber subscriber);
    void notifySubscribers(String magazine);
}

class MagazinePublisher implements publisher {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String name;

    public MagazinePublisher (String magazine) {
        this.name = magazine;
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
        System.out.println("subscribed to " + name + "successfully");
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
        System.out.println("unsubscribed successfully");
    }

    public void notifySubscribers(String magazine) {
        for (Subscriber subscriber: subscribers) {
            subscriber.update(magazine);
        }
    }

    public void releaseNewIssue() {
        System.out.println(name + "released new issue!");
        notifySubscribers(name);
    }
}


public class example1 {
    public static void main(String[] args) {
        MagazinePublisher fashionMagazine = new MagazinePublisher("Fashion Magazine");
        MagazinePublisher techMagazine = new MagazinePublisher("Tech Magazine");

        Subscriber alice = new concreteSubscriber("Alice");
        Subscriber bob = new concreteSubscriber("Bob");
        Subscriber charlie = new concreteSubscriber("Charlie");

        fashionMagazine.subscribe(alice);
        fashionMagazine.subscribe(bob);

        techMagazine.subscribe(bob);
        techMagazine.subscribe(charlie);

        fashionMagazine.releaseNewIssue();
        techMagazine.releaseNewIssue();

        fashionMagazine.unsubscribe(bob);
        fashionMagazine.releaseNewIssue();
    }
}