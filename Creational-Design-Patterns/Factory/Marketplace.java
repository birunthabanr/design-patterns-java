interface Product {
    void displayDetails();
    double calculateDiscount(double price);
}

class Electronics implements Product {
    private String name;
    private String brand;
    private double price;

    public Electronics(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public void displayDetails() {
        System.out.println("Electronics Product:");
        System.out.println("Name: " + name);
        System.out.println("Brand: " + brand);
        System.out.println("Price: $" + price);
    }

    @Override
    public double calculateDiscount(double discountRate) {
        double discount = price * discountRate / 100;
        return price - discount;
    }
}

 class Clothing implements Product {
    private String name;
    private String size;
    private double price;

    public Clothing(String name, String size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    @Override
    public void displayDetails() {
        System.out.println("Clothing Product:");
        System.out.println("Name: " + name);
        System.out.println("Size: " + size);
        System.out.println("Price: $" + price);
    }

    @Override
    public double calculateDiscount(double discountRate) {
        double discount = price * discountRate / 100;
        return price - discount;
    }
}


class Books implements Product {
    private String title;
    private String author;
    private double price;

    public Books(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    @Override
    public void displayDetails() {
        System.out.println("Book Product:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Price: $" + price);
    }

    @Override
    public double calculateDiscount(double discountRate) {
        double discount = price * discountRate / 100;
        return price - discount;
    }
}


class ProductFactory {
    public static Product createProduct(String productType, String name, String detail, double price) {
        switch (productType.toLowerCase()) {
            case "electronics":
                return new Electronics(name, detail, price);
            case "clothing":
                return new Clothing(name, detail, price);
            case "books":
                return new Books(name, detail, price);
            default:
                throw new IllegalArgumentException("Unknown product type: " + productType);
        }
    }
}


public class Marketplace {
    public static void main(String[] args) {
        Product electronics = ProductFactory.createProduct("Electronics", "Smartphone", "BrandX", 299.99);
        electronics.displayDetails();
        System.out.println("Discounted Price: $" + electronics.calculateDiscount(10));

        // Create Clothing Product
        Product clothing = ProductFactory.createProduct("Clothing", "T-Shirt", "L", 19.99);
        clothing.displayDetails();
        System.out.println("Discounted Price: $" + clothing.calculateDiscount(15));

        System.out.println();

        // Create Books Product
        Product book = ProductFactory.createProduct("Books", "The Great Gatsby", "F. Scott Fitzgerald", 10.99);
        book.displayDetails();
        System.out.println("Discounted Price: $" + book.calculateDiscount(5));
    }
}