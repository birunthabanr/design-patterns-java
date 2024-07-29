class User {
    protected String username;
    protected String password;
    protected String accessLevel;

    public User(String username, String password, String accessLevel) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

        public String getUsername() {
            return username;
        }
    
        public String getPassword() {
            return password;
        }
    
        public String getAccessLevel() {
            return accessLevel;
        
    }
}

abstract class AuthenticationHandler {
    protected AuthenticationHandler nextHandler;

    public void setNextHandler(AuthenticationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handle(User user);

}

class UserExistsHandler extends AuthenticationHandler {
    private java.util.Map<String, User> usersDatabase;

    public UserExistsHandler(java.util.Map<String, User> userDatabase) {
        this.usersDatabase = userDatabase;
    }

    public boolean handle(User user) {
        if (!usersDatabase.containsKey(user.getUsername())) {
            System.out.println("User does not exists.");
            return false;
        }

        if (nextHandler != null) {
            return nextHandler.handle(user);
        }

        return true;
    }
}

class PasswordCheckHandler extends AuthenticationHandler {
    
    public boolean handle(User user) {
        if (!"password123".equals(user.getPassword())) {
            System.out.println("Incorrect password.");
            return false;
        }
        if (nextHandler != null) {
            return nextHandler.handle(user);
        }
        return true;
    }
}

class AccessLevelHandler extends AuthenticationHandler {

    private String requiredAccessLevel;

    public AccessLevelHandler(String requiredAccessLevel) {
        this.requiredAccessLevel = requiredAccessLevel;
    }

    @Override
    public boolean handle(User user) {
        if (!requiredAccessLevel.equals(user.getAccessLevel())) {
            System.out.println("Insufficient access level.");
            return false;
        }
        if (nextHandler != null) {
            return nextHandler.handle(user);
        }
        return true;
    }
}

public class example2 {
    public static void main(String[] args) {
        // Create a dummy users database
        java.util.Map<String, User> usersDatabase = new java.util.HashMap<>();
        usersDatabase.put("john_doe", new User("john_doe", "password123", "admin"));
        usersDatabase.put("jane_smith", new User("jane_smith", "password456", "user"));

        // Create the handlers
        AuthenticationHandler userExistsHandler = new UserExistsHandler(usersDatabase);
        AuthenticationHandler passwordCheckHandler = new PasswordCheckHandler();
        AuthenticationHandler accessLevelHandler = new AccessLevelHandler("admin");

        // Set up the chain
        userExistsHandler.setNextHandler(passwordCheckHandler);
        passwordCheckHandler.setNextHandler(accessLevelHandler);

        // Create a user
        User user = new User("john_doe", "password123", "admin");

        // Start the authentication process
        if (userExistsHandler.handle(user)) {
            System.out.println("User authenticated successfully.");
        } else {
            System.out.println("User authentication failed.");
        }
    }
}