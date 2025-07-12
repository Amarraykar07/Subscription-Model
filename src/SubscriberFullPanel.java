import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SubscriberFullPanel {

    private SubscriberDAO subscriberDAO = new SubscriberDAO();
    private SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    private JTextField idInputField;
    private JButton loadButton;

    private JTextField idField, nameField, emailField, addressField, phoneField, startDateField, endDateField, statusField;

    private DefaultTableModel subscriptionModel = new DefaultTableModel(new String[]{"ID", "Plan", "Platform", "Start", "End", "Status", "Auto Renew"}, 0);
    private JTable subscriptionTable = new JTable(subscriptionModel);

    private DefaultTableModel orderModel = new DefaultTableModel(new String[]{"ID", "Date", "Amount", "Status", "Payment", "Delivery"}, 0);
    private JTable orderTable = new JTable(orderModel);

    private DefaultTableModel paymentModel = new DefaultTableModel(new String[]{"ID", "Order ID", "Date", "Status", "Method", "Amount"}, 0);
    private JTable paymentTable = new JTable(paymentModel);

    public JPanel getPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 💬 Input Panel for ID
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🔍 Search Subscriber"));

        idInputField = new JTextField(10);
        loadButton = new JButton("Load Data");

        inputPanel.add(new JLabel("Enter Subscriber ID:"));
        inputPanel.add(idInputField);
        inputPanel.add(loadButton);

        // 📋 Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // 🧾 Subscriber info fields
        JPanel subscriberPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        subscriberPanel.setBorder(BorderFactory.createTitledBorder("👤 Subscriber Info"));

        idField = new JTextField(); nameField = new JTextField();
        emailField = new JTextField(); addressField = new JTextField();
        phoneField = new JTextField(); startDateField = new JTextField();
        endDateField = new JTextField(); statusField = new JTextField();

        subscriberPanel.add(new JLabel("ID:")); subscriberPanel.add(idField);
        subscriberPanel.add(new JLabel("Name:")); subscriberPanel.add(nameField);
        subscriberPanel.add(new JLabel("Email:")); subscriberPanel.add(emailField);
        subscriberPanel.add(new JLabel("Address:")); subscriberPanel.add(addressField);
        subscriberPanel.add(new JLabel("Phone:")); subscriberPanel.add(phoneField);
        subscriberPanel.add(new JLabel("Start Date:")); subscriberPanel.add(startDateField);
        subscriberPanel.add(new JLabel("End Date:")); subscriberPanel.add(endDateField);
        subscriberPanel.add(new JLabel("Status:")); subscriberPanel.add(statusField);

        // Add all into content
        contentPanel.add(subscriberPanel);
        contentPanel.add(wrapInTitledPanel(subscriptionTable, "📦 Subscriptions"));
        contentPanel.add(wrapInTitledPanel(orderTable, "📬 Orders"));
        contentPanel.add(wrapInTitledPanel(paymentTable, "💰 Payments"));

        // Scrollable area
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 🔘 Button action
        loadButton.addActionListener((ActionEvent e) -> {
            String input = idInputField.getText();
            if (input.isEmpty() || !input.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "⚠️ Please enter a valid numeric Subscriber ID.");
                return;
            }
            loadFullData(Integer.parseInt(input));
        });

        return mainPanel;
    }

    private JPanel wrapInTitledPanel(JTable table, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(800, 200));
        return panel;
    }

    private void loadFullData(int subscriberId) {
        try {
            Subscriber s = subscriberDAO.getSubscriberById(subscriberId);
            if (s == null) {
                JOptionPane.showMessageDialog(null, "❌ Subscriber not found!");
                return;
            }

            idField.setText(String.valueOf(s.getSubscriberId()));
            nameField.setText(s.getName());
            emailField.setText(s.getEmail());
            addressField.setText(s.getAddress());
            phoneField.setText(s.getPhone());
            startDateField.setText(s.getSubscriptionStartDate());
            endDateField.setText(s.getSubscriptionEndDate());
            statusField.setText(s.getSubscriptionStatus());

            // Subscriptions
            subscriptionModel.setRowCount(0);
            for (Subscription sub : subscriptionDAO.getSubscriptionsBySubscriberId(subscriberId)) {
                subscriptionModel.addRow(new Object[]{
                        sub.getSubscriptionId(),
                        sub.getSubscriptionPlan(),
                        sub.getPlatformId(),
                        sub.getStartDate(),
                        sub.getEndDate(),
                        sub.getSubscriptionStatus(),
                        sub.isRenewalAuto()
                });
            }

            // Orders
            orderModel.setRowCount(0);
            for (Order order : orderDAO.getOrdersBySubscriberId(subscriberId)) {
                orderModel.addRow(new Object[]{
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getAmount(),
                        order.getStatus(),
                        order.getPaymentStatus(),
                        order.getDeliveryDate()
                });
            }

            // Payments
            paymentModel.setRowCount(0);
            for (Payment payment : paymentDAO.getPaymentsBySubscriberId(subscriberId)) {
                paymentModel.addRow(new Object[]{
                        payment.getPaymentId(),
                        payment.getOrderId(),
                        payment.getPaymentDate(),
                        payment.getPaymentStatus(),
                        payment.getPaymentMethod(),
                        payment.getPaymentAmount()
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage());
        }
    }
}