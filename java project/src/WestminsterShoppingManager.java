import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class WestminsterShoppingManager implements ShoppingManager {

    private final Scanner scanner = new Scanner(System.in);
    public final ArrayList<Product> productList = new ArrayList<>();

    @Override
    public void addNewProduct() {
        if (productList.size() >= 50) {
            System.out.println("Cannot add more products. Maximum limit reached.");

        }
        System.out.println("Select the type of product to add:");
        System.out.println("1. Clothing");
        System.out.println("2. Electronics");

        int productTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (productTypeChoice) {
            case 1:
                addClothing();
                break;
            case 2:
                addElectronics();
                break;
            default:
                System.out.println("Invalid product type choice.");
        }



    }



    private void addClothing() {
        Clothing clothing = new Clothing("4rt", "ball", 78, 45.00, "m", "red");

        // Get common attributes
        System.out.print("Enter Product ID ");
        clothing.setProductID(validateStringInput());

        System.out.print("Enter product name ");
        clothing.setProductName(validateStringOnlyInput());

        System.out.print("Enter available Items ");
        clothing.setAvailableItems(validateIntInput());
        // Consume the newline character

        System.out.print("Enter Price ");
        clothing.setPrice(validateDoubleInput());
        // Consume the newline character

        // Get specific attributes for Clothing
        System.out.print("Enter size ");
        clothing.setSize(validateStringOnlyInput());

        System.out.print("Enter the color ");
        clothing.setColor(validateStringOnlyInput());

        // Add the clothing object to the product list
        productList.add(clothing);
        System.out.println("Clothing item added successfully. ");
    }

    private void addElectronics() {
        Electronics electronics = new Electronics("3e", "bosh", 3, 45.78, "samsung", 2);

        // Get common attributes
        System.out.print("Enter Product ID ");
        electronics.setProductID(validateStringInput());

        System.out.print("Enter Product name ");
        electronics.setProductName(validateStringOnlyInput());

        System.out.print("Enter available items ");
        electronics.setAvailableItems(validateIntInput());
        // Consume the newline character

        System.out.print("Enter price: ");
        electronics.setPrice(validateDoubleInput());
        // Consume the newline character

        // Get specific attributes for Electronics
        System.out.print("Enter brand: ");
        electronics.setBrand(validateStringOnlyInput());

        System.out.print("Enter warranty period: ");
        electronics.setWarrantyPeriod(validateIntInput());
        // Consume the newline character

        // Add the electronics object to the product list
        productList.add(electronics);
        System.out.println("Electronics added successfully.");
    }

    private String validateStringInput() {
        String input = scanner.nextLine();
        while (input.isEmpty() || !input.matches("^[a-zA-Z0-9]+$")) {
            System.out.println("Error:  Please enter a valid input.");
            input = scanner.nextLine();
        }
        return input;
    }

    private String validateStringOnlyInput() {
        String input = scanner.nextLine();
        while (input.isEmpty() || !input.matches("^[a-zA-Z]+$")) {
            System.out.println("Error:  Please enter a valid input.");
            input = scanner.nextLine();
        }
        return input;
    }


    private int validateIntInput() {
        int input = -1;
        while (input == -1)  {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid input.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        scanner.nextLine(); // Consume the newline character
        return input;
    }

    private double validateDoubleInput() {
        double input = -1.0;
        while (input == -1.0) {
            try {
                input = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid input.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        scanner.nextLine(); // Consume the newline character
        return input;
    }

    @Override
    public void deleteProduct() {
        System.out.println("Enter the product ID to delete: ");
        String productIdToDelete = scanner.nextLine();

        //variables to store information about the deleted product
        String deletedProductType = null;
        String deletedProductInfo = null;

        for (Product product : productList) {
            if (product.getProductID().equals(productIdToDelete)) {
                // storing info about the deleted product
                deletedProductType = (product instanceof Clothing) ? "Clothing" : "Electronics";
                deletedProductInfo = product.toString();

                // remove the product from the product list
                productList.remove(product);
                System.out.println("Product deleted successfully.");
                System.out.println("                              ");

                break; // Exit the loop once the product is found and deleted
            }
        }
        // display information about the deleted product and the total number of products left
        if(deletedProductInfo != null){
            System.out.println("Deleted product information");
            System.out.println("                            ");
            System.out.println("Type : " + deletedProductType);
            System.out.println("Details : " + deletedProductInfo);

        }else {
            System.out.println("Product not found.");
        }
        System.out.println("Total number of product left : " + productList.size());


    }




    @Override
    public void printProductList() {
        System.out.println(" ---------- Product List ----------");
        System.out.println("                                   ");
        for (Product product : productList) {
            System.out.println(product);
        }
    }



    @Override
    public void saveProductsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
            outputStream.writeObject(productList);
            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving products to file.");
        }
    }

    public void loadProductsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("products.dat"))) {
            Object obj = inputStream.readObject();
            if (obj instanceof ArrayList) {
                productList.addAll((ArrayList<Product>) obj);
                System.out.println("Products loaded from the saved file successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions if the file is not found or cannot be read
            System.out.println(" Starting with an empty product list.");
        }
    }

    private int getUserChoice(){
        int choice = -1;
        try{
            System.out.print("\n Enter your choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Error :  enter a valid integer choice");
            scanner.nextLine();
        }
        return choice;
    }
    public void startConsoleMenu() {

        loadProductsFromFile();


        while (true) {
            System.out.println("\n ---------- Console Menu ----------");
            System.out.println("                                     ");
            System.out.println(" 1. Add a New Product ");
            System.out.println(" 2. Delete a Product ");
            System.out.println(" 3. Print the List of the Products ");
            System.out.println(" 4. Save in a File");
            System.out.println(" 5. Exit ");
            System.out.print("                                   ");

            int choice = getUserChoice();


            switch (choice) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    printProductList();
                    break;
                case 4:
                    saveProductsToFile();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }
        }
    }

    public static void main(String[] args) {
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        westminsterShoppingManager.startConsoleMenu();
    }


}

