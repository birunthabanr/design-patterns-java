// The handler interface declares a method for executing a request.
interface ComponentWithContextualHelp {
    void showHelp();
}

// The base class for simple components.
abstract class Component implements ComponentWithContextualHelp {
    protected String tooltipText;
    protected Container container;

    // The component shows a tooltip if there's help text assigned to it. Otherwise it forwards the call to the container, if it exists.
    @Override
    public void showHelp() {
        if (tooltipText != null) {
            // Show tooltip.
            System.out.println("Showing tooltip: " + tooltipText);
        } else if (container != null) {
            container.showHelp();
        }
    }
}

// Containers can contain both simple components and other containers as children. The chain relationships are established here. The class inherits showHelp behavior from its parent.
abstract class Container extends Component {
    protected java.util.List<Component> children = new java.util.ArrayList<>();

    public void add(Component child) {
        children.add(child);
        child.container = this;
    }
}

// Primitive components may be fine with default help implementation...
class Button extends Component {
    public Button(String tooltipText) {
        this.tooltipText = tooltipText;
    }
}

// But complex components may override the default implementation. If the help text can't be provided in a new way, the component can always call the base implementation (see Component class).
class Panel extends Container {
    private String modalHelpText;

    public Panel(String modalHelpText) {
        this.modalHelpText = modalHelpText;
    }

    @Override
    public void showHelp() {
        if (modalHelpText != null) {
            // Show a modal window with the help text.
            System.out.println("Showing modal help text: " + modalHelpText);
        } else {
            super.showHelp();
        }
    }
}

// ...same as above...
class Dialog extends Container {
    private String wikiPageURL;

    public Dialog(String wikiPageURL) {
        this.wikiPageURL = wikiPageURL;
    }

    @Override
    public void showHelp() {
        if (wikiPageURL != null) {
            // Open the wiki help page.
            System.out.println("Opening wiki page: " + wikiPageURL);
        } else {
            super.showHelp();
        }
    }
}

// Client code.
class Application {
    private Dialog dialog;

    // Every application configures the chain differently.
    public void createUI() {
        dialog = new Dialog("http://example.com/wiki/Budget_Reports");
        Panel panel = new Panel("This panel does...");
        Button ok = new Button("This is an OK button that...");
        Button cancel = new Button("Cancel");

        panel.add(ok);
        panel.add(cancel);
        dialog.add(panel);
    }

    // Imagine what happens here.
    public void onF1KeyPress() {
        // Assume getComponentAtMouseCoords() returns a Component.
        Component component = getComponentAtMouseCoords();
        if (component != null) {
            component.showHelp();
        }
    }

    // Dummy method to simulate getting the component at mouse coordinates.
    private Component getComponentAtMouseCoords() {
        // For demonstration purposes, return a component from the dialog.
        return dialog.children.get(0); // Return the OK button.
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.createUI();
        app.onF1KeyPress(); // Simulate pressing F1 key.
    }
}
