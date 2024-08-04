class Animal {
    public void makeSound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {

    public void makeSound() {
        System.out.println("Dog Sound");
    }
    public void bark() {
        System.out.println("Dog barks");
    }
}

public class casting {
    public static void main(String[] args) {
        Animal animal = new Dog(); // Upcasting
        animal.makeSound();

        // This instance cannot call bark method
        // animal.bark

        // Downcasting
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal; // Downcast to Dog
            dog.bark(); // Access subclass-specific method
            dog.makeSound(); 
        } else {
            System.out.println("The object is not an instance of Dog.");
        }
    }
}
