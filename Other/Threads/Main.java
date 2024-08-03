/**
* CS1040 â Section 3 - Lab Exercise 2
* Team Name: PHANTOM ORION
* Team Members:
* Kavienan J. - 220314M
* Birunthaban R. - 220073V
* Deepthika K.A.E.R. - 220107G
* Dharmakeerthi D.H.N. - 220117L
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


class Console {
/**
* Prints a message to the console with a specified color.
* @param message The message to print.
* @param color The color to print the message in.
*/

public static void print(String message, String color) {
System.out.println(color + message + "\u001B[0m");
}

/**
* Prints a warning message to the console in yellow.
* @param message The warning message to print.
*/
public static void printWarning(String message) {
print(message, "\u001B[33m");
}
/**
* Prints a success message to the console in green.
* @param message The success message to print.
*/
public static void printSuccess(String message) {
print(message, "\u001B[32m");
}
/**
* Prints an error message to the console in red.
* @param message The error message to print.
*/
public static void printError(String message) {
print(message, "\u001B[31m");
}
/**
* Prints an informational message to the console in blue.
* @param message The informational message to print.
*/
public static void printInfo(String message) {
print(message, "\u001B[34m");
}
}




// Custom exception to be thrown when the type of the print job is not supported
class TypeNotSupportedException extends Exception {
        public TypeNotSupportedException() {
            super("Print File Type Not Supported");
        }
}


/*
* PrintJob class represents a print job that can be sent to the printer.
*/
class PrintJob {
    final String content;
    final String type;
    
    public static final PrintJob END_OF_JOBS = new PrintJob("END", "END");
    
    public PrintJob(String content, String type) {
        this.content = content;
        this.type = type;
    }
    
    private boolean checkSupported() {
    // check if the printer supports the type of the print job
        return type.equals("pdf") ||
            type.equals("docx") ||
            type.equals("pptx") ||
            type.equals("png") ||
            type.equals("jpg") ||
            type.equals("txt");
    }
    public String prepareContent() throws TypeNotSupportedException {
    // prepare the content of the print job based on the type
    // if the type is not supported, throw a TypeNotSupportedException
        if (!checkSupported()) throw new TypeNotSupportedException();
        return "Preparing content for " + content + " of type " + type;
    }
    
    public int getPrintDuration() throws TypeNotSupportedException {
    // return the print duration based on the type of the print job
    // if the type is not supported, throw a TypeNotSupportedException
    if (!checkSupported()) throw new TypeNotSupportedException();
    
    switch (type) {
        case "pdf":
            return 1000;
        case "docx":
            return 2000;
        case "pptx":
            return 4000;
        case "png":
            return 2500;
        case "jpg":
            return 3000;
        default:
            return 2000;
        }
    }
}
/*
* TextFile class represents a text file that can be read from the disk.
*/
class TextFile {
    final String content;
    public TextFile(String content) {
        this.content = content;
    }
}


class Computer implements Runnable {
    private final SharedQueue queue;
    private final List<PrintJob> jobs;
    public Computer(SharedQueue queue, List<PrintJob> jobs) {
    this.queue = queue;
    this.jobs = jobs;
    }
    @Override
    public void run() {
        try {
            for (PrintJob job : jobs) {
                try {
                    // Prepare the content of the print job
                    job.prepareContent();
                } catch (TypeNotSupportedException e) {
                    Console.printError(Thread.currentThread().getName() + " - " +
                    e.getMessage());
                    continue; // skip the print job if the type is not supported
                }
                // Enqueue a print job to the queue
                Console.printInfo(Thread.currentThread().getName() + " adding print job - " +
                job.content);
                queue.add(job);
                // random sleep time between 1 and 5 seconds
                // To simulate the users sending print jobs at random times
                Thread.sleep((long) (Math.random() * 4000) + 1000);
            }
        
            queue.add(PrintJob.END_OF_JOBS); // add end signal after all jobs are added
        
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


class WebInterface implements Runnable {
    private final SharedQueue queue;
    public WebInterface(SharedQueue queue) {
        this.queue = queue;
    }
    public static TextFile readAFile(String filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        // read the file line by line and append it to the StringBuilder
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        return new TextFile(stringBuilder.toString());
    }
    
    @Override
    public void run() {
        // text files text1.txt, text2.txt
        String[] files = {
            "shine_printers/text1.txt",
            "shine_printers/text2.txt"
        };
        try {
            for (String file : files) {
                try {
                    TextFile textFile = readAFile(file);
                    PrintJob job = new PrintJob(textFile.content, "txt");
                    // Prepare the content of the print job
                    job.prepareContent();
                    // Enqueue a print job to the queue
                    Console.printInfo(Thread.currentThread().getName() + " adding web interface job - " + file);
                    queue.add(job);
                    // random sleep time between 1 and 5 seconds
                    // To simulate the users sending print jobs at random times
                    Thread.sleep((long) (Math.random() * 4000) + 1000);
                } catch (TypeNotSupportedException e) {
                    Console.printError(Thread.currentThread().getName() + " - " +
                    e.getMessage());
                    continue; // skip the print job if the type is not supported
                } catch (IOException e) {
                    Console.printError(Thread.currentThread().getName() + " - " +
                    e.getMessage());
                    continue; // skip the print job if the file cannot be read
                }
            }
            queue.add(PrintJob.END_OF_JOBS); // add end signal after all jobs are added
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


class Printer implements Runnable {
    private final SharedQueue queue;
    public Printer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Dequeue a print job from the queue
                PrintJob job = queue.remove();

                if (job == PrintJob.END_OF_JOBS) {
                    break; // break loop if end signal is encountered
                }
                Console.printWarning(Thread.currentThread().getName() + " printing - " + job.content);

                // sleep to simulate the printer printing the job
                // throw a TypeNotSupportedException if the type is not supported
                try {
                    int duration = job.getPrintDuration();
                    Thread.sleep(duration);
                    Console.printSuccess(Thread.currentThread().getName() + " finished - " + job.content);
                } catch (TypeNotSupportedException e) {
                    Console.printError(Thread.currentThread().getName() + " - " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


/*
* SharedQueue class represents a shared queue that can be accessed by multiple threads.
*/
class SharedQueue {
    private final List<PrintJob> queue;
    private final int capacity;
    
    public SharedQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void add(PrintJob job) throws InterruptedException {
        // wait until there is space in the queue
        while (queue.size() == capacity) {
            wait();
        }

        // add the job to the queue
        queue.add(job);
        notifyAll();
    }

    public synchronized PrintJob remove() throws InterruptedException {
        // wait until there is a job in the queue
        while (queue.isEmpty()) {
            wait();
        }
        // remove and return the first job in the queue
        PrintJob job = queue.remove(0);
        notifyAll();
        return job;
    }
}

public class Main {
    public static void main(String[] args) {
    // Create a shared queue with a capacity of 5
    SharedQueue queue = new SharedQueue(5);
    List<PrintJob> jobs1 = Arrays.asList(
        new PrintJob("DOCX from Computer 1", "docx"),
        new PrintJob("JPG from Computer 1", "jpg"),
        new PrintJob("PPTX from Computer 1", "pptx"),
        new PrintJob("DOCX from Computer 1", "docx"),
        new PrintJob("PNG from Computer 1", "png"),
        new PrintJob("MP3 from Computer 1", "mp3"),
        new PrintJob("PPTX from Computer 1", "pptx"),
        new PrintJob("DOCX from Computer 1", "docx")
    );

    Thread computer1 = new Thread(new Computer(queue, jobs1), "Computer 1");
    List<PrintJob> jobs2 = Arrays.asList(
        new PrintJob("PNG from Computer 2", "png"),
        new PrintJob("ZIP from Computer 2", "zip"),
        new PrintJob("PDF from Computer 2", "pdf"),
        new PrintJob("PDF from Computer 2", "pdf"),
        new PrintJob("JPG from Computer 2", "jpg"),
        new PrintJob("DOCX from Computer 2", "docx"),
        new PrintJob("PPTX from Computer 2", "pptx")
    );

    Thread computer2 = new Thread(new Computer(queue, jobs2), "Computer 2");
    List<PrintJob> jobs3 = Arrays.asList(
        new PrintJob("PDF from Computer 3", "pdf"),
        new PrintJob("ZIP from Computer 3", "zip"),
        new PrintJob("PNG from Computer 3", "png"),
        new PrintJob("MP4 from Computer 3", "mp4"),
        new PrintJob("PDF from Computer 3", "pdf"),
        new PrintJob("MP3 from Computer 3", "mp3"),
        new PrintJob("DOCX from Computer 3", "docx"),
        new PrintJob("PDF from Computer 3", "pdf")
    );

    Thread computer3 = new Thread(new Computer(queue, jobs3), "Computer 3");

    Thread printer1 = new Thread(new Printer(queue), "Printer 1");

    Thread printer2 = new Thread(new Printer(queue), "Printer 2");

    Thread webInterface = new Thread(new WebInterface(queue), "Web Interface");

    // Start 3 Computer threads

    computer1.start();

    computer2.start();

    computer3.start();

    // Start 2 Printer threads

    printer1.start();

    printer2.start();

    // Start Web Interface thread

    webInterface.start();

    // print success message when all threads are done

    try {
        computer1.join();
        computer2.join();
        computer3.join();
        printer1.join();
        printer2.join();
        webInterface.join();
        Console.printSuccess("\nAll Print Jobs Completed!");
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        }
    }
}