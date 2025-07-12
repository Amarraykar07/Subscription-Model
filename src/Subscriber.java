public class Subscriber {
    private int subscriberId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private String subscriptionStatus;

    public Subscriber(int subscriberId, String name, String email, String address, String phone,
                      String subscriptionStartDate, String subscriptionEndDate, String subscriptionStatus) {
        this.subscriberId = subscriberId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
        this.subscriptionStatus = subscriptionStatus;
    }

    // Getters and Setters
    public int getSubscriberId() { return subscriberId; }
    public void setSubscriberId(int subscriberId) { this.subscriberId = subscriberId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSubscriptionStartDate() { return subscriptionStartDate; }
    public void setSubscriptionStartDate(String subscriptionStartDate) { this.subscriptionStartDate = subscriptionStartDate; }

    public String getSubscriptionEndDate() { return subscriptionEndDate; }
    public void setSubscriptionEndDate(String subscriptionEndDate) { this.subscriptionEndDate = subscriptionEndDate; }

    public String getSubscriptionStatus() { return subscriptionStatus; }
    public void setSubscriptionStatus(String subscriptionStatus) { this.subscriptionStatus = subscriptionStatus; }
}
