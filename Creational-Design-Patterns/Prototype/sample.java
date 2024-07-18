// Prototype.java
interface Prototype {
    Prototype clone();
}


// ConcretePrototype1.java
class ConcretePrototype1 implements Prototype {
    private String name;

    public ConcretePrototype1(String name) {
        this.name = name;
    }

    public ConcretePrototype1(ConcretePrototype1 prototype) {
        this.name = prototype.name;
    }

    @Override
    public Prototype clone() {
        return new ConcretePrototype1(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ConcretePrototype1 [name=" + name + "]";
    }
}

// ConcretePrototype2.java
class ConcretePrototype2 implements Prototype {
    private int number;

    public ConcretePrototype2(int number) {
        this.number = number;
    }

    public ConcretePrototype2(ConcretePrototype2 prototype) {
        this.number = prototype.number;
    }

    @Override
    public Prototype clone() {
        return new ConcretePrototype2(this);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "ConcretePrototype2 [number=" + number + "]";
    }
}



// Application.java
public class sample {
    public static void main(String[] args) {
        // Create original prototypes
        ConcretePrototype1 prototype1 = new ConcretePrototype1("Prototype1");
        ConcretePrototype2 prototype2 = new ConcretePrototype2(123);

        // Clone prototypes
        ConcretePrototype1 clone1 = (ConcretePrototype1) prototype1.clone();
        ConcretePrototype2 clone2 = (ConcretePrototype2) prototype2.clone();

        // Display clones
        System.out.println(clone1);
        System.out.println(clone2);
    }
}
