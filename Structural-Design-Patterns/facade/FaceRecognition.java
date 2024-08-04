import java.io.*;

abstract class Pixel {
    public abstract int getValue(int idx);
}

class ColorPixel extends Pixel {
    private int [] colors;

    ColorPixel(int r, int g, int b) {
        colors = new int[3];
        this.colors[0] = r; this.colors[1] = g; this.colors[2] = b;
    }

    public int getValue(int idx) { // idx 0,1,2 return R,G,B
        if((idx >= 0) && (idx <3))
        return colors[idx];
        else
        return -1;
    }
}

class GrayScalePixel extends Pixel {
    private int graylevel;

    GrayScalePixel(int g) {
        graylevel = g;
    }

    // An index of 0 will return the GRAY value

    public int getValue(int idx) {
        if(idx == 0)
        return graylevel;
        else
        return -1;
    }
}

class PixelBuffer {
    private Pixel [][] buffer;
    private int length;
    private int height;

    PixelBuffer(int length, int height) {
        this.length = length; this.height = height;
        buffer = new Pixel[length][height];
    }

    public void set(int i, int j, Pixel p) {
        buffer[i][j] = p;
    }

    public Pixel get(int i, int j) {
        return buffer[i][j];
    }
}

class Image {
    private PixelBuffer pixbuf;
    private int length;
    private int height;
    private int type; // 0 - mono, 1 - color

    Image() {
        length = 0; height = 0; type = 0; pixbuf = null;
    }

    public void load(String filename)
    throws FileNotFoundException, IOException {
        System.out.println("Loading image from file: "+filename);
        File inf = new File(filename);
        FileReader infr = new FileReader(inf);
        BufferedReader inbr = new BufferedReader(infr);
        String s;
        s = inbr.readLine(); type = Integer.parseInt(s);
        s = inbr.readLine(); length = Integer.parseInt(s);
        s = inbr.readLine(); height = Integer.parseInt(s);
        pixbuf = new PixelBuffer(length,height);
        int gray = 0, r = 0, g = 0, b = 0;

        for(int i=0; i<length; i++) {
            for(int j=0; j<height; j++) {
                if(type == 0) {
                    s = inbr.readLine(); gray = Integer.parseInt(s);
                    pixbuf.set(i,j,new GrayScalePixel(gray));
                }
                else if(type == 1) {
                    s = inbr.readLine(); r = Integer.parseInt(s);
                    s = inbr.readLine(); g = Integer.parseInt(s);
                    s = inbr.readLine(); b = Integer.parseInt(s);
                    pixbuf.set(i,j,new ColorPixel(r,g,b));
                }
                else
                System.out.println("Invalid image type");
            }
        }
        inbr.close();
    }
    public void show() {
        GrayScalePixel gp;
        ColorPixel cp;
        for(int i=0; i<length; i++) {
            for(int j=0; j<height; j++) {
                if(type == 0) {
                    gp = (GrayScalePixel)pixbuf.get(i,j);
                    System.out.print(gp.getValue(0));
                }
                else {
                    cp = (ColorPixel)pixbuf.get(i,j);
                    System.out.print((cp.getValue(0)+cp.getValue(1)
                    +cp.getValue(2))/3);
                }
            }
            System.out.println();
        }
    }
}

public class FaceRecognition {
    public static void main(String [] args) {
        Image img = new Image();
        try {
            img.load("image-data.file");
        }
        catch(FileNotFoundException e) {
        }
        catch(IOException e) {
        }
        img.show();
    }
}