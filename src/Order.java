public class Order {
    private int orderId;
    private int subscriberId;
    private String orderDate;
    private double amount;
    private String status;
    private String paymentStatus;
    private String deliveryDate; // Can be null

    public Order(int orderId, int subscriberId, String orderDate, double amount,
                 String status, String paymentStatus, String deliveryDate) {
        this.orderId = orderId;
        this.subscriberId = subscriberId;
        this.orderDate = orderDate;
        this.amount = amount;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.deliveryDate = deliveryDate;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getSubscriberId() { return subscriberId; }
    public void setSubscriberId(int subscriberId) { this.subscriberId = subscriberId; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }
}
