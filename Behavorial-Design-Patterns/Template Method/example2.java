abstract class DataParser {
    
    // Template method
    public final void parseData() {
        readData();
        processData();
        writeData();
    }

    // Abstract methods to be implemented by subclasses
    abstract void readData();
    abstract void processData();

    // Concrete method
    void writeData() {
        System.out.println("Writing data to output.");
    }
}
