// Component interface representing a person
interface Person {
    void wear();
}

// Concrete Component representing a basic person without any clothing
class BasicPerson implements Person {
    @Override
    public void wear() {
        System.out.println("Basic person with no extra clothing.");
    }
}

// Abstract Decorator class implementing the Person interface
abstract class ClothingDecorator implements Person {
    protected Person person; // Reference to a Person object

    public ClothingDecorator(Person person) {
        this.person = person;
    }

    @Override
    public void wear() {
        person.wear(); // Delegate the call to the wrapped object
    }
}

// Concrete Decorator class for a Sweater
class Sweater extends ClothingDecorator {
    public Sweater(Person person) {
        super(person);
    }

    @Override
    public void wear() {
        super.wear(); // Call the wrapped person's wear method
        System.out.println("Wearing a sweater.");
    }
}

// Concrete Decorator class for a Jacket
class Jacket extends ClothingDecorator {
    public Jacket(Person person) {
        super(person);
    }

    @Override
    public void wear() {
        super.wear(); // Call the wrapped person's wear method
        System.out.println("Wearing a jacket.");
    }
}

// Concrete Decorator class for a Raincoat
class Raincoat extends ClothingDecorator {
    public Raincoat(Person person) {
        super(person);
    }

    @Override
    public void wear() {
        super.wear(); // Call the wrapped person's wear method
        System.out.println("Wearing a raincoat.");
    }
}

// Main class to demonstrate the Decorator Pattern
public class example1 {
    public static void main(String[] args) {
        // Create a basic person
        Person basicPerson = new BasicPerson();

        // Decorate the person with a sweater
        Person personWithSweater = new Sweater(basicPerson);
        System.out.println("Current Outfit:");
        personWithSweater.wear();

        System.out.println();

        // Decorate the person with a sweater and a jacket
        Person personWithSweaterAndJacket = new Jacket(personWithSweater);
        System.out.println("Current Outfit:");
        personWithSweaterAndJacket.wear();

        System.out.println();

        // Decorate the person with a sweater, a jacket, and a raincoat
        Person fullyDressedPerson = new Raincoat(personWithSweaterAndJacket);
        System.out.println("Current Outfit:");
        fullyDressedPerson.wear();

        System.out.println();

        // Re-decorate the person with a raincoat only
        Person personWithRaincoatOnly = new Raincoat(basicPerson);
        System.out.println("Current Outfit:");
        personWithRaincoatOnly.wear();
    }
}
