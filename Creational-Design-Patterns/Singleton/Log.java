class Logger {
    private static Logger instance;

    private Logger() {

    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("Log : " + message);
    }
}

public class Log {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log("This is a log message.");
        Logger loggernew = Logger.getInstance();
        loggernew.log("This is anew log message");
    }
}
