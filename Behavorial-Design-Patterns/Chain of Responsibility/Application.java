
interface ComponentWithContextualHelp {
    void showHelp();
}

abstract class Component implements ComponentWithContextualHelp {
    protected String tooltipText;
    protected Container container;

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

abstract class Container extends Component {
    protected java.util.List<Component> children = new java.util.ArrayList<>();

    public void add(Component child) {
        children.add(child);
        child.container = this;
    }
}

class Button extends Component {
    public Button(String tooltipText) {
        this.tooltipText = tooltipText;
    }
}

class Panel extends Container {
    private String modalHelpText;

    public Panel(String modalHelpText) {
        this.modalHelpText = modalHelpText;
    }

    @Override
    public void showHelp() {
        if (modalHelpText != null) {
            System.out.println("Showing modal help text: " + modalHelpText);
        } else {
            super.showHelp();
        }
    }
}

class Dialog extends Container {
    private String wikiPageURL;

    public Dialog(String wikiPageURL) {
        this.wikiPageURL = wikiPageURL;
    }

    @Override
    public void showHelp() {
        if (wikiPageURL != null) {
            System.out.println("Opening wiki page: " + wikiPageURL);
        } else {
            super.showHelp();
        }
    }
}

class Application {
    private Dialog dialog;

    public void createUI() {
        dialog = new Dialog("http://example.com/wiki/Budget_Reports");
        Panel panel = new Panel("This panel does...");
        Button ok = new Button("This is an OK button that...");
        Button cancel = new Button("Cancel");

        panel.add(ok);
        panel.add(cancel);
        dialog.add(panel);
    }

    public void onF1KeyPress() {
        Component component = getComponentAtMouseCoords();
        if (component != null) {
            component.showHelp();
        }
    }

    private Component getComponentAtMouseCoords() {
        return dialog.children.get(0);
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.createUI();
        app.onF1KeyPress();
    }
}
