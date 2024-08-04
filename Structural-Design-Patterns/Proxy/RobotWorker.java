// This is the Subject
interface Robot {
    public void move(double x, double y, double z);
}

// This is the Real Subject
class SixAxisRobot implements Robot {
    private double [] movement;
    private double [] current;
    private double [] previous;

    public SixAxisRobot() {
        movement = new double[6];
        for(int i=0; i<6; i++) movement[i] = 0.0;
        current = new double[3];
        for(int i=0; i<3; i++) current[i] = 0.0;
        previous = new double[3];
        for(int i=0; i<3; i++) previous[i] = 0.0;
    }

    public void set(double x, double y, double z) {
        for(int i=1; i<3; i++) previous[i] = current[i];
        current[0] = x; current[1] = y; current[0] = z;
        // using previous and new current positions
        // calculate and set movement[0]...[5]
        System.out.println("Set target position ["+x+","+y+","+z+"]");
    }
    
    public boolean checkSafety() {
        boolean safe = false;
        // using previous[], current[], and movement[] values, check
        // if the motion plan is safe and within tolerance margins
        System.out.println("Checking safety margins for motion plan");return safe;
    }

    public void move(double x, double y, double z) {
        // using movement[], operate the motors
        System.out.println("Moved to target position at ["+current[0]+","+current[1]+","+current[2]+"]");
    }
}


// This is the Proxy
class SafeRobot implements Robot {
    private SixAxisRobot sarobot;

    public SafeRobot() {
        sarobot = new SixAxisRobot();
    }

    public void move(double x, double y, double z) {
        sarobot.set(x,y,z);
        if (sarobot.checkSafety())
            sarobot.move(x,y,z);
        else
            System.out.println("The motion plan was unsafe");
    }
}

// This is the Client.
// Client use the direct move() method assuming that SafeRobot
// operates in a safe manner. However, it is the proxy that
// call a sequence of methods to ensure safety.
public class RobotWorker {
    public static void main(String [] args) {
        Robot rob = new SafeRobot();
        rob.move(1.2,3.4,5.6);
    }
}