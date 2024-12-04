import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;


public class MyFrame extends JFrame{

    JComboBox<String> productTypeComboBox;
    JTable productTable;
    DefaultTableModel tableModel;
    JTextArea productDetailsTextArea;
    JButton addToCartButton;
    JButton viewCartButton;

    List<Product> productList;
    ShoppingCart shoppingCart;

    JTable cartTable;

    DefaultTableModel cartTableModel;

    Map<String, CustomerInfo> customerInfoMap;

    public MyFrame (String title) throws HeadlessException{
        super(title);


        productList = new ArrayList<>();
        shoppingCart  = new ShoppingCart();

        customerInfoMap = new HashMap<>();

        // initializing components

        productTypeComboBox  = new JComboBox<>(new  String[]{"All", "Electronics", "Clothing"});
        productTable = new JTable();
        tableModel = new DefaultTableModel(new String[]{" Product ID "," Name "," Category ", " Price(€) "," Info ",},0);
        productTable.setModel(tableModel);


        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                Product selectedProduct = productList.get(selectedRow);
                updateProductDetailsPanel(selectedProduct);
            }
        });

        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton(" Add to shopping cart ");
        viewCartButton = new JButton(" Shopping cart ");
        shoppingCart = new ShoppingCart();

        cartTable = new JTable();
        cartTableModel = new DefaultTableModel(new String[]{"Product", "Quantity", "Price"}, 0);
        cartTable.setModel(cartTableModel);


        // setting the layout

        setLayout(new BorderLayout());

        // adding components to the frame

        add(createTopPanel(),BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);


        // setting action listeners

        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable();
                productDetailsTextArea.setText("");  // Clearing product details when changing product type
            }
        });


        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    Product selectedProduct = productList.get(selectedRow);
                    int quantityToAdd = 1;

                    if (selectedProduct.getAvailableItems() >= quantityToAdd) {
                        selectedProduct.setAvailableItems(selectedProduct.getAvailableItems() - quantityToAdd);

                        shoppingCart.addProduct(selectedProduct, quantityToAdd);
                        refreshCartButton();
                        updateCartTable();  // Updating the cart table when a product is added
                        updateProductTable();
                        updateProductDetailsPanel(selectedProduct);
                        JOptionPane.showMessageDialog(MyFrame.this, "Product added to the cart.");
                    } else {
                        JOptionPane.showMessageDialog(MyFrame.this, "Out of stock.");
                    }
                }
            }
        });



        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCartTable();
                displayShoppingCart();

            }
        });


    }

    private void updateCartTable() {
        SwingUtilities.invokeLater(() -> {
            cartTableModel.setRowCount(0);

            for (Map.Entry<Product, Integer> entry : shoppingCart.getCartItems().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double price = product.getPrice() * quantity;

                String productName = product.getProductName();
                String productID = product.getProductID();
                String productDetails;

                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    productDetails = clothing.getSize() + ", " + clothing.getColor();
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    productDetails = electronics.getBrand() + ", " + electronics.getWarrantyPeriod() + " weeks warranty";
                } else {
                    productDetails = "";
                }

                cartTableModel.addRow(new Object[]{productID + " / "+  productName + " / " +  productDetails, quantity, price});


// changes made

            }
        });
    }


    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());


        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(new JLabel(" Select product category "));
        centerPanel.add(productTypeComboBox);
        topPanel.add(centerPanel, BorderLayout.CENTER);

        // Add the 'Shopping Cart' button to the right (east)
        topPanel.add(viewCartButton, BorderLayout.EAST);

        return topPanel;
    }


    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(productTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the product details text area below the table
        centerPanel.add(productDetailsTextArea, BorderLayout.SOUTH);

        return centerPanel;
    }


    private JPanel createBottomPanel(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(addToCartButton);


        return bottomPanel;
    }

    private void refreshCartButton(){
        viewCartButton.setText("View cart(" + shoppingCart.getProductCount() + ")");

    }

    private void displayShoppingCart() {
        // Creating a new JFrame for the shopping cart details

        JFrame cartDetailsFrame = new JFrame("Shopping Cart");
        cartDetailsFrame.setLayout(new BorderLayout());

        // Adding the shopping cart table to the frame

        cartDetailsFrame.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Creating a panel for discount and total labels

        JPanel labelsPanel = new JPanel(new GridLayout(4, 1));

        // Calculating discounts and final total

        double total = shoppingCart.calculateTotalCost();
        double firstPurchaseDiscount = 0;
        double categoryDiscount = 0;

        if (!shoppingCart.isFirstPurchase()) {
            firstPurchaseDiscount = total * 0.1; // 10% discount for the first purchase
        }

        if (shoppingCart.getProductCount() >= 3) {
            categoryDiscount = total * 0.2; // 20% discount for buying at least three products from the same category
        }

        double finalTotal = total - firstPurchaseDiscount - categoryDiscount;

        // Add discount and final total information to the labels panel        " + String.format("%.2f", categoryDiscount)));
        labelsPanel.add(new JLabel("Total:                                                                                                                                                              " + String.format("%.2f €", total)));
        labelsPanel.add(new JLabel("First Purchase Discount (10%):                                                                                                               " + String.format("%.2f €", firstPurchaseDiscount)));
        labelsPanel.add(new JLabel("Three items in the same category discount (20%):                                                                            " + String.format("%.2f €", categoryDiscount)));
        labelsPanel.add(new JLabel("Final Total:                                                                                                                                                     " + String.format("%.2f €", finalTotal)));

        // Adding the labels panel to the frame

        cartDetailsFrame.add(labelsPanel, BorderLayout.SOUTH);

        cartDetailsFrame.setSize(750, 450);
        cartDetailsFrame.setVisible(true);

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" 1. Open console menu ");
        System.out.println(" 2. Open GUI");
        System.out.print("Select an option : ");
        int choice = scanner.nextInt();



        if (choice == 1) {
            WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
            westminsterShoppingManager.startConsoleMenu();
        } else if (choice == 2) {


            MyFrame frame = new MyFrame("Westminster Shopping Centre");
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.updateProductTable();


            frame.productList.add(new Electronics("e123","speaker",23,45.00,"bose",12 ));
            frame.productList.add(new Clothing("23we","test",2,45.78,"m","red"));
            frame.productList.add(new Clothing("245tr","shirt",34,56.78,"s","blue"));
            frame.productList.add(new Clothing("245t3","sheet",34,56.70,"l","blue"));
            frame.productList.add(new Electronics("swe456","guitar",1,45.90,"yamaha",45));

            frame.updateProductTable();

            // Adding sample clients and their purchase history
            frame.customerInfoMap.put("customer 1",new CustomerInfo());
            frame.customerInfoMap.put("customer 2",new CustomerInfo());

            frame.customerInfoMap.get("customer 1").addPurchase(frame.shoppingCart);
            frame.customerInfoMap.get("customer 2").addPurchase(frame.shoppingCart);



        } else {
            System.out.println("Invalid choice");
        }


    }

    private void updateProductTable() {
        tableModel.setRowCount(0);

        // sorting the product list alphabetically by productID
        Collections.sort(productList, Comparator.comparing(Product::getProductID));

        for (Product product : productList) {
            boolean isReducedAvailability = product.getAvailableItems() < 3;

            if ((productTypeComboBox.getSelectedItem().equals("All")
                    || (productTypeComboBox.getSelectedItem().equals("Electronics") && product instanceof Electronics)
                    || (productTypeComboBox.getSelectedItem().equals("Clothing") && product instanceof Clothing))) {

                // Adding the product type to the row data
                String productType = (product instanceof Electronics) ? "Electronics" : "Clothing";

                String info;
                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    info = clothing.getSize() + ",  " + clothing.getColor();
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    info = electronics.getBrand() + ", " + electronics.getWarrantyPeriod();
                } else {
                    info = "";
                }

                // Adding the row to the table model
                if (isReducedAvailability) {
                    // If availability is less than 3, set the row to red
                    tableModel.addRow(new Object[]{
                            "<html><font color='red'>" + product.getProductID() + "</font></html>",
                            "<html><font color='red'>" + product.getProductName() + "</font></html>",
                            "<html><font color='red'>" + productType + "</font></html>",
                            "<html><font color='red'>" + product.getPrice() + "</font></html>",
                            "<html><font color='red'>" + info + "</font></html>"
                    });
                } else {
                    // Normal row without red highlighting
                    tableModel.addRow(new Object[]{product.getProductID(), product.getProductName(), productType, product.getPrice(), info});
                }
            }
        }
    }



    private void updateProductDetailsPanel(Product product) {

        productDetailsTextArea.setText("");

        // Additional code to append the product details under the product table
        productDetailsTextArea.append("\n\nSelected product -  details \n");
        productDetailsTextArea.append("Product ID: " + product.getProductID() + "\n");
        productDetailsTextArea.append("Category: " + (product instanceof Electronics ? "Electronics" : "Clothing") + "\n");
        productDetailsTextArea.append("Name: " + product.getProductName() + "\n");


        // Adding specific information based on the product type

        if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            productDetailsTextArea.append("Size: " + clothing.getSize() + "\n");
            productDetailsTextArea.append("Color: " + clothing.getColor() + "\n");
        } else if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            productDetailsTextArea.append("Brand: " + electronics.getBrand() + "\n");
            productDetailsTextArea.append("Warranty Period: " + electronics.getWarrantyPeriod() + " Weeks warranty\n");
        }
        productDetailsTextArea.append((" Available items : " + product.getAvailableItems() + "\n"));

    }

    private static class CustomerInfo {
        private boolean firstPurchase;
        private List<ShoppingCart> purchaseHistory;

        public CustomerInfo() {
            this.firstPurchase = true;
            this.purchaseHistory = new ArrayList<>();
        }

        public void addPurchase(ShoppingCart shoppingCart) {
            if (firstPurchase) {
                shoppingCart.applyFirstPurchaseDiscount();
                firstPurchase = false;
            }
            purchaseHistory.add(shoppingCart);
        }
    }


}