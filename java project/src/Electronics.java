// subclass representing Clothing

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    // constructor

    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // getters and setters for Electronic class - specific attributes
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "brand='" + brand + '\'' +
                ", warrantyPeriod=" + warrantyPeriod +
                "} " + super.toString();
    }

    @Override
    public String getProductID() {
        // Implement the method
        return super.getProductID();  // Example implementation; adjust as needed
    }

    @Override
    public String getProductName() {
        // Implement the method
        return super.getProductName();  // Example implementation; adjust as needed
    }
    public void displayDetails() {
     System.out.println("Brand: " + brand + "\nWarranty Period: " + warrantyPeriod + " months");
    }

    @Override
    public String getProductDescription() {
      return "Electronics - " +
           "Product ID: " + getProductID() +
         ", Product Name: " + getProductName() +
        ", Available Items: " + getAvailableItems() +
     ", Price: $" + getPrice() +
    ", Brand: " + brand +
    ", Warranty Period: " + warrantyPeriod + " months";
      }

}
