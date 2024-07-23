import java.io.*;

public class ReadAFile {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("ex.txt");
            BufferedReader reader1 = new BufferedReader(reader);
            String line = null;
            while ((line = reader1.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
