interface Mediator {
    void notify(Component sender, String event);
}


class AuthenticationDialog implements Mediator {
    protected String title;
    protected Checkbox loginOrRegisterChkBx;
    protected Textbox loginUsername, loginPassword;
    protected Textbox registrationUsername, registrationPassword, registrationEmail;
    protected Button okBtn, cancelBtn;

    public AuthenticationDialog() {
        loginOrRegisterChkBx = new Checkbox(this);
        loginUsername = new Textbox(this);
        loginPassword = new Textbox(this);
        registrationUsername = new Textbox(this);
        registrationPassword = new Textbox(this);
        registrationEmail = new Textbox(this);
        okBtn = new Button(this);
        cancelBtn = new Button(this);
    }

    @Override
    public void notify(Component sender, String event) {
        if (sender == loginOrRegisterChkBx && event.equals("check")) {
            if (loginOrRegisterChkBx.isChecked()) {
                title = "Log in";
                // Show login form components.
                System.out.println("Showing login form components.");
                // Hide registration form components.
                System.out.println("Hiding registration form components.");
            } else {
                title = "Register";
                // Show registration form components.
                System.out.println("Showing registration form components.");
                // Hide login form components.
                System.out.println("Hiding login form components.");
            }
        }

        if (sender == okBtn && event.equals("click")) {
            if (loginOrRegisterChkBx.isChecked()) {
                // Try to find a user using login credentials.
                boolean found = authenticate(loginUsername.getText(), loginPassword.getText());
                if (!found) {
                    // Show an error message above the login field.
                    System.out.println("Error: User not found.");
                }
            } else {
                // Create a user account using data from the registration fields.
                createUser(registrationUsername.getText(), registrationPassword.getText(), registrationEmail.getText());
                // Log that user in.
                System.out.println("User registered and logged in.");
            }
        }
    }

    private boolean authenticate(String username, String password) {
        // Dummy authentication logic for demonstration purposes
        return "user".equals(username) && "pass".equals(password);
    }

    private void createUser(String username, String password, String email) {
        // Dummy user creation logic for demonstration purposes
        System.out.println("User created: " + username + ", " + email);
    }
}


class Component {
    protected Mediator dialog;

    public Component(Mediator dialog) {
        this.dialog = dialog;
    }

    public void click() {
        dialog.notify(this, "click");
    }

    public void keypress() {
        dialog.notify(this, "keypress");
    }
}

class Button extends Component {
    public Button(Mediator dialog) {
        super(dialog);
    }

    // Additional Button-specific functionality
}

class Textbox extends Component {
    private String text;

    public Textbox(Mediator dialog) {
        super(dialog);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Additional Textbox-specific functionality
}

class Checkbox extends Component {
    private boolean checked;

    public Checkbox(Mediator dialog) {
        super(dialog);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        check();
    }

    public void check() {
        dialog.notify(this, "check");
    }

    // Additional Checkbox-specific functionality
}


public class example1 {
    public static void main(String[] args) {
        AuthenticationDialog dialog = new AuthenticationDialog();

        // Simulate user checking the login/register checkbox
        dialog.loginOrRegisterChkBx.setChecked(true);
        dialog.okBtn.click();

        dialog.loginUsername.setText("user");
        dialog.loginPassword.setText("pass");
        dialog.okBtn.click();

        dialog.loginOrRegisterChkBx.setChecked(false);
        dialog.registrationUsername.setText("new_user");
        dialog.registrationPassword.setText("new_pass");
        dialog.registrationEmail.setText("new_user@example.com");
        dialog.okBtn.click();
    }
}
