interface Chair {
    void hasLegs();
    void sitOn();
}

interface Table {
    void hasLegs();
    void sitOn();
}

class victorianChair implements Chair {
    public void hasLegs() {
        System.out.println("Has 4 legs");
    }

    public void sitOn() {
        System.out.println("Yes");
    }
}

class modernChair implements Chair {
    public void hasLegs() {
        System.out.println("Has 4 legs");
    }

    public void sitOn() {
        System.out.println("Yes");
    }
}

class victorianTable implements Table {
    public void hasLegs() {
        System.out.println("Has 4 legs");
    }

    public void sitOn() {
        System.out.println("No");
    }
}

class modernTable implements Table {
    public void hasLegs() {
        System.out.println("Has 4 legs");
    }

    public void sitOn() {
        System.out.println("No");
    }
}

interface furnitureFactory {
    Chair produceChair();
    Table produceTable();
}

class victorianFactory implements furnitureFactory {
    public Chair produceChair() {
        return new victorianChair();
    }

    public Table produceTable() {
        return new victorianTable();
    }
}

class modernFactory implements furnitureFactory {
    public Chair produceChair() {
        return new modernChair();
    }

    public Table produceTable() {
        return new modernTable();
    }
}

public class Furniture {

    public static void produceFurniture(furnitureFactory factory) {
        Chair chair = factory.produceChair();
        Table table = factory.produceTable();
        chair.hasLegs();
        table.sitOn();
    }

    public static void main(String[] args) {
        produceFurniture(new victorianFactory());
        
    }
    
}
