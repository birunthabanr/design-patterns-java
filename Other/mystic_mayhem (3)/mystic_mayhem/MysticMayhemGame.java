package mystic_mayhem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

class Console {
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void print(String message, String color) {
        System.out.println(color + message + "\u001B[0m");
    }

    public static void printWarning(String message) {
        print(message, "\u001B[33m");
    }

    public static void printSuccess(String message) {
        print(message, "\u001B[32m");
    }

    public static void printError(String message) {
        print(message, "\u001B[31m");
    }

    public static void printInfo(String message) {
        print(message, "\u001B[34m");
    }
}

enum GameLocation {
    HILLCREST("Hillcrest"),
    MARSHLAND("Marshland"),
    DESERT("Desert"),
    ARCANE("Arcane");

    final String name;

    GameLocation(String name) {
        this.name = name;
    }

    static GameLocation getLocation(String location) {
        for (GameLocation loc : GameLocation.values()) {
            if (loc.name.equalsIgnoreCase(location)) {
                return loc;
            }
        }
        return null;
    }
}

abstract class GameEquipment {
    final int id;
    final String name;
    final int attack;
    final int defense;
    final int health;
    final int speed;
    final int price;

    public GameEquipment(int id, String name, int attack, int defense, int health, int speed, int price) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.speed = speed;
        this.price = price;
    }
}

class Armour extends GameEquipment {
    public Armour(int id, String name, int attack, int defense, int health, int speed, int price) {
        super(id, name, attack, defense, health, speed, price);
    }
}

class Artefact extends GameEquipment {
    public Artefact(int id, String name, int attack, int defense, int health, int speed, int price) {
        super(id, name, attack, defense, health, speed, price);
    }
}

class EquipmentFactory {
    public static List<Armour> getArmours() {
        return List.of(
            new Armour(1, "Chainmail", 0, 1, 0, -1, 70),
            new Armour(2, "Regalia", 0, 1, 0, 0, 105),
            new Armour(3, "Fleece", 0, 2, 1, -1, 150)
        );
    }

    public static List<Artefact> getArtefacts() {
        return List.of(
            new Artefact(4, "Excalibur", 2, 0, 0, 0, 150),
            new Artefact(5, "Amulet", 1, -1, 1, 1, 200),
            new Artefact(6, "Crystal", 2, 1, -1, -1, 210)
        );
    }

    public static GameEquipment getEquipment(int id) {
        List<List<? extends GameEquipment>> equipment = List.of(
            getArmours(),
            getArtefacts()
        );
        for (List<? extends GameEquipment> list : equipment) {
            for (GameEquipment item : list) {
                if (item.id == id) {
                    return item;
                }
            }
        }
        return null;
    }
}

abstract class GameCharacterCategory {
    final String name;
    GameCharacterCategory(String name) {
        this.name = name;
    }
    
    double attackBoost(GameLocation location, int attack) {
        return 0;
    }

    double defenseBoost(GameLocation location, int defense) {
        return 0;
    }

    double healthBoost(GameLocation location, int health) {
        return 0;
    }

    double speedBoost(GameLocation location, int speed) {
        return 0;
    }

    boolean hasExtraAttackTurn(GameLocation location) {
        return false;
    }

    double extraTurnAttack(GameLocation location, double attack) {
        return 0;
    }

    boolean canHealAfterTurn(GameLocation location) {
        return false;
    }

    double healDamage(GameLocation location, double health, double damage) {
        return 0;
    }
}

class Highlander extends GameCharacterCategory {
    Highlander() { super("Highlander"); }

    @Override
    double attackBoost(GameLocation location, int attack) {
        if (location == GameLocation.HILLCREST) {
            return 1;
        }
        return 0;
    }

    @Override
    double defenseBoost(GameLocation location, int defense) {
        if (location == GameLocation.HILLCREST) {
            return 1;
        }
        if (location == GameLocation.ARCANE) {
            return -1;
        }
        return 0;
    }

    @Override
    double speedBoost(GameLocation location, int speed) {
        if (location == GameLocation.ARCANE) {
            return -1;
        }
        return 0;
    }

    @Override
    boolean hasExtraAttackTurn(GameLocation location) {
        return location == GameLocation.HILLCREST;
    }

    @Override
    double extraTurnAttack(GameLocation location, double attack) {
        if (location == GameLocation.HILLCREST) {
            return Math.round(attack * 0.2);
        }
        return 0;
    }
}

class Marshlander extends GameCharacterCategory {
    Marshlander() { super("Marshlander"); }

    @Override
    double speedBoost(GameLocation location, int speed) {
        if (location == GameLocation.ARCANE) {
            return -1;
        }
        if (location == GameLocation.HILLCREST) {
            return -1;
        }
        return 0;
    }

    @Override
    double defenseBoost(GameLocation location, int defense) {
        if (location == GameLocation.ARCANE) {
            return -1;
        }
        if (location == GameLocation.MARSHLAND) {
            return 2;
        }
        return 0;
    }

    @Override
    double healthBoost(GameLocation location, int health) {
        if (location == GameLocation.DESERT) {
            return -1;
        }
        return 0;
    }
}

class Sunchild extends GameCharacterCategory {
    Sunchild() { super("Sunchild"); }

    @Override
    double attackBoost(GameLocation location, int attack) {
        if (location == GameLocation.DESERT) {
            return 1;
        }
        if (location == GameLocation.MARSHLAND) {
            return -1;
        }
        return 0;
    }

    @Override
    double speedBoost(GameLocation location, int speed) {
        if (location == GameLocation.HILLCREST) {
            return -1;
        }
        return 0;
    }
}

class Mystic extends GameCharacterCategory {
    Mystic() { super("Mystic"); }

    @Override
    double speedBoost(GameLocation location, int speed) {
        if (location == GameLocation.MARSHLAND) {
            return -1;
        }
        return 0;
    }

    @Override
    double attackBoost(GameLocation location, int attack) {
        if (location == GameLocation.ARCANE) {
            return 2;
        }
        return 0;
    }

    @Override
    boolean canHealAfterTurn(GameLocation location) {
        return location == GameLocation.ARCANE;
    }

    @Override
    double healDamage(GameLocation location, double health, double damage) {
        if (location == GameLocation.ARCANE) {
            double deductDamage = (health - damage) * 0.1;
            return deductDamage;
        }
        return health;
    }
}

abstract class GameCharacter<T extends GameCharacterCategory> {
    final int id;
    final String name;
    private final int attack;
    private final int defense;
    private final int health;
    private final int speed;
    private final int price;
    public final T category;
    private Armour armour;
    private Artefact artefact;
    private double damage;
    private GameLocation location;

    public GameCharacter(int id, String name, int attack, int defense, int health, int speed, int price, T category) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.speed = speed;
        this.price = price;
        this.damage = 0;
        this.category = category;
    }

    public double getHealth() {
        double boost = location != null ? category.healthBoost(location, health) : 0;
        return health + 
            (armour != null ? armour.health : 0) +
            (artefact != null ? artefact.health : 0) +
            boost;
    }

    public double getAttack() {
        double boost = location != null ? category.attackBoost(location, attack) : 0;
        return attack + 
            (armour != null ? armour.attack : 0) +
            (artefact != null ? artefact.attack : 0) +
            boost;
    }

    public double getDefense() {
        double boost = location != null ? category.defenseBoost(location, defense) : 0;
        return defense + 
            (armour != null ? armour.defense : 0) +
            (artefact != null ? artefact.defense : 0) +
            boost;
    }

    public double getSpeed() {
        double boost = location != null ? category.speedBoost(location, speed) : 0;
        return speed + 
            (armour != null ? armour.defense : 0) +
            (artefact != null ? artefact.defense : 0) +
            boost;
    }

    public boolean hasExtraAttackTurn() {
        return location != null && category.hasExtraAttackTurn(location);
    }

    public double getExtraTurnAttack() {
        return location != null ? category.extraTurnAttack(location, getAttack()) : 0;
    }

    public boolean canHealAfterTurn() {
        return location != null && category.canHealAfterTurn(location);
    }

    public double getHealDamage() {
        return location != null ? category.healDamage(location, getHealth(), damage) : 0;
    }

    public long getPrice() {
        return Math.round(price + 
            (armour != null ? armour.price*0.2 : 0) +
            (artefact != null ? artefact.price*0.2 : 0));
    }

    public long getBuyingPrice() {
        return price;
    }

    public long getSellingPrice() {
        return Math.round(getPrice() * 0.9);
    }

    public void setEquipment(Armour armour) {
        this.armour = armour;
    }

    public void setEquipment(Artefact artefact) {
        this.artefact = artefact;
    }

    public void takeDamage(double damage) {
        this.damage += damage;
    }

    public void healDamage(double health) {
        this.damage -= health;
        if (this.damage < 0) {
            this.damage = 0;
        }
    }

    public void setLocation(GameLocation location) {
        this.location = location;
    }

    public Armour getArmour() {
        return armour;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void resetDamage() {
        this.damage = 0;
    }

    public double getRemainingHealth() {
        double remainingHealth = getHealth() - damage;
        return remainingHealth < 0 ? 0 : Math.round(remainingHealth * 10.0) / 10.0;
    }

    public boolean isDefeated() {
        return getHealth() - damage <= 0;
    }

    public String getType() {
        if (this instanceof ArcherGameCharacter) {
            return "Archer";
        } else if (this instanceof KnightGameCharacter) {
            return "Knight";
        } else if (this instanceof MageGameCharacter) {
            return "Mage";
        } else if (this instanceof HealerGameCharacter) {
            return "Healer";
        } else if (this instanceof MythicalCreatureGameCharacter) {
            return "Mythical Creature";
        }
        return "Unknown";
    }

    public void displayStats() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + getType());
        System.out.println("Category: " + category.name);
        System.out.println("Attack: " + getAttack());
        System.out.println("Defense: " + getDefense());
        System.out.println("Health: " + getHealth());
        System.out.println("Speed: " + getSpeed());
    }
}

class ArcherGameCharacter<T extends GameCharacterCategory> extends GameCharacter<T> {
    public ArcherGameCharacter(int id, String name, int attack, int defense, int health, int speed, int price, T category) {
        super(id, name, attack, defense, health, speed, price, category);
    }
}

class KnightGameCharacter<T extends GameCharacterCategory> extends GameCharacter<T> {
    public KnightGameCharacter(int id, String name, int attack, int defense, int health, int speed, int price, T category) {
        super(id, name, attack, defense, health, speed, price, category);
    }
}

class MageGameCharacter<T extends GameCharacterCategory> extends GameCharacter<T> {
    public MageGameCharacter(int id, String name, int attack, int defense, int health, int speed, int price, T category) {
        super(id, name, attack, defense, health, speed, price, category);
    }
}

class HealerGameCharacter<T extends GameCharacterCategory> extends GameCharacter<T> {
    public HealerGameCharacter(int id, String name, int attack, int defense, int health, int speed, int price, T category) {
        super(id, name, attack, defense, health, speed, price, category);
    }
}

class MythicalCreatureGameCharacter<T extends GameCharacterCategory> extends GameCharacter<T> {
    public MythicalCreatureGameCharacter(int id, String name, int attack, int defense, int health, int speed, int price, T category) {
        super(id, name, attack, defense, health, speed, price, category);
    }
}

class TeamManager {
    private ArcherGameCharacter<? extends GameCharacterCategory> archer;
    private KnightGameCharacter<? extends GameCharacterCategory> knight;
    private MageGameCharacter<? extends GameCharacterCategory> mage;
    private HealerGameCharacter<? extends GameCharacterCategory> healer;
    private MythicalCreatureGameCharacter<? extends GameCharacterCategory> mythicalCreature;

    
    public boolean areAllCharactersDefeated() {
        return archer.isDefeated() && 
            knight.isDefeated() && 
            mage.isDefeated() && 
            healer.isDefeated() &&
            mythicalCreature.isDefeated();
    }

    public List<GameCharacter<? extends GameCharacterCategory>> getCharacters() {
        return List.of(archer, knight, mage, healer, mythicalCreature);
    }

    public boolean isTeamReady() {
        return archer != null && 
            knight != null && 
            mage != null && 
            healer != null &&
            mythicalCreature != null;
    }

    public void prepareTeam(GameLocation location) {
        archer.resetDamage();
        archer.setLocation(location);
        knight.resetDamage();
        knight.setLocation(location);
        mage.resetDamage();
        mage.setLocation(location);
        healer.resetDamage();
        healer.setLocation(location);
        mythicalCreature.resetDamage();
        mythicalCreature.setLocation(location);
    }

    public List<GameCharacter<? extends GameCharacterCategory>> getCharactersAvailableToAttackBySpeed() {
        List<GameCharacter<? extends GameCharacterCategory>> priorityOrder = List.of(
            archer, knight, mythicalCreature, mage, healer
        );
        List<GameCharacter<? extends GameCharacterCategory>> characters = new ArrayList<>(priorityOrder);
        characters.sort((c1, c2) -> { // descending order
            if (c1.getSpeed() > c2.getSpeed()) {
                return -1;
            } else if (c1.getSpeed() < c2.getSpeed()) {
                return 1;
            } else {
                return Integer.compare(priorityOrder.indexOf(c1), priorityOrder.indexOf(c2));
            }
        });
        characters.removeIf(c -> c.isDefeated());
        return characters;
    }

    public List<GameCharacter<? extends GameCharacterCategory>> getCharactersAvailableToDefendByDefence() {
        List<GameCharacter<? extends GameCharacterCategory>> priorityOrder = List.of(
            healer, mythicalCreature, archer, knight, mage
        );
        List<GameCharacter<? extends GameCharacterCategory>> characters = new ArrayList<>(priorityOrder);
        characters.sort((c1, c2) -> { // ascending order
            if (c1.getDefense() < c2.getDefense()) {
                return -1;
            } else if (c1.getDefense() > c2.getDefense()) {
                return 1;
            } else {
                return Integer.compare(priorityOrder.indexOf(c1), priorityOrder.indexOf(c2));
            }
        });
        characters.removeIf(c -> c.isDefeated());
        return characters;
    }

    public List<GameCharacter<? extends GameCharacterCategory>> getCharactersAvailableToHealByHealth() {
        List<GameCharacter<? extends GameCharacterCategory>> priorityOrder = List.of(
            mythicalCreature, mage, knight, archer
        );
        List<GameCharacter<? extends GameCharacterCategory>> characters = new ArrayList<>(priorityOrder);
        characters.sort((c1, c2) -> { // ascending order
            if (c1.getHealth() < c2.getHealth()) {
                return -1;
            } else if (c1.getHealth() > c2.getHealth()) {
                return 1;
            } else {
                return Integer.compare(priorityOrder.indexOf(c1), priorityOrder.indexOf(c2));
            }
        });
        characters.removeIf(c -> c.isDefeated());
        return characters;
    }

    public boolean isCharacterInTeam(GameCharacter<? extends GameCharacterCategory> character) {
        if (character instanceof ArcherGameCharacter) {
            return archer != null && archer.id == character.id;
        } else if (character instanceof KnightGameCharacter) {
            return knight != null && knight.id == character.id;
        } else if (character instanceof MageGameCharacter) {
            return mage != null && mage.id == character.id;
        } else if (character instanceof HealerGameCharacter) {
            return healer != null && healer.id == character.id;
        } else if (character instanceof MythicalCreatureGameCharacter) {
            return mythicalCreature != null && mythicalCreature.id == character.id;
        }
        return false;
    }

    public GameCharacter<? extends GameCharacterCategory> getActiveCharacter(GameCharacter<? extends GameCharacterCategory> character) {
        if (character instanceof ArcherGameCharacter) {
            return archer;
        } else if (character instanceof KnightGameCharacter) {
            return knight;
        } else if (character instanceof MageGameCharacter) {
            return mage;
        } else if (character instanceof HealerGameCharacter) {
            return healer;
        } else if (character instanceof MythicalCreatureGameCharacter) {
            return mythicalCreature;
        }
        return null;
    }

    public void setTeamCharacter(GameCharacter<? extends GameCharacterCategory> character) {
        if (character instanceof ArcherGameCharacter) {
            archer = (ArcherGameCharacter<? extends GameCharacterCategory>) character;
        } else if (character instanceof KnightGameCharacter) {
            knight = (KnightGameCharacter<? extends GameCharacterCategory>) character;
        } else if (character instanceof MageGameCharacter) {
            mage = (MageGameCharacter<? extends GameCharacterCategory>) character;
        } else if (character instanceof HealerGameCharacter) {
            healer = (HealerGameCharacter<? extends GameCharacterCategory>) character;
        } else if (character instanceof MythicalCreatureGameCharacter) {
            mythicalCreature = (MythicalCreatureGameCharacter<? extends GameCharacterCategory>) character;
        }
    }

    public ArcherGameCharacter<? extends GameCharacterCategory> getArcher() {
        return archer;
    }

    public KnightGameCharacter<? extends GameCharacterCategory> getKnight() {
        return knight;
    }

    public MageGameCharacter<? extends GameCharacterCategory> getMage() {
        return mage;
    }

    public HealerGameCharacter<? extends GameCharacterCategory> getHealer() {
        return healer;
    }

    public MythicalCreatureGameCharacter<? extends GameCharacterCategory> getMythicalCreature() {
        return mythicalCreature;
    }

    public void removeTeamCharacter(GameCharacter<? extends GameCharacterCategory> character) {
        if (character instanceof ArcherGameCharacter) {
            archer = null;
        } else if (character instanceof KnightGameCharacter) {
            knight = null;
        } else if (character instanceof MageGameCharacter) {
            mage = null;
        } else if (character instanceof HealerGameCharacter) {
            healer = null;
        } else if (character instanceof MythicalCreatureGameCharacter) {
            mythicalCreature = null;
        }
    }

    public void resetTeam() {
        archer = null;
        knight = null;
        mage = null;
        healer = null;
        mythicalCreature = null;
    }

    public String teamDataToCsv() {
        String csvString = "";
        if (archer != null) {
            csvString += archer.id + "," + 
            (archer.getArmour() != null ? archer.getArmour().id : -1) + "," + 
            (archer.getArtefact() != null ? archer.getArtefact().id : -1) + ",";
        } else {
            csvString += "-1,-1,-1,";
        }
        if (knight != null) {
            csvString += knight.id + "," + 
            (knight.getArmour() != null ? knight.getArmour().id : -1) + "," + 
            (knight.getArtefact() != null ? knight.getArtefact().id : -1) + ",";
        } else {
            csvString += "-1,-1,-1,";
        }
        if (mage != null) {
            csvString += mage.id + "," + 
            (mage.getArmour() != null ? mage.getArmour().id : -1) + "," + 
            (mage.getArtefact() != null ? mage.getArtefact().id : -1) + ",";
        } else {
            csvString += "-1,-1,-1,";
        }
        if (healer != null) {
            csvString += healer.id + "," + 
            (healer.getArmour() != null ? healer.getArmour().id : -1) + "," + 
            (healer.getArtefact() != null ? healer.getArtefact().id : -1) + ",";
        } else {
            csvString += "-1,-1,-1,";
        }
        if (mythicalCreature != null) {
            csvString += mythicalCreature.id + "," + 
            (mythicalCreature.getArmour() != null ? mythicalCreature.getArmour().id : -1) + "," + 
            (mythicalCreature.getArtefact() != null ? mythicalCreature.getArtefact().id : -1);
        } else {
            csvString += "-1,-1,-1";
        }
        return csvString;
    }

    public void loadTeamDataFromCsv(String csvString) {
        resetTeam();
        String[] data = csvString.split(",");
        for (int i = 0; i < data.length; i += 3) {
            int characterId = Integer.parseInt(data[i]);
            int armourId = Integer.parseInt(data[i + 1]);
            int artefactId = Integer.parseInt(data[i + 2]);
            GameCharacter<? extends GameCharacterCategory> character = GameCharacterFactory.getCharacter(characterId);
            if (character != null) {
                setTeamCharacter(character);
                if (armourId != -1) {
                    Armour armour = (Armour) EquipmentFactory.getEquipment(armourId);
                    character.setEquipment(armour);
                }
                if (artefactId != -1) {
                    Artefact artefact = (Artefact) EquipmentFactory.getEquipment(artefactId);
                    character.setEquipment(artefact);
                }
            }
        }
    }
}

class GameCharacterFactory {
    public static List<GameCharacter<? extends GameCharacterCategory>> getArchers() {
        return List.of(
            new ArcherGameCharacter<Highlander>(1, "Shooter", 11, 4, 6, 9, 80, new Highlander()),
            new ArcherGameCharacter<Highlander>(2, "Ranger", 14, 5, 8, 10, 115, new Highlander()),
            new ArcherGameCharacter<Sunchild>(3, "Sunfire", 15, 5, 7, 14, 160, new Sunchild()),
            new ArcherGameCharacter<Sunchild>(4, "Zing", 16, 9, 11, 14, 200, new Sunchild()),
            new ArcherGameCharacter<Mystic>(5, "Saggitarius", 18, 7, 12, 17, 230, new Mystic())
        );
    }

    public static List<GameCharacter<? extends GameCharacterCategory>> getKnights() {
        return List.of(
            new KnightGameCharacter<Marshlander>(6, "Squire", 8, 9, 7, 8, 85, new Marshlander()),
            new KnightGameCharacter<Highlander>(7, "Cavalier", 10, 12, 7,10, 110, new Highlander()),
            new KnightGameCharacter<Sunchild>(8, "Templar", 14, 16, 12, 12, 155, new Sunchild()),
            new KnightGameCharacter<Highlander>(9, "Zoro", 17, 16, 13, 14, 180, new Highlander()),
            new KnightGameCharacter<Marshlander>(10, "Swiftblade", 18, 20, 17, 13, 250, new Marshlander())
        );
    }

    public static List<GameCharacter<? extends GameCharacterCategory>> getMages() {
        return List.of(
            new MageGameCharacter<Marshlander>(11, "Warlock",12, 7, 10, 12,100, new Marshlander()),
            new MageGameCharacter<Mystic>(12, "Illusionist",13,8,12,14,120,new Mystic()),
            new MageGameCharacter<Highlander>(13, "Enchanter",16,10,13,16,160, new Highlander()),
            new MageGameCharacter<Highlander>(14,"Conjurer",18,15,14,12,195,new Highlander()),
            new MageGameCharacter<Mystic>(15,"Eldritch",19,17,18,14,270,new Mystic())
        );
    }

    public static List<GameCharacter<? extends GameCharacterCategory>> getHealers() {
        return List.of(
            new HealerGameCharacter<Sunchild>(16,"Soother",10,8,9,6,95,new Sunchild()),
            new HealerGameCharacter<Highlander>(17,"Medic",12,9,10,7,125,new Highlander()),
            new HealerGameCharacter<Marshlander>(18,"Alchimist",13,13,13,13,150,new Marshlander()),
            new HealerGameCharacter<Mystic>(19,"Saint",16,14,17,9,200,new Mystic()),
            new HealerGameCharacter<Sunchild>(20,"Lightbringer",17,15,19,12,260,new Sunchild())
        );
    }

    public static List<GameCharacter<? extends GameCharacterCategory>> getMythicalCreatures() {
        return List.of(
            new MythicalCreatureGameCharacter<Sunchild>(21,"Dragon",12,14,15,8,120,new Sunchild()),
            new MythicalCreatureGameCharacter<Marshlander>(22,"Basilisk",115,11,10,12,165,new Marshlander()),
            new MythicalCreatureGameCharacter<Marshlander>(23,"Hydra",12,16,15,11,205,new Marshlander()),
            new MythicalCreatureGameCharacter<Sunchild>(24,"Phoenix",17,13,17,19,275,new Sunchild()),
            new MythicalCreatureGameCharacter<Mystic>(25,"Pegasus",14,18,20,20,340,new Mystic())
        );
    }

    public static GameCharacter<? extends GameCharacterCategory> getCharacter(int id) {
        List<List<GameCharacter<? extends GameCharacterCategory>>> characters = List.of(
            getArchers(),
            getKnights(),
            getMages(),
            getHealers(),
            getMythicalCreatures()
        );
        for (List<GameCharacter<? extends GameCharacterCategory>> list : characters) {
            for (GameCharacter<? extends GameCharacterCategory> character : list) {
                if (character.id == id) {
                    return character;
                }
            }
        }
        return null;
    }
}

class Player {
    final String username;
    private String displayName;
    final int userId;
    private long coins;
    private int xp;
    private GameLocation location;
    public TeamManager teamManager = new TeamManager();

    public Player(String username, String displayName, int userId, int coins, int xp) {
        this.username = username;
        this.displayName = displayName;
        this.userId = userId;
        this.coins = coins;
        this.xp = xp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = Math.round(coins);
    }

    public void incrementXp() {
        this.xp += 1;
    }

    public int getXp() {
        return xp;
    }

    public void setLocation(GameLocation location) {
        this.location = location;
    }

    public String getLocationName() {
        return location != null ? location.name : "(Not Selected)";
    }

    public GameLocation getLocation() {
        return location;
    }
}

class PlayersManager {
    private Player currentUser;
    private Map<String, Player> players;
    private Scanner scanner;
    private String userDataFile = "users.csv";

    public PlayersManager() {
        this.scanner = new Scanner(System.in);
        loadPlayers();
    }

    public boolean isPlayerLoggedIn() {
        return currentUser != null;
    }

    public boolean isRegisteredPlayer(String username) {
        return players.containsKey(username);
    }

    public void setCurrentPlayer(String username) {
        if (players.containsKey(username)) {
            currentUser = players.get(username);
            Console.printSuccess("Logged in as " + currentUser.getDisplayName());
        } else {
            Console.printError("Player not found. Please register as a new player.");
        }
    }

    public void logoutPlayer() {
        currentUser = null;
    }

    public Player getCurrentPlayer() {
        return currentUser;
    }

    public void loadPlayers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            players = new java.util.HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String teamCsvString = "";
                String username = data[0];
                String displayName = data[1];
                int userId = Integer.parseInt(data[2]);
                int coins = Integer.parseInt(data[3]);
                int xp = Integer.parseInt(data[4]);
                GameLocation location = GameLocation.getLocation(data[5]);
                Player player = new Player(username, displayName, userId, coins, xp);
                player.setLocation(location);
                for (int i = 6; i < data.length; i++) {
                    teamCsvString += data[i] + ",";
                }
                player.teamManager.loadTeamDataFromCsv(teamCsvString);
                players.put(username, player);
            }
        } catch (IOException e) {
            try {
                FileWriter writer = new FileWriter(userDataFile);
                writer.close();
                players = new java.util.HashMap<>();
            } catch (IOException e2) {
                Console.printError("Failed to create users file: " + e2.getMessage());
            }
        }
    }

    public List<Player> getPlayers() {
        return List.copyOf(players.values());
    }

    public void createPlayer() {
        System.out.println("Register a new player\n---------------------\n");
        // Prompt for username
        while (true) {
            System.out.print("Enter your username. Use lowercase letters only. Enter . to return back.\nUsername: ");
            String username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                Console.printError("Username cannot be empty!\n");
                continue;
            }
            if (username.equals(".")) {
                System.out.println();
                break;
            }
            if (!username.matches("[a-z]+")) {
                Console.printError("Invalid username. Use lowercase letters only.\n");
                continue;
            }
            if (isRegisteredPlayer(username)) {
                Console.printError("Username exists. Choose different username.\n");
                continue;
            }
            // Prompt for display name
            System.out.print("\nEnter your display name. This can be changed later. Use letters and spaces only. Enter . to return back.\n");
            Console.printInfo("Username: " + username);
            System.out.print("Display Name: ");
            String displayName = scanner.nextLine().trim();
            if (displayName.isEmpty()) {
                Console.printError("Display name cannot be empty.\n");
                continue;
            }
            if (username.equals(".")) {
                System.out.println();
                break;
            }
            if (!displayName.matches("[a-zA-Z ]+")) {
                Console.printError("Invalid display name. Use letters and spaces only.\n");
                continue;
            }
            System.out.println("\nCreating player...");
            int userId = players.size() + 1;
            int coins = 500;
            int xp = 0;
            Player player = new Player(username, displayName, userId, coins, xp);
            players.put(username, player);
            savePlayers();
            Console.printSuccess("Player created successfully with username: " + username);
            Console.wait(800);
            break;
        }
    }

    public void savePlayers() {
        try (FileWriter writer = new FileWriter(userDataFile)) {
            for (Player player : players.values()) {
                writer.write(player.username + "," +
                    player.getDisplayName() + "," +
                    player.userId + "," +
                    player.getCoins() + "," +
                    player.getXp() + "," +
                    player.getLocationName() + "," +
                    player.teamManager.teamDataToCsv() + "\n"
                );
            }
        } catch (IOException e) {
            Console.printError("Failed to save players: " + e.getMessage());
        }
    }
}

class MainMenu {
    private Scanner scanner;
    private List<String> menuOptions;

    public MainMenu(List<String> menuOptions) {
        scanner = new Scanner(System.in);
        this.menuOptions = menuOptions;
    }

    public void display() {
        System.out.println("Main Menu\n---------\n");
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println((i + 1) + ". " + menuOptions.get(i));
        }
        System.out.println();
    }

    public int getChoice() {
        System.out.print("Enter option number: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

public class MysticMayhemGame {
    private Scanner scanner = new Scanner(System.in);
    private MainMenu menu = new MainMenu(List.of(
        "Start Game",
        "Manage Army & Weapons",
        "Select Game Map",
        "Edit Display Name",
        "Create New Player",
        "Switch Player",
        "Exit Game"
    ));
    private PlayersManager playersManager = new PlayersManager();

    public static void main(String[] args) {
        MysticMayhemGame game = new MysticMayhemGame();
        game.begin();
    }

    private void begin() {
        String asciiArt = 
            "   __  ___         __  _       __  ___          __           \n" +
            "  /  |/  /_ _____ / /_(_)___  /  |/  /__ ___ __/ /  ___ __ _ \n" +
            " / /|_/ / // (_-</ __/ / __/ / /|_/ / _ `/ // / _ \\/ -_)  ' \\\n" +
            "/_/  /_/\\_, /___/\\__/_/\\__/ /_/  /_/\\_,_/\\_, /_//_/\\__/_/_/_/\n" +
            "       /___/                            /___/               \n";
        Console.printInfo(asciiArt);
        loginPlayer();
        while (true) {
            System.out.println();
            menu.display();
            Console.printInfo("Logged in as " + playersManager.getCurrentPlayer().getDisplayName());
            if (!playersManager.getCurrentPlayer().teamManager.isTeamReady()) {
                Console.printWarning("! Your team is not ready. Choose your characters and equip them with weapons.");
            }
            if (playersManager.getCurrentPlayer().getLocation() == null) {
                Console.printWarning("! Game map is not selected. Select a game map.");
            }
            if (playersManager.getCurrentPlayer().teamManager.isTeamReady() && playersManager.getCurrentPlayer().getLocation() != null) {
                Console.printSuccess("Your team is ready and game map is set. You are ready to start the game.");
            }
            int choice = menu.getChoice();
            System.out.println();
            switch (choice) {
                case 1:
                    startGame();
                    Console.wait(800);
                    break;
                case 2:
                    chooseCharacters();
                    break;
                case 3:
                    selectGameLocation();
                    break;
                case 4:
                    changeDisplayName();
                    break;
                case 5:
                    createNewPlayer();
                    break;
                case 6:
                    changePlayer();
                    Console.wait(800);
                    break;
                case 7:
                    Console.printInfo("Exiting the game. Goodbye!");
                    System.exit(0);
                default:
                    Console.printError("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void selectGameLocation() {
        System.out.println("Select Game Map\n---------------");
        while (true) {
            GameLocation currentLocation = playersManager.getCurrentPlayer().getLocation();
            GameLocation[] locations = GameLocation.values();
            for (int i = 0; i < locations.length; i++) {
                if (locations[i] == currentLocation) {
                    Console.printSuccess((i + 1) + ". " + locations[i].name + " (Selected)");
                } else {
                    System.out.println((i + 1) + ". " + locations[i].name);
                }
            }
            System.out.print("\nEnter location number (Enter . to return back): ");
            String choiceInput = scanner.nextLine().trim();
            if (choiceInput.equals(".")) {
                break;
            }
            try {
                int choice = Integer.parseInt(choiceInput);
                if (choice >= 1 && choice <= locations.length) {
                    GameLocation selectedLocation = locations[choice - 1];
                    if (selectedLocation != currentLocation) {
                        playersManager.getCurrentPlayer().setLocation(selectedLocation);
                        saveGameState();
                        Console.printSuccess("Location changed to " + selectedLocation.name);
                        Console.wait(800);
                        break;
                    } else {
                        Console.printError("You are already in " + selectedLocation.name);
                    }
                } else {
                    Console.printError("Invalid location number. Please enter a valid location number.");
                }
            } catch (NumberFormatException e) {
                Console.printError("Please enter a valid location number.");
            }
        }
    }

    private void changeDisplayName() {
        Player currentPlayer = playersManager.getCurrentPlayer();
        System.out.println("Edit Display Name\n-----------------");
        while (true) {
            Console.printInfo("\nCurrent Display Name: " + currentPlayer.getDisplayName());
            System.out.print("Enter new display name(Enter . to return back): ");
            String newName = scanner.nextLine().trim();
            if (newName.equals(".")) {
                return;
            }
            if (newName.isEmpty()) {
                Console.printError("Display name cannot be empty.");
            } else if (!newName.matches("[a-zA-Z ]+")) {
                Console.printError("Invalid display name. Use letters and spaces only.");
            } else {
                currentPlayer.setDisplayName(newName);
                saveGameState();
                Console.printSuccess("Display name changed to " + newName);
                Console.wait(800);
                break;
            }
        }
    }

    private void saveGameState() {
        playersManager.savePlayers();
    }

    private void loginPlayer() {
        while (!playersManager.isPlayerLoggedIn()) {
            System.out.print("Enter username to login(Enter . to create a new player): ");
            String username = scanner.nextLine().trim();
            if (username.equals(".")) {
                System.out.println();
                createNewPlayer();
            } else if (playersManager.isRegisteredPlayer(username)) {
                playersManager.setCurrentPlayer(username);
            } else if (username.isEmpty()) {
                Console.printError("Enter a existing username or create a new player.\n");
            } else {
                Console.printError("Player not found!\n");
            }
        }
    }

    private void createNewPlayer() {
        playersManager.createPlayer();
    }

    private void changePlayer() {
        Console.printSuccess("Logged out from " + playersManager.getCurrentPlayer().getDisplayName());
        playersManager.logoutPlayer();
        System.out.println("Choose a player to log in");
        loginPlayer();
    }

    private void chooseCharacters() {
        System.out.println("Choose your characters\n---------------------");
        int currentPage = 1;
        TeamManager teamManager = playersManager.getCurrentPlayer().teamManager;
        List<List<GameCharacter<? extends GameCharacterCategory>>> pages = List.of(
            GameCharacterFactory.getArchers(),
            GameCharacterFactory.getKnights(),
            GameCharacterFactory.getMages(),
            GameCharacterFactory.getHealers(),
            GameCharacterFactory.getMythicalCreatures()
        );
        while (true) {
            List<GameCharacter<? extends GameCharacterCategory>> characters = pages.get(currentPage - 1);
            System.out.println("\n* Choose your " + characters.get(0).getType() + " character *\n");
            for (int i = 0; i < characters.size(); i++) {
                GameCharacter<? extends GameCharacterCategory> character = characters.get(i);
                if (teamManager.isCharacterInTeam(character)) {
                    Console.printSuccess((i + 1) + ". " + character.name + " (Selected)");
                } else {
                    System.out.print((i + 1) + ". " + character.name);
                    Console.printInfo(" (" + character.getBuyingPrice() + " coins)");
                }
            }
            System.out.println("\nPage " + currentPage + " of " + pages.size());
            System.out.println("P. Previous page | N. Next page | B. Back");
            System.out.print("Enter character number(or P/N/B): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("p")) {
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    currentPage = pages.size();
                }
            } else if (choice.equals("n")) {
                if (currentPage < pages.size()) {
                    currentPage++;
                } else {
                    currentPage = 1;
                }
            } else if (choice.equals("b")) {
                break;
            } else {
                try {
                    int characterIndex = Integer.parseInt(choice) - 1;
                    if (characterIndex >= 0 && characterIndex < characters.size()) {
                        characterDetails(characters.get(characterIndex));
                    } else {
                        Console.printError("\nInvalid character number!");
                    }
                } catch (NumberFormatException e) {
                    Console.printError("\nInvalid option! Enter option number or P/N/B.");
                }
            }
        }
    }

    private void characterDetails(GameCharacter<? extends GameCharacterCategory> character) {
        Player currentPlayer = playersManager.getCurrentPlayer();
        TeamManager teamManager = currentPlayer.teamManager;
        while (true) {
            GameCharacter<? extends GameCharacterCategory> selectedCharacter = teamManager.getActiveCharacter(character);
            System.out.println("\nCharacter Stats\n---------------\n");
            boolean isCharacterInTeam = teamManager.isCharacterInTeam(character);
            if (isCharacterInTeam) {
                selectedCharacter.displayStats();
                Armour armour = selectedCharacter.getArmour();
                Artefact artefact = selectedCharacter.getArtefact();
                System.out.println("Armour: " + (armour != null ? armour.name : "(Not Selected)"));
                System.out.println("Artefact: " + (artefact != null ? artefact.name : "(Not Selected)"));
                System.out.println("Sell price: " + selectedCharacter.getSellingPrice() + " coins");
                Console.printSuccess("\n(Active Character)");
                System.out.println("Available coins: " + currentPlayer.getCoins() + " coins");
                System.out.println("Q. Choose Armour | W. Choose Artefact | S. Sell Character | B. Back");
            } else {
                character.displayStats();
                System.out.println("Buy price: " + character.getBuyingPrice() + " coins");
                Console.printInfo("\nPurchase character for " + character.getBuyingPrice() + " coins?");
                if (selectedCharacter != null) {
                    Console.printWarning("(Alert: If purchased " + selectedCharacter.name + " will be sold for " + selectedCharacter.getSellingPrice() + " coins)");
                }
                System.out.println("Available coins: " + currentPlayer.getCoins() + " coins");
                System.out.println("P. Purchase | B. Back");
            }
            System.out.print("Enter option: ");
            String option = scanner.nextLine().trim().toLowerCase();
            if (option.equals("p")) {
                if (isCharacterInTeam) {
                    Console.printError("Character already in team.");
                } else {
                    int coinsChange = (int) (
                        character.getBuyingPrice() - 
                        (selectedCharacter != null ? selectedCharacter.getSellingPrice() : 0)
                    );
                    if (currentPlayer.getCoins() - coinsChange < 0) {
                        Console.printError("\nInsufficient coins to purchase character!");
                        Console.wait(800);
                    } else {
                        currentPlayer.setCoins(currentPlayer.getCoins() - coinsChange);
                        teamManager.setTeamCharacter(character);
                        saveGameState();
                        Console.printSuccess("\nCharacter purchased successfully!");
                        Console.wait(800);
                    }
                }
            } else if (option.equals("s")) {
                if (isCharacterInTeam) {
                    currentPlayer.setCoins(currentPlayer.getCoins() + selectedCharacter.getSellingPrice());
                    teamManager.removeTeamCharacter(selectedCharacter);
                    saveGameState();
                    Console.printSuccess("\nCharacter sold successfully!");
                    Console.wait(800);
                } else {
                    Console.printError("Character not in team.");
                }
            } else if (option.equals("q")) {
                if (isCharacterInTeam) {
                    chooseCharacterEquipment(selectedCharacter, "armour");
                } else {
                    Console.printError("Character not in team.");
                }
            } else if (option.equals("w")) {
                if (isCharacterInTeam) {
                    chooseCharacterEquipment(selectedCharacter, "artefact");
                } else {
                    Console.printError("Character not in team.");
                }
            } else if (option.equals("b")) {
                break;
            } else {
                Console.printError("Invalid option. Please enter a valid option.");
            }
        }
    }

    private void chooseCharacterEquipment(GameCharacter<? extends GameCharacterCategory> character, String type) {
        Player currentPlayer = playersManager.getCurrentPlayer();
        TeamManager teamManager = currentPlayer.teamManager;
        List<? extends GameEquipment> equipmentList = type.equals("armour") 
            ? EquipmentFactory.getArmours() 
            : EquipmentFactory.getArtefacts();
        GameCharacter<? extends GameCharacterCategory> selectedCharacter = teamManager.getActiveCharacter(character);
        int chosenEquipmentId = -1;
        while (true) {
            System.out.println("\nPurchase " + type + " for " + character.name + "\n--------------------------\n");
            for (int i = 0; i < equipmentList.size(); i++) {
                GameEquipment equipment = equipmentList.get(i);
                if (selectedCharacter.getArmour() != null 
                && type.equals("armour") 
                && selectedCharacter.getArmour().id == equipment.id) {
                    chosenEquipmentId = selectedCharacter.getArmour().id;
                    Console.printSuccess((i + 1) + ". " + equipment.name + " (Selected)");
                } else if (selectedCharacter.getArtefact() != null 
                && type.equals("artefact") 
                && selectedCharacter.getArtefact().id == equipment.id) {
                    chosenEquipmentId = selectedCharacter.getArtefact().id;
                    Console.printSuccess((i + 1) + ". " + equipment.name + " (Selected)");
                } else {
                    System.out.print((i + 1) + ". " + equipment.name);
                    Console.printInfo(" (" + (int) (equipment.price) + " coins)");
                }
            }
            System.out.println("\nAvailable coins: " + currentPlayer.getCoins() + " coins");
            System.out.println("B. Back");
            System.out.print("Enter option: ");
            String option = scanner.nextLine().trim().toLowerCase();
            if (option.equals("b")) {
                break;
            } else {
                try {
                    int choice = Integer.parseInt(option) - 1;
                    if (choice >= 0 && choice < equipmentList.size()) {
                        GameEquipment equipment = equipmentList.get(choice);
                        if (chosenEquipmentId == equipment.id) {
                            Console.printError("\n" + equipment.name + " already selected!");
                            Console.wait(800);
                        } else if (currentPlayer.getCoins() - equipment.price < 0) {
                            Console.printError("\nInsufficient coins to purchase " + equipment.name + "!");
                            Console.wait(800);
                        } else {
                            currentPlayer.setCoins(currentPlayer.getCoins() - equipment.price);
                            if (type.equals("armour")) {
                                selectedCharacter.setEquipment((Armour) equipment);
                            } else {
                                selectedCharacter.setEquipment((Artefact) equipment);
                            }
                            saveGameState();
                            Console.printSuccess("\n" + equipment.name + " purchased successfully!");
                            Console.wait(800);
                            return;
                        }
                    } else {
                        Console.printError("Invalid option. Please enter a valid option.");
                    }
                } catch (NumberFormatException e) {
                    Console.printError("Invalid option. Please enter a valid option.");
                }
            }
        }
    }

    private Player chooseOpponent() {
        Player currentPlayer = playersManager.getCurrentPlayer();
        ArrayList<Player> players = new ArrayList<>(playersManager.getPlayers());
        players.remove(currentPlayer);
        if (players.size() == 0) {
            Console.printError("\nNo opponents available. Please create a new player from the main menu.");
            Console.wait(800);
            return null;
        }
        Collections.shuffle(players);
        int i = 0;
        while (true) {
            Player opponent = players.get(i);
            System.out.println("\nOpponent Stats\n--------------");
            Console.printInfo("Player: " + opponent.getDisplayName());
            Console.printInfo("XP: " + opponent.getXp());
            if (!opponent.teamManager.isTeamReady() || opponent.getLocation() == null) {
                Console.printWarning("Player's team is not ready or game map is not selected. Choose another opponent.");
                System.out.println("\n2. Next Opponent | 3. Exit Game");
            } else {
                System.out.println("\n1. Challenge | 2. Next Opponent | 3. Exit Game");
            }
            System.out.print("Enter option: ");
            String option = scanner.nextLine().trim();
            if (option.equals("1")) {
                if (opponent.teamManager.isTeamReady() && opponent.getLocation() != null) {
                    return opponent;
                } else {
                    Console.printError("Opponent's team is not ready or game map is not selected. Choose another opponent.");
                }
            } else if (option.equals("2")) {
                i = (i + 1) % players.size();
                continue;
            } else if (option.equals("3")) {
                break;
            } else {
                Console.printError("Invalid option. Please enter a valid option.");
            }
        }
        return null;
    }

    private void startGame() {
        Player currentPlayer = playersManager.getCurrentPlayer();
        if (!currentPlayer.teamManager.isTeamReady()) {
            Console.printError("Your team is not ready. Choose your Army and Equipments before starting the game.");
            Console.wait(800);
            return;
        } else if (currentPlayer.getLocation() == null) {
            Console.printError("Select a game map before starting the game.");
            Console.wait(800);
            return;
        }
        Console.printInfo("Choose opponent to start the game");
        Player opponentPlayer = chooseOpponent();
        if (opponentPlayer == null) { return; }
        GameLocation location = opponentPlayer.getLocation();
        currentPlayer.teamManager.prepareTeam(location);
        opponentPlayer.teamManager.prepareTeam(location);
        Player winnningPlayer = BattleManager.startBattle(currentPlayer, opponentPlayer, location);
        System.out.println();
        if (winnningPlayer == null) {
            Console.printWarning("No coins exchanged. No XP gained.");
        } else {
            Player losingPlayer = winnningPlayer == currentPlayer ? opponentPlayer : currentPlayer;
            int coinsExchanged = (int) (losingPlayer.getCoins() * 0.1);
            winnningPlayer.setCoins(winnningPlayer.getCoins() + coinsExchanged);
            losingPlayer.setCoins(losingPlayer.getCoins() - coinsExchanged);
            winnningPlayer.incrementXp();
            saveGameState();
            Console.printSuccess(winnningPlayer.getDisplayName() + " => XP: " + winnningPlayer.getXp() + " | Coins: " + winnningPlayer.getCoins());
            Console.printError(losingPlayer.getDisplayName() + " => XP: " + losingPlayer.getXp() + " | Coins: " + losingPlayer.getCoins());
        }
        Console.printSuccess("Battle Concluded.");
    }
}

class BattleManager {

    public static List<GameCharacter<? extends GameCharacterCategory>> filterDeadCharacters(List<GameCharacter<? extends GameCharacterCategory>> characters) {
        characters.removeIf(character -> character.isDefeated());
        return characters;
    }

    public static Player startBattle(Player currentPlayer, Player opponentPlayer, GameLocation location) {
        Console.printInfo("\nBattle Ground set to " + location.name + ".");
        Console.printInfo(currentPlayer.getDisplayName() + " vs. " + opponentPlayer.getDisplayName());
        Console.printSuccess("Battle begins...");
        List<GameCharacter<? extends GameCharacterCategory>> currentPlayerAttackers = 
            currentPlayer.teamManager.getCharactersAvailableToAttackBySpeed();
        List<GameCharacter<? extends GameCharacterCategory>> opponentPlayerAttackers =
            opponentPlayer.teamManager.getCharactersAvailableToAttackBySpeed();
        for (int turn = 1; turn <= 10; turn++) {
            // currentPlayer Turn
            // Current player attacker from available attackers
            currentPlayerAttackers = BattleManager.filterDeadCharacters(currentPlayerAttackers);
            if (currentPlayerAttackers.isEmpty()) {
                currentPlayerAttackers = currentPlayer.teamManager.getCharactersAvailableToAttackBySpeed();
                if (currentPlayerAttackers.isEmpty()) {
                    Console.printSuccess("\n" + opponentPlayer.getDisplayName() + " won the battle!");
                    return opponentPlayer;
                }
            }
            GameCharacter<? extends GameCharacterCategory> currentPlayerAttackingCharacter = currentPlayerAttackers.remove(0);
            // Opponent player defender from available defenders
            List<GameCharacter<? extends GameCharacterCategory>> opponentPlayerDefenders = 
                opponentPlayer.teamManager.getCharactersAvailableToDefendByDefence();
            if (opponentPlayerDefenders.isEmpty()) {
                Console.printSuccess("\n" + currentPlayer.getDisplayName() + " won the battle!");
                return currentPlayer;
            }
            GameCharacter<? extends GameCharacterCategory> opponentPlayerDefendingCharacter = opponentPlayerDefenders.get(0);
            System.out.println("\nTurn " + turn + " of 10: " + currentPlayer.getDisplayName());

            // Currrent Player Battle Turn
            battleTurn(currentPlayer, opponentPlayer, currentPlayerAttackingCharacter, opponentPlayerDefendingCharacter);
            Console.wait(800);

            // Opponent player Turn
            // Opponent player attacker from available attackers
            opponentPlayerAttackers = BattleManager.filterDeadCharacters(opponentPlayerAttackers);
            if (opponentPlayerAttackers.isEmpty()) {
                opponentPlayerAttackers = opponentPlayer.teamManager.getCharactersAvailableToAttackBySpeed();
                if (opponentPlayerAttackers.isEmpty()) {
                    Console.printSuccess("\n" + currentPlayer.getDisplayName() + " won the battle!");
                    return currentPlayer;
                }
            }
            GameCharacter<? extends GameCharacterCategory> opponentPlayerAttackingCharacter = opponentPlayerAttackers.remove(0);
            // Current player defender from available defenders
            List<GameCharacter<? extends GameCharacterCategory>> currentPlayerDefenders = 
                currentPlayer.teamManager.getCharactersAvailableToDefendByDefence();
            if (currentPlayerDefenders.isEmpty()) {
                Console.printSuccess("\n" + opponentPlayer.getDisplayName() + " won the battle!");
                return opponentPlayer;
            }
            GameCharacter<? extends GameCharacterCategory> currentPlayerDefendingCharacter = currentPlayerDefenders.get(0);
            System.out.println("\nTurn " + turn + " of 10: " + opponentPlayer.getDisplayName());

            // Opponent Player Battle Turn
            battleTurn(opponentPlayer, currentPlayer, opponentPlayerAttackingCharacter, currentPlayerDefendingCharacter);
            Console.wait(800);

            // Check if all characters are defeated
            if (currentPlayer.teamManager.getCharactersAvailableToAttackBySpeed().isEmpty()) {
                Console.printSuccess("\n" + opponentPlayer.getDisplayName() + " won the battle!");
                return opponentPlayer;
            }
            if (opponentPlayer.teamManager.getCharactersAvailableToAttackBySpeed().isEmpty()) {
                Console.printSuccess("\n" + currentPlayer.getDisplayName() + " won the battle!");
                return currentPlayer;
            }
        }
        Console.printWarning("\nThe battle is a draw!");
        // show remaining health of characters
        Console.printInfo("\nYour Remaining Characters:");
        for (GameCharacter<? extends GameCharacterCategory> character : currentPlayer.teamManager.getCharactersAvailableToDefendByDefence()) {
            Console.printInfo(character.name + " => " + character.getRemainingHealth() + " health");
        }
        Console.wait(800);
        Console.printInfo("\nOpponent's Remaining Characters:");
        for (GameCharacter<? extends GameCharacterCategory> character : opponentPlayer.teamManager.getCharactersAvailableToDefendByDefence()) {
            Console.printInfo(character.name + " => " + character.getRemainingHealth() + " health");
        }
        Console.wait(800);
        return null;
    }
    
    public static void battleTurn(Player attacker, Player defender, GameCharacter<? extends GameCharacterCategory> attackingCharacter, GameCharacter<? extends GameCharacterCategory> defendingCharacter) {
        if (attackingCharacter instanceof HealerGameCharacter) {
            HealerGameCharacter<? extends GameCharacterCategory> healer = 
                (HealerGameCharacter<? extends GameCharacterCategory>) attackingCharacter;
            double healAmount = 0.1 * healer.getAttack();
            healAmount = Math.round(healAmount * 10.0) / 10.0;
            if (attacker.teamManager.getCharactersAvailableToHealByHealth().size() > 0) {
                GameCharacter<? extends GameCharacterCategory> weakestCharacter =  attacker.teamManager.getCharactersAvailableToHealByHealth().get(0);
                weakestCharacter.healDamage(healAmount);
                Console.printInfo(attackingCharacter.name + " (Healer) heals " + weakestCharacter.name + " for " + healAmount + " health.");
                Console.printInfo(weakestCharacter.name + "'s remaining health: " + weakestCharacter.getRemainingHealth());
            } else {
                Console.printInfo(attackingCharacter.name + " (Healer) has no characters to heal.");
            }
        } else {
            double attack = attackingCharacter.getAttack();
            double defense = defendingCharacter.getDefense();
            double damage = (0.5 * attack) - (0.1 * defense);
            damage = Math.round(damage * 10.0) / 10.0;
            Console.printInfo(attackingCharacter.name + " attacks " + defendingCharacter.name + " and gives " + damage + " damage.");
            defendingCharacter.takeDamage(damage);
            double newDefenderHealth = defendingCharacter.getRemainingHealth();
            Console.printInfo(defendingCharacter.name + "'s remaining health: " + newDefenderHealth);
            if (defendingCharacter.isDefeated()) {
                Console.printError(defendingCharacter.name + " died!");
            }
        }
        if (attackingCharacter.hasExtraAttackTurn()) {
            Console.printSuccess("[Extra Turn]");
            double attack = attackingCharacter.getExtraTurnAttack();
            if (attackingCharacter instanceof HealerGameCharacter) {
                double healAmount = 0.1 * attack;
                healAmount = Math.round(healAmount * 10.0) / 10.0;
                if (attacker.teamManager.getCharactersAvailableToHealByHealth().size() > 0) {
                    GameCharacter<? extends GameCharacterCategory> weakestCharacter =  attacker.teamManager.getCharactersAvailableToHealByHealth().get(0);
                    weakestCharacter.healDamage(healAmount);
                    Console.printInfo(attackingCharacter.name + " (Healer) heals " + weakestCharacter.name + " for " + healAmount + " health.");
                    Console.printInfo(weakestCharacter.name + "'s remaining health: " + weakestCharacter.getRemainingHealth());
                } else {
                    Console.printInfo(attackingCharacter.name + " (Healer) has no characters to heal.");
                }
            } else {
                if (defendingCharacter.isDefeated()) {
                    defendingCharacter = defender.teamManager.getCharactersAvailableToDefendByDefence().get(0);
                }
                double defense = defendingCharacter.getDefense();
                double damage = (0.5 * attack) - (0.1 * defense);
                damage = Math.round(damage * 10.0) / 10.0;
                Console.printInfo(attackingCharacter.name + " attacks " + defendingCharacter.name + " and gives " + damage + " damage.");
                defendingCharacter.takeDamage(damage);
                double newDefenderHealth = defendingCharacter.getRemainingHealth();
                Console.printInfo(defendingCharacter.name + "'s remaining health: " + newDefenderHealth);
                if (defendingCharacter.isDefeated()) {
                    Console.printError(defendingCharacter.name + " died!");
                }
            }
        }
        if (attackingCharacter.canHealAfterTurn()) {
            double healAmount = attackingCharacter.getHealDamage();
            healAmount = Math.round(healAmount * 10.0) / 10.0;
            attackingCharacter.healDamage(healAmount);
            Console.printSuccess("[Heal After Turn]");
            Console.printInfo(attackingCharacter.name + " (Healer) heals itself for " + healAmount + " health.");
        }
    }
}
