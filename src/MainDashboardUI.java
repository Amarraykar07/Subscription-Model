
import javax.swing.*;

public class MainDashboardUI extends JFrame {

    public MainDashboardUI() {
        setTitle("🧠 Subscription Management Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // 🌐 Platform Tab
        PlatformPanel platformPanel = new PlatformPanel();
        tabbedPane.addTab("🌐 Platform", platformPanel.getPanel());

        // 👥 Subscriber Tab
        SubscriberPanel subscriberPanel = new SubscriberPanel();
        tabbedPane.addTab("👥 Subscribers", subscriberPanel.getPanel());

        // 📦 Orders Tab
        OrderPanel orderPanel = new OrderPanel();
        tabbedPane.addTab("📦 Orders", orderPanel.getPanel());

        // 📄 Subscription Tab
        SubscriptionPanel subscriptionPanel = new SubscriptionPanel();
        tabbedPane.addTab("📄 Subscriptions", subscriptionPanel.getPanel());

        // 🛒 Product Tab
        ProductPanel productPanel = new ProductPanel();
        tabbedPane.addTab("🛒 Products", productPanel.getPanel());

        // 💳 Payment Tab
        PaymentPanel paymentPanel = new PaymentPanel();
        tabbedPane.addTab("💳 Payments", paymentPanel.getPanel());

        // 📊 Subscriber Report Tab
        SubscriberFullPanel reportPanel = new SubscriberFullPanel();
        tabbedPane.addTab("📊 Subscriber Report", reportPanel.getPanel());

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainDashboardUI::new);
    }
}
