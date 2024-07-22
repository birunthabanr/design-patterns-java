// Visitor interface
interface Visitor {
    void visit(ResidentialBuilding residentialBuilding);
    void visit(Bank bank);
    void visit(CoffeeShop coffeeShop);
}

// Concrete Visitor
class InsuranceAgent implements Visitor {
    @Override
    public void visit(ResidentialBuilding residentialBuilding) {
        System.out.println("Selling medical insurance to residents.");
    }

    @Override
    public void visit(Bank bank) {
        System.out.println("Selling theft insurance to the bank.");
    }

    @Override
    public void visit(CoffeeShop coffeeShop) {
        System.out.println("Selling fire and flood insurance to the coffee shop.");
    }
}

// Element interface
interface Building {
    void accept(Visitor visitor);
}

// Concrete Elements
class ResidentialBuilding implements Building {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Bank implements Building {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class CoffeeShop implements Building {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Client code
public class example1 {
    public static void main(String[] args) {
        // Create an insurance agent (visitor)
        Visitor insuranceAgent = new InsuranceAgent();

        // Create different types of buildings (elements)
        Building residentialBuilding = new ResidentialBuilding();
        Building bank = new Bank();
        Building coffeeShop = new CoffeeShop();

        // Let the insurance agent visit each building
        residentialBuilding.accept(insuranceAgent);
        bank.accept(insuranceAgent);
        coffeeShop.accept(insuranceAgent);
    }
}
