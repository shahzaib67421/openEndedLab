package InventoryManagementSystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryManagementUI extends Application {

    private InventoryManagement inventoryManagement;

    @Override
    public void start(Stage primaryStage) {
        // Initialize Inventory Management system
        inventoryManagement = new InventoryManagement(5);

        // Layouts
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // UI Components
        Label title = new Label(inventoryManagement.nameofProject);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addButton = new Button("Add Item to Inventory");
        Button removeButton = new Button("Remove Item from Inventory");
        Button viewButton = new Button("View Inventory");
        Button restockButton = new Button("Enqueue Restocking");
        Button processRestockButton = new Button("Process Restocking");
        Button processButton = new Button("Push for Processing");
        Button processItemButton = new Button("Process Item");

        // Action Handlers
        addButton.setOnAction(e -> addItem());
        removeButton.setOnAction(e -> removeItem());
        viewButton.setOnAction(e -> viewInventory());
        restockButton.setOnAction(e -> enqueueRestock());
        processRestockButton.setOnAction(e -> processRestock());
        processButton.setOnAction(e -> pushProcessing());
        processItemButton.setOnAction(e -> processItem());

        root.getChildren().addAll(title, addButton, removeButton, viewButton, restockButton, 
                                  processRestockButton, processButton, processItemButton);

        // Scene and Stage
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle(inventoryManagement.nameofProject);
        primaryStage.show();
    }

    private void addItem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Add Item to Inventory");
        dialog.setContentText("Enter item name:");
        String name = dialog.showAndWait().orElse("");

        dialog.setContentText("Enter item quantity:");
        int quantity;
        try {
            quantity = Integer.parseInt(dialog.showAndWait().orElse("0"));
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity!");
            return;
        }

        if (!inventoryManagement.addToInventory(name, quantity)) {
            showAlert("Inventory is full!");
        }
    }

    private void removeItem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Remove Item from Inventory");
        dialog.setContentText("Enter item name:");
        String name = dialog.showAndWait().orElse("");

        if (!inventoryManagement.removeFromInventory(name)) {
            showAlert("Item not found!");
        }
    }

    private void viewInventory() {
        StringBuilder inventoryList = new StringBuilder();
        for (Item item : inventoryManagement.getInventory()) { // Use getInventory() here
            if (item != null) {
                inventoryList.append(item.toString()).append("\n");
            }
        }

        if (inventoryList.length() == 0) {
            inventoryList.append("Inventory is empty!");
        }

        showAlert(inventoryList.toString());
    }


    private void enqueueRestock() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enqueue Restocking");
        dialog.setContentText("Enter item name:");
        String name = dialog.showAndWait().orElse("");

        dialog.setContentText("Enter item quantity:");
        int quantity;
        try {
            quantity = Integer.parseInt(dialog.showAndWait().orElse("0"));
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity!");
            return;
        }

        inventoryManagement.enqueueRestock(name, quantity);
    }

    private void processRestock() {
        inventoryManagement.processRestock();
    }

    private void pushProcessing() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Push for Processing");
        dialog.setContentText("Enter item name:");
        String name = dialog.showAndWait().orElse("");

        dialog.setContentText("Enter item quantity:");
        int quantity;
        try {
            quantity = Integer.parseInt(dialog.showAndWait().orElse("0"));
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity!");
            return;
        }

        inventoryManagement.pushProcessing(name, quantity);
    }

    private void processItem() {
        inventoryManagement.processItem();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
