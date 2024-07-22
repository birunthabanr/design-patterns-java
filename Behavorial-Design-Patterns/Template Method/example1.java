import java.util.ArrayList;
import java.util.List;

// Supporting classes
class Structure {
    void collect() {
        // Implementation for collecting resources from a structure
    }
}

class Position {
    // Implementation for position class
}

class Enemy {
    private Position position;

    Position getPosition() {
        return position;
    }

    // Additional implementation for Enemy class
}

class Map {
    Position getCenter() {
        // Implementation to get the center position of the map
        return new Position();
    }

    // Additional implementation for Map class
}

class Unit {
    // Implementation for unit class
}

// Abstract base class
abstract class GameAI {
    protected List<Structure> builtStructures = new ArrayList<>();
    protected Map map = new Map();
    protected List<Unit> scouts = new ArrayList<>();
    protected List<Unit> warriors = new ArrayList<>();

    // The template method defines the skeleton of an algorithm.
    public final void turn() {
        collectResources();
        buildStructures();
        buildUnits();
        attack();
    }

    // Some of the steps may be implemented right in a base class.
    protected void collectResources() {
        for (Structure s : this.builtStructures) {
            s.collect();
        }
    }

    // And some of them may be defined as abstract.
    protected abstract void buildStructures();
    protected abstract void buildUnits();

    // A class can have several template methods.
    protected void attack() {
        Enemy enemy = closestEnemy();
        if (enemy == null) {
            sendScouts(map.getCenter());
        } else {
            sendWarriors(enemy.getPosition());
        }
    }

    protected abstract void sendScouts(Position position);
    protected abstract void sendWarriors(Position position);

    protected abstract Enemy closestEnemy();
}

// Concrete class for OrcsAI
class OrcsAI extends GameAI {
    @Override
    protected void buildStructures() {
        if (someResourcesAvailable()) {
            // Build farms, then barracks, then stronghold.
            System.out.println("Orcs: Building structures...");
        }
    }

    @Override
    protected void buildUnits() {
        if (plentyOfResourcesAvailable()) {
            if (noScouts()) {
                // Build peon, add it to scouts group.
                scouts.add(new Unit());
                System.out.println("Orcs: Building peon and adding to scouts.");
            } else {
                // Build grunt, add it to warriors group.
                warriors.add(new Unit());
                System.out.println("Orcs: Building grunt and adding to warriors.");
            }
        }
    }

    @Override
    protected void sendScouts(Position position) {
        if (!scouts.isEmpty()) {
            // Send scouts to position.
            System.out.println("Orcs: Sending scouts to position.");
        }
    }

    @Override
    protected void sendWarriors(Position position) {
        if (warriors.size() > 5) {
            // Send warriors to position.
            System.out.println("Orcs: Sending warriors to position.");
        }
    }

    // Additional helper methods
    private boolean someResourcesAvailable() {
        // Implementation to check resources
        return true;
    }

    private boolean plentyOfResourcesAvailable() {
        // Implementation to check resources
        return true;
    }

    private boolean noScouts() {
        // Implementation to check scouts
        return scouts.isEmpty();
    }

    @Override
    protected Enemy closestEnemy() {
        // Implementation to find closest enemy
        return null;
    }
}

// Concrete class for MonstersAI
class MonstersAI extends GameAI {
    @Override
    protected void collectResources() {
        // Monsters don't collect resources.
        System.out.println("Monsters: No resource collection.");
    }

    @Override
    protected void buildStructures() {
        // Monsters don't build structures.
        System.out.println("Monsters: No structure building.");
    }

    @Override
    protected void buildUnits() {
        // Monsters don't build units.
        System.out.println("Monsters: No unit building.");
    }

    @Override
    protected void sendScouts(Position position) {
        // Monsters don't send scouts.
        System.out.println("Monsters: No scouts to send.");
    }

    @Override
    protected void sendWarriors(Position position) {
        // Monsters don't send warriors.
        System.out.println("Monsters: No warriors to send.");
    }

    @Override
    protected Enemy closestEnemy() {
        // Implementation to find closest enemy
        return null;
    }
}

// Main class to run the example
public class example1 {
    public static void main(String[] args) {
        GameAI orcsAI = new OrcsAI();
        orcsAI.turn(); // Execute the turn for OrcsAI

        GameAI monstersAI = new MonstersAI();
        monstersAI.turn(); // Execute the turn for MonstersAI
    }
}
