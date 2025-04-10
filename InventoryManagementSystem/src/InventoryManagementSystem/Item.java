package InventoryManagementSystem;

class Item {
    String name;
    int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{name='" + name + "', quantity=" + quantity + '}';
    }
}
