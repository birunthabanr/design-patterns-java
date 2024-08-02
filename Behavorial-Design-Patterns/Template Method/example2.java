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


class CSVParser extends DataParser {

    @Override
    void readData() {
        System.out.println("Reading data from a CSV file.");
    }

    @Override
    void processData() {
        System.out.println("Processing CSV data.");
    }
}

class XMLParser extends DataParser {

    @Override
    void readData() {
        System.out.println("Reading data from an XML file.");
    }

    @Override
    void processData() {
        System.out.println("Processing XML data.");
    }
}
