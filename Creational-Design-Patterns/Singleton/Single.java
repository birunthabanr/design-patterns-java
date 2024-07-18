class Singleton {
    // Private static instance of the same class that is the only instance of the class
    private static Singleton instance;

    // Private constructor to prevent instantiation from other classes
    private Singleton() {
        // Initialization code here
    }

    // Public method to provide access to the instance
    public static Singleton getInstance() {
        if (instance == null) {
            // Synchronized block to remove overhead
            synchronized (Singleton.class) {
                if (instance == null) {
                    // If instance is null, initialize
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // Other methods of the Singleton class
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

public class Single {
    public static void main(String[] args) {
        // Get the only object available
        Singleton singleton = Singleton.getInstance();

        // Show the message
        singleton.doSomething();
    }
}
