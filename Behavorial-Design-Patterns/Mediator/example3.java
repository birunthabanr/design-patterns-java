interface FormMediator {
    void notify(Component sender, String event);
}

class FormController implements FormMediator {
    private TextBox textBox;
    private CheckBox checkBox;
    private Button button;

    public void setTextBox(TextBox textBox) {
        this.textBox = textBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setSubmitButton(Button submitButton) {
        this.button = submitButton;
    }

    public void notify(Component sender, String event) {
        if (sender == checkBox && event.equals("check")) {
            if (checkBox.isChecked()) {
                textBox.setEditable(true);
                System.out.println("TextBox is editable.");
            } else {
                textBox.setEditable(false);
                System.out.println("TextBox is not editable.");
            }
        }
        if (sender == submitButton && event.equals("click")) {
            if (textBox.isEditable() && !textBox.getText().isEmpty()) {
                System.out.println("Form submitted with text: " + textBox.getText());
            } else {
                System.out.println("Form submission failed. TextBox is either not editable or empty.");
            }
        }
    }
}

abstract class Component {
    protected String type;
    protected FormMediator mediator;

    public Component(FormMediator mediator, String type) {
        this.mediator = mediator;
    }

    public abstract void click();
    public abstract void keypress();
}

class Button extends Component {
    public Button(FormMediator mediator, String type) {
        super(mediator);
    }

    public void click(String onClick) {
        Systen
    }

}

class TextBox extends Component {
    
}

class CheckBox extends Component {
    
}

public class example3 {

    public static void main(String[] args) {
        FormMediator formMediator = new FormController();


    }
    
}
