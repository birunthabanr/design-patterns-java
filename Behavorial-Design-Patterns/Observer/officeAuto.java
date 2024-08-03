interface listeners {
    public void update();
}

class lightListener implements listeners {
    private String light;

    public lightListener(String light) {
        this.light = light;
    }


    public void update(String state) {
        System.out.println("Light " + state);
    }
}


public class officeAuto {
    
}
