public class Product {
    private int productId;
    private int platformId;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean subscriptionPlanApplicable;

    public Product(int productId, int platformId, String name, String description,
                   double price, String category, boolean subscriptionPlanApplicable) {
        this.productId = productId;
        this.platformId = platformId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.subscriptionPlanApplicable = subscriptionPlanApplicable;
    }

    // Getters & Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getPlatformId() { return platformId; }
    public void setPlatformId(int platformId) { this.platformId = platformId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isSubscriptionPlanApplicable() { return subscriptionPlanApplicable; }
    public void setSubscriptionPlanApplicable(boolean subscriptionPlanApplicable) { this.subscriptionPlanApplicable = subscriptionPlanApplicable; }
}
