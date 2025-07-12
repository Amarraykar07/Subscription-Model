public class Platform {
    private int platformId;
    private String platformName;
    private String platformUrl;
    private String platformDescription;

    public Platform(int platformId, String platformName, String platformUrl, String platformDescription) {
        this.platformId = platformId;
        this.platformName = platformName;
        this.platformUrl = platformUrl;
        this.platformDescription = platformDescription;
    }

    // Getters & Setters
    public int getPlatformId() { return platformId; }
    public void setPlatformId(int platformId) { this.platformId = platformId; }

    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) { this.platformName = platformName; }

    public String getPlatformUrl() { return platformUrl; }
    public void setPlatformUrl(String platformUrl) { this.platformUrl = platformUrl; }

    public String getPlatformDescription() { return platformDescription; }
    public void setPlatformDescription(String platformDescription) { this.platformDescription = platformDescription; }
}
