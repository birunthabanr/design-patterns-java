import java.lang.Thread;

public class MemoryInconsistency {
    private static int counter = 0;

    public static void main(String[] args) {
        Thread thread1 = new IncrementThread();
        Thread thread2 = new ReadThread();

        thread1.start();
        thread2.start();
    }

    static class IncrementThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter++;
                System.out.println(counter);
                System.out.println(i);                
            
            }
        }
    }

    static class ReadThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (counter % 2 == 1) {
                    System.out.println("Counter is odd: " + counter);
                }
                System.out.println("hi" + i);
            }
        }
    }
}
