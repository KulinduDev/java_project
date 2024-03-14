// subclass representing Clothing

public class Clothing extends Product {
    private String size;
    private String color;

    // constructor
    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    // getters and setters for clothing-specific attributes


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                "} " + super.toString();
    }
     public void displayDetails() {
       System.out.println("Size: " + size + "\nColor: " + color);
    }

    @Override
    public String getProductDescription() {
      return "Clothing - " +
            "Product ID: " + getProductID() +
          ", Product Name: " + getProductName() +
        ", Available Items: " + getAvailableItems() +
      ", Price: $" + getPrice() +
    ", Size: " + size +
    ", Color: " + color;
    }
}

