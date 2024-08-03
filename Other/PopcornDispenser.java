import java.util.LinkedList;
import java.util.Queue;

class OutputBin {
    private final int capacity;
    private final Queue<String> bin;

    public OutputBin(int capacity) {
        this.capacity = capacity;
        this.bin = new LinkedList<>();
    }

    // Method to add a popcorn bag to the bin
    public synchronized void addPopcorn(String popcorn) throws InterruptedException {
        // Wait if the bin is full
        while (bin.size() == capacity) {
            wait();
        }

        bin.add(popcorn);
        System.out.println("Cooker produced: " + popcorn);
        notifyAll();  // Notify the consumer threads
    }

    // Method to remove a popcorn bag from the bin
    public synchronized String removePopcorn() throws InterruptedException {
        // Wait if the bin is empty
        while (bin.isEmpty()) {
            wait();
        }

        String popcorn = bin.poll();
        System.out.println("Robot arm picked: " + popcorn);
        notifyAll();  // Notify the producer threads
        return popcorn;
    }
}

class Cooker extends Thread {
    private final OutputBin outputBin;
    private final int id;
    private int popcornCount = 0;

    public Cooker(OutputBin outputBin, int id) {
        this.outputBin = outputBin;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Produce a popcorn bag
                String popcorn = "Popcorn " + (++popcornCount) + " from Cooker " + id;
                outputBin.addPopcorn(popcorn);

                // Simulate time taken to produce popcorn
                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Cooker " + id + " interrupted.");
        }
    }
}

class RobotArm extends Thread {
    private final OutputBin outputBin;

    public RobotArm(OutputBin outputBin) {
        this.outputBin = outputBin;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Pick a popcorn bag from the bin
                String popcorn = outputBin.removePopcorn();

                // Simulate time taken to move the popcorn to the table
                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Robot arm interrupted.");
        }
    }
}

public class PopcornDispenser {
    public static void main(String[] args) {
        final int BIN_CAPACITY = 3; // Output bin capacity
        OutputBin outputBin = new OutputBin(BIN_CAPACITY);

        // Create and start two cookers
        Cooker cooker1 = new Cooker(outputBin, 1);
        Cooker cooker2 = new Cooker(outputBin, 2);
        cooker1.start();
        cooker2.start();

        // Create and start the robot arm
        RobotArm robotArm = new RobotArm(outputBin);
        robotArm.start();

        // Run the simulation for a fixed duration, e.g., 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt threads to stop the simulation
        cooker1.interrupt();
        cooker2.interrupt();
        robotArm.interrupt();

        System.out.println("Simulation finished.");
    }
}
