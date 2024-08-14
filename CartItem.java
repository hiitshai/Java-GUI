public class CartItem {
    private String itemName;
    private int quantity;
    private double totalCost;

    public CartItem(String itemName, int quantity, double totalCost) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", totalCost=" + totalCost +
                '}';
    }
}