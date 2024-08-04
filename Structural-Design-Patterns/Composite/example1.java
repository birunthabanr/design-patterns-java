import java.util.*;

// Component Interface
interface ArmyComponent {
    void receiveOrder(String order);
}

// Leaf class representing an individual soldier
class Soldier implements ArmyComponent {
    private String name;

    public Soldier(String name) {
        this.name = name;
    }

    @Override
    public void receiveOrder(String order) {
        System.out.println(name + " received order: " + order);
    }
}

// Composite class representing a squad
class Squad implements ArmyComponent {
    private String name;
    private List<ArmyComponent> soldiers = new ArrayList<>();

    public Squad(String name) {
        this.name = name;
    }

    public void addSoldier(ArmyComponent soldier) {
        soldiers.add(soldier);
    }

    public void removeSoldier(ArmyComponent soldier) {
        soldiers.remove(soldier);
    }

    @Override
    public void receiveOrder(String order) {
        System.out.println(name + " squad received order: " + order);
        for (ArmyComponent soldier : soldiers) {
            soldier.receiveOrder(order);
        }
    }
}

// Composite class representing a platoon
class Platoon implements ArmyComponent {
    private String name;
    private List<ArmyComponent> squads = new ArrayList<>();

    public Platoon(String name) {
        this.name = name;
    }

    public void addSquad(ArmyComponent squad) {
        squads.add(squad);
    }

    public void removeSquad(ArmyComponent squad) {
        squads.remove(squad);
    }

    @Override
    public void receiveOrder(String order) {
        System.out.println(name + " platoon received order: " + order);
        for (ArmyComponent squad : squads) {
            squad.receiveOrder(order);
        }
    }
}

// Composite class representing a brigade
class Brigade implements ArmyComponent {
    private String name;
    private List<ArmyComponent> platoons = new ArrayList<>();

    public Brigade(String name) {
        this.name = name;
    }

    public void addPlatoon(ArmyComponent platoon) {
        platoons.add(platoon);
    }

    public void removePlatoon(ArmyComponent platoon) {
        platoons.remove(platoon);
    }

    @Override
    public void receiveOrder(String order) {
        System.out.println(name + " brigade received order: " + order);
        for (ArmyComponent platoon : platoons) {
            platoon.receiveOrder(order);
        }
    }
}

// Composite class representing a division
class Division implements ArmyComponent {
    private String name;
    private List<ArmyComponent> brigades = new ArrayList<>();

    public Division(String name) {
        this.name = name;
    }

    public void addBrigade(ArmyComponent brigade) {
        brigades.add(brigade);
    }

    public void removeBrigade(ArmyComponent brigade) {
        brigades.remove(brigade);
    }

    @Override
    public void receiveOrder(String order) {
        System.out.println(name + " division received order: " + order);
        for (ArmyComponent brigade : brigades) {
            brigade.receiveOrder(order);
        }
    }
}

// Composite class representing the army
class Army implements ArmyComponent {
    private String name;
    private List<ArmyComponent> divisions = new ArrayList<>();

    public Army(String name) {
        this.name = name;
    }

    public void addDivision(ArmyComponent division) {
        divisions.add(division);
    }

    public void removeDivision(ArmyComponent division) {
        divisions.remove(division);
    }

    @Override
    public void receiveOrder(String order) {
        System.out.println(name + " army received order: " + order);
        for (ArmyComponent division : divisions) {
            division.receiveOrder(order);
        }
    }
}

// Main class to demonstrate the Composite Pattern
public class example1 {
    public static void main(String[] args) {
        // Creating individual soldiers
        ArmyComponent soldier1 = new Soldier("Soldier 1");
        ArmyComponent soldier2 = new Soldier("Soldier 2");
        ArmyComponent soldier3 = new Soldier("Soldier 3");
        ArmyComponent soldier4 = new Soldier("Soldier 4");
        ArmyComponent soldier5 = new Soldier("Soldier 5");
        ArmyComponent soldier6 = new Soldier("Soldier 6");

        // Creating a squad and adding soldiers to it
        Squad squad1 = new Squad("Alpha");
        squad1.addSoldier(soldier1);
        squad1.addSoldier(soldier2);

        Squad squad2 = new Squad("Bravo");
        squad2.addSoldier(soldier3);
        squad2.addSoldier(soldier4);

        // Creating a platoon and adding squads to it
        Platoon platoon1 = new Platoon("First");
        platoon1.addSquad(squad1);
        platoon1.addSquad(squad2);

        Squad squad3 = new Squad("Charlie");
        squad3.addSoldier(soldier5);
        squad3.addSoldier(soldier6);

        // Creating another platoon
        Platoon platoon2 = new Platoon("Second");
        platoon2.addSquad(squad3);

        // Creating a brigade and adding platoons to it
        Brigade brigade1 = new Brigade("Red");
        brigade1.addPlatoon(platoon1);
        brigade1.addPlatoon(platoon2);

        // Creating a division and adding brigades to it
        Division division1 = new Division("North");
        division1.addBrigade(brigade1);

        // Creating an army and adding divisions to it
        Army army = new Army("Western");
        army.addDivision(division1);

        // Sending an order to the army
        System.out.println("\nSending order to the entire army:");
        army.receiveOrder("Advance to the frontline!");

        // Sending an order to a specific division
        System.out.println("\nSending order to the division:");
        division1.receiveOrder("Hold position!");

        // Sending an order to a specific brigade
        System.out.println("\nSending order to the brigade:");
        brigade1.receiveOrder("Prepare for inspection!");

        // Sending an order to a specific platoon
        System.out.println("\nSending order to the platoon:");
        platoon1.receiveOrder("Engage the enemy!");

        // Sending an order to a specific squad
        System.out.println("\nSending order to the squad:");
        squad1.receiveOrder("Retreat immediately!");

        // Sending an order to a specific soldier
        System.out.println("\nSending order to the soldier:");
        soldier1.receiveOrder("Take cover!");
    }
}
