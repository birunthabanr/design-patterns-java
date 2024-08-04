/**
 * Program Construction Lab
 * IO Operations, Serialization and Exception Handling Lab Exercise
 * Team Name: PHANTOM ORION
 * Team Members:
 * Kavienan J. - 220314M
 * Birunthaban R. - 220073V
 * Deepthika K.A.E.R. - 220107G
 * Dharmakeerthi D.H.N. - 220117L
 */

import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides utility methods for console output in a Point of Sale (POS) system.
 */
class Console {
    /**
     * Pauses the execution of the current thread for a specified amount of time.
     * @param ms The amount of time to pause in milliseconds.
     */
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a message to the console with a specified color.
     * @param message The message to print.
     * @param color The color to print the message in.
     */
    public static void print(String message, String color) {
        System.out.println(color + message + "\u001B[0m");
    }

    /**
     * Prints a warning message to the console in yellow.
     * @param message The warning message to print.
     */
    public static void printWarning(String message) {
        print(message, "\u001B[33m");
    }

    /**
     * Prints a success message to the console in green.
     * @param message The success message to print.
     */
    public static void printSuccess(String message) {
        print(message, "\u001B[32m");
    }

    /**
     * Prints an error message to the console in red.
     * @param message The error message to print.
     */
    public static void printError(String message) {
        print(message, "\u001B[31m");
    }

    /**
     * Prints an informational message to the console in blue.
     * @param message The informational message to print.
     */
    public static void printInfo(String message) {
        print(message, "\u001B[34m");
    }
}


/**
 * This class represents a custom exception in a Point of Sale (POS) system.
 * It extends Exception, which means it can be used with try-catch blocks.
 * This exception is thrown when an item code is not found in the database.
 */
class ItemCodeNotFound extends Exception {
    /**
     * Constructor for the ItemCodeNotFound class.
     * Initializes the exception with a custom message.
     * @param message The custom message for the exception.
     */
    public ItemCodeNotFound(String message) {
        super("Item code not found in the database");
    }
}


/**
 * This class represents a grocery item in a Point of Sale (POS) system.
 * It implements Serializable, which means instances of this class can be saved to a file or sent over a network.
 */
class GloceryItem implements Serializable {
    // Unique code for the item
    String item_code;
    // Name of the item
    String item_name;
    // Price per unit of the item
    double unit_price;
    // Label for the unit price (e.g., "per kg", "per liter")
    String unit_label;
    // Date when the item was manufactured
    String date_of_manufacturing;
    // Date when the item expires
    String date_of_expiry;
    // Name of the manufacturer
    String manufacturer_name;

    /**
     * Constructor for the GroceryItem class.
     * Initializes all the fields of the class with the provided values.
     */
    public GloceryItem(String item_code, String item_name, double unit_price, String unit_price_label,
            String date_of_manufacturing, String date_of_expiry, String manufacturer_name) {
        this.item_code = item_code;
        this.item_name = item_name;
        this.unit_price = unit_price;
        this.unit_label = unit_price_label;
        this.date_of_manufacturing = date_of_manufacturing;
        this.date_of_expiry = date_of_expiry;
        this.manufacturer_name = manufacturer_name;
    }

    /**
     * Calculates the total price for a given quantity of the item.
     * @param quantity The quantity of the item.
     * @return The total price.
     */
    public double getQuantityPrice(double quantity) {
        return unit_price * quantity;
    }

    /**
     * Prints the details of the item to the console.
     */
    public void printItem() {
        Console.printInfo("Item Code: " + item_code);
        Console.printInfo("Item Name: " + item_name);
        Console.printInfo("Unit Price: " + unit_price + " Rs per " + unit_label);
        Console.printInfo("Date of Manufacturing: " + date_of_manufacturing);
        Console.printInfo("Date of Expiry: " + date_of_expiry);
        Console.printInfo("Manufacturer Name: " + manufacturer_name);
    }
}


/**
 * This class represents an item in a bill in a Point of Sale (POS) system.
 * It implements Serializable, which means instances of this class can be saved to a file or sent over a network.
 */
class BillItem implements Serializable {
    // The grocery item
    final GloceryItem item;
    // The quantity of the item
    final double quantity;
    // The discount applied to the item
    final double discount;
    // The net price of the item after applying the discount
    final double net_price;

    /**
     * Constructor for the BillItem class.
     * Initializes all the fields of the class with the provided values.
     * The discount and net price are calculated based on the quantity and discount percentage.
     * @param item The grocery item.
     * @param quantity The quantity of the item.
     * @param discount_percentage The discount percentage to be applied to the item.
     */
    public BillItem(GloceryItem item, double quantity, double discount_percentage) {
        this.item = item;
        this.quantity = quantity;
        this.discount = item.getQuantityPrice(quantity) * discount_percentage / 100;
        this.net_price = item.getQuantityPrice(quantity) - discount;
    }
}


/**
 * This class represents a bill in a Point of Sale (POS) system.
 * It implements Serializable, which means instances of this class can be saved to a file or sent over a network.
 */
class Bill implements Serializable {
    // Name of the cashier who processed the bill
    final String cashier_name;
    // Name of the branch where the bill was processed
    final String branch;
    // Name of the customer
    private String customer_name;
    // List of items in the bill
    private List<BillItem> items;
    // Total discount applied to the bill
    private double total_discount;
    // Total price of the bill after applying the discount
    private double total_price;
    // Date when the bill was processed
    private String date;
    // Time when the bill was processed
    private String time;
    // Flag indicating whether the bill is completed or not
    private boolean isCompleted;

    public Bill(String cashier_name, String branch) {
        this.cashier_name = cashier_name;
        this.branch = branch;
        this.customer_name = "";
        this.items = new ArrayList<>();
        this.total_discount = 0;
        this.total_price = 0;
        this.isCompleted = false;
    }

    public void addItem(GloceryItem item, double quantity, double discount_percentage) {
        BillItem bill_item = new BillItem(item, quantity, discount_percentage);
        items.add(bill_item);
        total_discount += bill_item.discount;
        total_price += bill_item.net_price;
    }
    
    public void removeLastItem() {
        if (items.size() > 0) {
            BillItem last_item = items.get(items.size() - 1);
            total_discount -= last_item.discount;
            total_price -= last_item.net_price;
            items.remove(items.size() - 1);
        }
    }

    public void completeBill() {
        // Set the date and time of the bill
        date = new Date().toString();
        time = new Timer().toString();
        isCompleted = true;
    }

    public int getTotalItems() {
        return items.size();
    }

    public String customerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }
    
    public void printBill() {
        System.out.println("\n\n********** BILL ***********");
        Console.printInfo("Cashier Name: " + cashier_name);
        Console.printInfo("Branch: " + branch);
        Console.printInfo("Customer Name: " + customer_name);
        if (isCompleted) {
            Console.printInfo("Date: " + date);
            Console.printInfo("Time: " + time);
        } else {
            Console.printWarning("Status: Pending");
        }
        Console.printInfo("Items: ");
        if (items.size() == 0) {
            Console.printWarning("(No items in the bill)");
            System.out.println("***************************\n");
            return;
        }
        for (BillItem item : items) {
            Console.printInfo(item.item.item_name + " - (" + item.quantity + " x " + item.item.unit_price + " Rs/ " + item.item.unit_label + " ) - (Disc " + item.discount + " Rs ) => " + item.net_price + " Rs");
        }
        Console.printInfo("\nTotal: " + (total_price + total_discount) + " Rs");
        Console.printInfo("Total Discount: " + total_discount + " Rs");
        Console.printInfo("Net Total: " + total_price + " Rs");
        System.out.println("***************************\n");
    }

    public void saveBillToTextFile() {
        try {
            String file_name = "bill_" + customer_name + "_" + date + ".txt";
            FileWriter file = new FileWriter(file_name);
            file.write("********** BILL ***********\n");
            file.write("Cashier Name: " + cashier_name + "\n");
            file.write("Branch: " + branch + "\n");
            file.write("Customer Name: " + customer_name + "\n");
            file.write("Date: " + date + "\n");
            file.write("Time: " + time + "\n");
            file.write("Items: \n");
            for (BillItem item : items) {
                file.write(item.item.item_name + " - (" + item.quantity + " x " + item.item.unit_price + " Rs/ " + item.item.unit_label + " ) - (Disc " + item.discount + " Rs ) => " + item.net_price + " Rs\n");
            }
            file.write("\nTotal: " + (total_price + total_discount) + " Rs\n");
            file.write("Total Discount: " + total_discount + " Rs\n");
            file.write("Net Total: " + total_price + " Rs\n");
            file.write("***************************\n");
            file.close();
            Console.printSuccess("Bill saved to " + file_name);
        } catch (IOException e) {
            Console.printError("Error in saving bill to file");
        }
    }
}


class GloceryDatabase {

    HashMap<String, GloceryItem> items = new HashMap<String, GloceryItem>() {
        {
            put("1001", new GloceryItem("1001", "Rice", 50, "kg", "01-01-2021", "01-01-2022", "Rice Manufacturer"));
            put("1002", new GloceryItem("1002", "Wheat", 40, "kg", "01-01-2021", "01-01-2022", "Wheat Manufacturer"));
            put("1003", new GloceryItem("1003", "Sugar", 60, "kg", "01-01-2021", "01-01-2022", "Sugar Manufacturer"));
            put("1004", new GloceryItem("1004", "Salt", 20, "kg", "01-01-2021", "01-01-2022", "Salt Manufacturer"));
            put("1005", new GloceryItem("1005", "Milk", 25, "litre", "01-01-2021", "01-01-2022", "Milk Manufacturer"));
            put("1006", new GloceryItem("1006", "Bread", 30, "1 packet", "01-01-2021", "01-01-2022", "Bread Manufacturer"));
            put("1007", new GloceryItem("1007", "Butter", 100, "kg", "01-01-2021", "01-01-2022", "Butter Manufacturer"));
            put("1008", new GloceryItem("1008", "Cheese", 150, "kg", "01-01-2021", "01-01-2022", "Cheese Manufacturer"));
            put("1009", new GloceryItem("1009", "Eggs", 10, "1 packet", "01-01-2021", "01-01-2022", "Eggs Manufacturer"));
            put("1010", new GloceryItem("1010", "Chicken", 200, "kg", "01-01-2021", "01-01-2022", "Chicken Manufacturer"));
        }
    };

    public GloceryItem getGloceryItem(String item_code) throws ItemCodeNotFound {
        if (items.containsKey(item_code)) {
            return items.get(item_code);
        } else {
            throw new ItemCodeNotFound("Item code not found in the database");
        }
    }
}


public class POS {

    private Scanner scanner = new Scanner(System.in);
    final GloceryDatabase database = new GloceryDatabase();
    final String cashier_name = "John Doe";
    final String branch = "Super-Saving Supermarket";
    private List<Bill> pending_bills = new ArrayList<>();
    private String asciiArt = 
    "   ___  ____  ____  ______  __________________  ___\n" +
    "  / _ \\/ __ \\/ __/ / __/\\ \\/ / __/_  __/ __/  |/  /\n" +
    " / ___/ /_/ /\\ \\  _\\ \\   \\  /\\ \\  / / / _// /|_/ / \n" +
    "/_/   \\____/___/ /___/   /_/___/ /_/ /___/_/  /_/  \n" +
    "                                                   ";

    public static void main(String[] args) {
        POS pos = new POS();
        // load pending bills data
        pos.deserializePendingBillsData();
        pos.start();
        Console.printSuccess("Thank you for using the POS system");
        System.exit(0);
    }

    public void serializePendingBillsData() {
        // Save pending bills data to the file
        try {
            FileOutputStream file = new FileOutputStream("data.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(pending_bills);
            out.close();
            file.close();
        } catch (IOException e) {
            Console.printError("Error in saving data");
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializePendingBillsData() {
        // Load pending bills data from the file
        try {
            FileInputStream file = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            pending_bills = (ArrayList<Bill>) in.readObject();
            in.close();
            file.close();
        } catch (IOException e) {
            serializePendingBillsData();
        } catch (ClassNotFoundException e) {
            Console.printError("Error in loading data");
        }
    }

    public void start() {
        Console.printSuccess(asciiArt);
        Console.printInfo("Welcome to the Super-Saving Supermarket POS System!");
        while (true) {
            System.out.println();
            // List all pending bills
            System.out.println("\nPending Bills: ");
            printPendingBills();
            System.out.println();
            // Main menu
            Console.printInfo("Press B to Bill new Customer");
            Console.printInfo("Press E to Exit");
            if (pending_bills.size() > 0) {
                System.out.print("or Enter Pending Bill Number: ");
            } else {
                System.out.print("Enter your choice: ");
            }
            String option = scanner.nextLine().toUpperCase().trim();
            if (option.equals("B")) {
                Bill bill = new Bill(cashier_name, branch);
                pending_bills.add(bill);
                billCustomer(bill);
            } else if (option.equals("E")) {
                break;
            } else {
                // Check if the option is a pending bill number
                if (pending_bills.size() == 0) {
                    Console.printError("Invalid Option. Please try again");
                    continue;
                }
                try {
                    int bill_number = Integer.parseInt(option);
                    if (bill_number > 0 && bill_number <= pending_bills.size()) {
                        Bill bill = pending_bills.get(pending_bills.size() - bill_number);
                        billCustomer(bill);
                    } else {
                        Console.printError("Invalid Bill Number. Please try again");
                    }
                } catch (NumberFormatException e) {
                    Console.printError("Invalid Option. Please try again");
                }
            }
        }
    }

    public void printPendingBills() {
        if (pending_bills.size() == 0) {
            Console.printSuccess("(No pending bills)");
            return;
        }
        for (int i = pending_bills.size() - 1; i >= 0; i--) {
            Console.printWarning((pending_bills.size() - i) + ". Bill of " + pending_bills.get(i).customerName() + " (Items: " + pending_bills.get(i).getTotalItems() + ")");
        }
    }

    public GloceryItem getItemDetails() {
        try {
            String item_code = scanner.nextLine().trim();
            GloceryItem item = null;
            item = database.getGloceryItem(item_code);
            return item;
        } catch (ItemCodeNotFound e) {
            Console.printError(e.getMessage());
        }
        return null;
    }

    public double validateAndGetItemQuantity(GloceryItem item) {
        // Get the quantity of the item
        double quantity = 0;
        while (true) {
            System.out.print("Enter the quantity (" + item.unit_label + "): ");
            try {
                quantity = Double.parseDouble(scanner.nextLine().trim());
                if (quantity <= 0) {
                    Console.printError("Quantity should be greater than 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                Console.printError("Invalid quantity. Please enter a valid number");
            }
        }
        return quantity;
    }

    public double validateAndGetDiscount() {
        // Get the discount percentage
        double discount = 0;
        while (true) {
            System.out.print("Enter the discount percentage (0-75 %): ");
            try {
                discount = Double.parseDouble(scanner.nextLine());
                if (discount < 0 || discount > 75) {
                    Console.printError("Discount should be between 0 and 75 %");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                Console.printError("Invalid discount. Please enter a valid number");
            }
        }
        return discount;
    }

    public String validateAndGetCustomerName() {
        // Get the customer name or generate a new one
        String customer_name = "";
        while (true) {
            System.out.print("Enter the customer name (or Press Enter to skip): ");
            customer_name = scanner.nextLine().trim();
            if (customer_name.length() == 0) {
                return "Customer " + (new Date().getTime());
            } else if (customer_name.matches("[a-zA-Z ]+")) {
                return customer_name;
            } else {
                Console.printError("Invalid customer name. Please enter a valid name");
            }
        }
    }

    public void billCustomer(Bill bill) {
        if (bill.customerName().length() == 0) {
            bill.setCustomerName(validateAndGetCustomerName());
        }
        while (true) {
            if (bill.getTotalItems() > 0) {
                // Ask the user to discard last item, save to pending, or print the bill
                bill.printBill();
                Console.printInfo("D - Discard Last Item");
                Console.printInfo("S - Save to Pending and exit");
                Console.printInfo("P - Print Bill");
                System.out.print("or Press any other key to continue adding items: ");
                String option = scanner.nextLine().toUpperCase().trim();
                if (option.equals("D")) {
                    bill.removeLastItem();
                    continue;
                } else if (option.equals("S")) {
                    serializePendingBillsData();
                    break;
                } else if (option.equals("P")) {
                    bill.completeBill();
                    bill.printBill();
                    for (Bill pending_bill : pending_bills) {
                        if (pending_bill == bill) {
                            pending_bills.remove(pending_bill);
                            break;
                        }
                    }
                    Console.printSuccess("Bill printed successfully");
                    bill.saveBillToTextFile();
                    serializePendingBillsData();
                    break;
                }
            }
            // Ask the user to enter the item code
            bill.printBill();
            System.out.print("Enter the item code (1001-1010): ");
            GloceryItem item = getItemDetails();
            if (item == null) {
                Console.wait(800);
                continue;
            }
            Console.printSuccess("Item found");
            item.printItem();
            double quantity = validateAndGetItemQuantity(item);
            double discount = validateAndGetDiscount();
            Console.printSuccess("Item added to the bill: ");
            bill.addItem(item, quantity, discount);
        }
    }
}
