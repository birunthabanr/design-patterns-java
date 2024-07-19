class RoundHole {
    private double radius;

    RoundHole(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    boolean fits(RoundPeg peg) {
        return (this.getRadius() >= peg.getRadius());
    }
}

class RoundPeg {
    private double radius;

    RoundPeg(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

}

class SquarePeg {
    double width;

    SquarePeg(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

}

class SquarePegAdapter extends RoundPeg {
    private SquarePeg peg;

    public SquarePegAdapter(SquarePeg peg){
        super(0);
        this.peg = peg;
    }

    public double getRadius() {
        return (peg.getWidth()*Math.sqrt(2) / 2);
    }
}


public class example1 {
    public static void main(String[] args) {
        RoundHole hole = new RoundHole(5);
        RoundPeg rpeg = new RoundPeg(5);
        hole.fits(rpeg);
        System.out.println(hole.fits(rpeg));

        SquarePeg small_sqpeg = new SquarePeg(5);
        SquarePeg large_sqpeg = new SquarePeg(10);

        // The below two lines will not work as fits only takes in roundpeg values
        // hole.fits(small_sqpeg);
        // hole.fits(large_sqpeg);

        SquarePegAdapter small_sqpeg_adapter = new SquarePegAdapter(small_sqpeg);
        SquarePegAdapter large_sqpeg_adapter = new SquarePegAdapter(large_sqpeg);
        hole.fits(small_sqpeg_adapter);
        hole.fits(large_sqpeg_adapter);
        System.out.println(hole.fits(small_sqpeg_adapter)); // true
        System.out.println(hole.fits(large_sqpeg_adapter));

    }
}