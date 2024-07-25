// USPlug interface (US plug standard)
interface USPlug {
    void plugIntoUSSocket();
}

// Concrete implementation of USPlug
class AmericanPlug implements USPlug {
    @Override
    public void plugIntoUSSocket() {
        System.out.println("American plug connected to US socket.");
    }
}

// EuropeanSocket interface (European socket standard)
interface EuropeanSocket {
    void plugIntoEuropeanSocket();
}

// Concrete implementation of EuropeanSocket
class GermanSocket implements EuropeanSocket {
    @Override
    public void plugIntoEuropeanSocket() {
        System.out.println("Plug connected to European socket.");
    }
}

// Adapter class that adapts USPlug to EuropeanSocket
class PlugAdapter implements EuropeanSocket {
    private USPlug usPlug;

    // Constructor that takes a USPlug object
    public PlugAdapter(USPlug usPlug) {
        this.usPlug = usPlug;
    }

    // Adapts the US plug to fit the European socket
    @Override
    public void plugIntoEuropeanSocket() {
        System.out.println("Adapter converts US plug to European socket.");
        usPlug.plugIntoUSSocket();
    }
}

// Main class to demonstrate the adapter pattern
public class example2 {
    
    public static void main(String[] args) {
        // Creating a US plug
        USPlug americanPlug = new AmericanPlug();

        // Trying to plug a US plug into a European socket without adapter (won't work)
        // This would not compile: ((EuropeanSocket)americanPlug).plugIntoEuropeanSocket();

        // Using an adapter to plug the US plug into a European socket
        EuropeanSocket adapter = new PlugAdapter(americanPlug);
        adapter.plugIntoEuropeanSocket(); // Works with the adapter
    }
}
