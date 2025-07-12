public class Subscription {
    private int subscriptionId;
    private int subscriberId;
    private int platformId;
    private String subscriptionPlan;
    private String startDate;
    private String endDate;
    private String subscriptionStatus;
    private boolean renewalAuto;

    public Subscription(int subscriptionId, int subscriberId, int platformId,
                        String subscriptionPlan, String startDate, String endDate,
                        String subscriptionStatus, boolean renewalAuto) {
        this.subscriptionId = subscriptionId;
        this.subscriberId = subscriberId;
        this.platformId = platformId;
        this.subscriptionPlan = subscriptionPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subscriptionStatus = subscriptionStatus;
        this.renewalAuto = renewalAuto;
    }

    // Getters & Setters
    public int getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(int subscriptionId) { this.subscriptionId = subscriptionId; }

    public int getSubscriberId() { return subscriberId; }
    public void setSubscriberId(int subscriberId) { this.subscriberId = subscriberId; }

    public int getPlatformId() { return platformId; }
    public void setPlatformId(int platformId) { this.platformId = platformId; }

    public String getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(String subscriptionPlan) { this.subscriptionPlan = subscriptionPlan; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getSubscriptionStatus() { return subscriptionStatus; }
    public void setSubscriptionStatus(String subscriptionStatus) { this.subscriptionStatus = subscriptionStatus; }

    public boolean isRenewalAuto() { return renewalAuto; }
    public void setRenewalAuto(boolean renewalAuto) { this.renewalAuto = renewalAuto; }
}
