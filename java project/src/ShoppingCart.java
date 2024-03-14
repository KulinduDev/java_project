import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<Product, Integer> cartItems;
    private boolean isFirstPurchase;

    private double totalCost;

    public ShoppingCart() {
        this.cartItems = new HashMap<>();
        this.isFirstPurchase = true;

        this.totalCost = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        if (cartItems.containsKey(product)) {
            int currentQuantity = cartItems.get(product);
            cartItems.put(product, currentQuantity + quantity);
        } else {
            cartItems.put(product, quantity);
        }
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public boolean isFirstPurchase() {
        return isFirstPurchase && cartItems.isEmpty();
    }

    public void setFirstPurchase(boolean isFirstPurchase) {
        this.isFirstPurchase = isFirstPurchase;
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }

    public int getProductCount() {
        int count = 0;
        for (int quantity : cartItems.values()) {
            count += quantity;
        }
        return count;
    }

    public void applyFirstPurchaseDiscount() {
        double discountPercentage = 0.10;
        double discount = calculateTotalCost() * discountPercentage;
        reduceTotalCost(discount);
    }
    private void reduceTotalCost(double discount) {
        // Ensure the discount doesn't exceed the total cost
        if (discount > totalCost) {
            totalCost = 0.0;
        } else {
            totalCost -= discount;
        }
    }
}
