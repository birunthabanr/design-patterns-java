interface Button {
    void onClick();
}


class ElevatedButton implements Button {
    public void onClick() {
        System.out.println("Clicked Elevated Button");
    }
}

class NormalButton implements Button {
    public void onClick() {
        System.out.println("Clicked Normal Button");
    }
}

abstract class ButtonCreator {
    abstract Button createButton();
}

class ElevatedCreator extends ButtonCreator {
    Button createButton() {
        return new ElevatedButton();
    }
}

class NormalCreator extends ButtonCreator {
    Button createButton() {
        return new NormalButton();
    }
}

public class Buttons {
    public static void makeButton(ButtonCreator buttonService) {
        Button button = buttonService.createButton();
        button.onClick();
    }

    public static void main(String[] args) {
        makeButton(new ElevatedCreator());
    }
}