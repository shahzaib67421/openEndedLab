package InventoryManagementSystem;

import java.util.*;


// Class to represent individual inventory items
// Inventory Management System
class InventoryManagement {
    private Item[] inventory; // Array for storing inventory items
    private int size; // Size of the inventory
    private int currentIndex; // Tracks the current number of items in the inventory

    private Queue<Item> restockingQueue; // Queue for restocking items
    private Stack<Item> processingStack; // Stack for processing items

    // Constructor to initialize the system
    public InventoryManagement(int size) {
        this.size = size;
        this.inventory = new Item[size];
        this.currentIndex = 0;
        this.restockingQueue = new LinkedList<>();
        this.processingStack = new Stack<>();
    }

    // Add a new item to inventory using the array
    public boolean addToInventory(String name, int quantity) {
        if (currentIndex >= size) {
            System.out.println("Inventory is full!");
            return false;
        }
        inventory[currentIndex++] = new Item(name, quantity);
        System.out.println("Added to inventory: " + name);
        return true;
    }

    // Remove an item from inventory based on user input
    public boolean removeFromInventory(String name) {
        for (int i = 0; i < currentIndex; i++) {
            if (inventory[i].name.equals(name)) {
                // Shift items to fill the gap
                for (int j = i; j < currentIndex - 1; j++) {
                    inventory[j] = inventory[j + 1];
                }
                inventory[--currentIndex] = null;
                System.out.println("Removed from inventory: " + name);
                return true;
            }
        }
        System.out.println("Item not found in inventory!");
        return false;
    }

    // View inventory (array)
    public void viewInventory() {
        System.out.println("Inventory:");
        for (int i = 0; i < currentIndex; i++) {
            System.out.println(inventory[i]);
        }
    }

    // Add an item to the restocking queue (FIFO)
    public void enqueueRestock(String name, int quantity) {
        restockingQueue.add(new Item(name, quantity));
        System.out.println("Enqueued for restocking: " + name);
    }

    // Process restocking (FIFO)
    public void processRestock() {
        if (restockingQueue.isEmpty()) {
            System.out.println("No items to restock.");
            return;
        }
        Item item = restockingQueue.poll();
        System.out.println("Restocked item: " + item);
        addToInventory(item.name, item.quantity); // Add to inventory
    }

    // Add an item to the processing stack (LIFO)
    public void pushProcessing(String name, int quantity) {
        processingStack.push(new Item(name, quantity));
        System.out.println("Pushed for processing: " + name);
    }

    // Process items (LIFO)
    public void processItem() {
        if (processingStack.isEmpty()) {
            System.out.println("No items to process.");
            return;
        }
        Item item = processingStack.pop();
        System.out.println("Processed item: " + item);
    }

   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManagement ims = new InventoryManagement(5);

        while (true) {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. Add to Inventory");
            System.out.println("2. Remove from Inventory");
            System.out.println("3. View Inventory");
            System.out.println("4. Enqueue for Restocking");
            System.out.println("5. Process Restocking");
            System.out.println("6. Push for Processing");
            System.out.println("7. Process Item");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String addName = scanner.nextLine();
                    System.out.print("Enter item quantity: ");
                    int addQuantity = scanner.nextInt();
                    ims.addToInventory(addName, addQuantity);
                    break;

                case 2:
                    System.out.print("Enter item name to remove: ");
                    String removeName = scanner.nextLine();
                    ims.removeFromInventory(removeName);
                    break;

                case 3:
                    ims.viewInventory();
                    break;

                case 4:
                    System.out.print("Enter item name: ");
                    String restockName = scanner.nextLine();
                    System.out.print("Enter item quantity: ");
                    int restockQuantity = scanner.nextInt();
                    ims.enqueueRestock(restockName, restockQuantity);
                    break;

                case 5:
                    ims.processRestock();
                    break;

                case 6:
                    System.out.print("Enter item name: ");
                    String processName = scanner.nextLine();
                    System.out.print("Enter item quantity: ");
                    int processQuantity = scanner.nextInt();
                    ims.pushProcessing(processName, processQuantity);
                    break;

                case 7:
                    ims.processItem();
                    break;

                case 8:
                    System.out.println("Exiting Inventory Management...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
