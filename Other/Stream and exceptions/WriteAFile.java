import java.io.*;

class WriteAFile {
    public static void main(String[] args){
        try {
            FileWriter writer = new FileWriter("ex.txt");
            BufferedWriter writer1 = new BufferedWriter(writer);
            writer1.write("hello");
            writer1.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

