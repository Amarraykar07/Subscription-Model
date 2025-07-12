import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class OrderPanel {

    private OrderDAO orderDAO = new OrderDAO();

    private JTextField orderIdField, subscriberIdField, orderDateField, amountField, statusField, paymentStatusField, deliveryDateField;
    private DefaultTableModel tableModel;
    private JTable table;

    public JPanel getPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 🔧 Form Panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("📦 Order Details"));

        formPanel.add(new JLabel("Order ID:"));
        orderIdField = new JTextField();
        formPanel.add(orderIdField);

        formPanel.add(new JLabel("Subscriber ID:"));
        subscriberIdField = new JTextField();
        formPanel.add(subscriberIdField);

        formPanel.add(new JLabel("Order Date (yyyy-mm-dd):"));
        orderDateField = new JTextField();
        formPanel.add(orderDateField);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        formPanel.add(new JLabel("Payment Status:"));
        paymentStatusField = new JTextField();
        formPanel.add(paymentStatusField);

        formPanel.add(new JLabel("Delivery Date (yyyy-mm-dd):"));
        deliveryDateField = new JTextField();
        formPanel.add(deliveryDateField);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // 📋 Table Panel
        tableModel = new DefaultTableModel(new String[]{
                "Order ID", "Subscriber ID", "Order Date", "Amount", "Status", "Payment Status", "Delivery Date"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 🎮 Button Panel
        JPanel buttonPanel = new JPanel();
        JButton insertBtn = new JButton("➕ Insert");
        JButton updateBtn = new JButton("✏️ Update");
        JButton deleteBtn = new JButton("🗑️ Delete");

        buttonPanel.add(insertBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 🔁 Load existing orders
        loadOrders();

        // 🖱️ Table row click → Load to form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                orderIdField.setText(tableModel.getValueAt(row, 0).toString());
                subscriberIdField.setText(tableModel.getValueAt(row, 1).toString());
                orderDateField.setText(tableModel.getValueAt(row, 2).toString());
                amountField.setText(tableModel.getValueAt(row, 3).toString());
                statusField.setText(tableModel.getValueAt(row, 4).toString());
                paymentStatusField.setText(tableModel.getValueAt(row, 5).toString());
                deliveryDateField.setText(tableModel.getValueAt(row, 6).toString());
            }
        });

        // ➕ Insert button action
        insertBtn.addActionListener(e -> {
            try {
                Order order = getOrderFromFields();
                orderDAO.insertOrder(order);
                loadOrders();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        // ✏️ Update button action
        updateBtn.addActionListener(e -> {
            try {
                Order order = getOrderFromFields();
                orderDAO.updateOrder(order);
                loadOrders();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        // 🗑️ Delete button action
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(orderIdField.getText());
                orderDAO.deleteOrder(id);
                loadOrders();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        return mainPanel;
    }

    private void loadOrders() {
        try {
            tableModel.setRowCount(0);
            List<Order> orders = orderDAO.getAllOrders();
            for (Order o : orders) {
                tableModel.addRow(new Object[]{
                        o.getOrderId(),
                        o.getSubscriberId(),
                        o.getOrderDate(),
                        o.getAmount(),
                        o.getStatus(),
                        o.getPaymentStatus(),
                        o.getDeliveryDate()
                });
            }
        } catch (SQLException e) {
            showError("Error loading orders: " + e.getMessage());
        }
    }

    private Order getOrderFromFields() {
        int orderId = Integer.parseInt(orderIdField.getText().trim());
        int subscriberId = Integer.parseInt(subscriberIdField.getText().trim());
        String orderDate = orderDateField.getText().trim();
        double amount = Double.parseDouble(amountField.getText().trim());
        String status = statusField.getText().trim();
        String paymentStatus = paymentStatusField.getText().trim();
        String deliveryDate = deliveryDateField.getText().trim();

        return new Order(orderId, subscriberId, orderDate, amount, status, paymentStatus, deliveryDate);
    }

    private void clearFields() {
        orderIdField.setText("");
        subscriberIdField.setText("");
        orderDateField.setText("");
        amountField.setText("");
        statusField.setText("");
        paymentStatusField.setText("");
        deliveryDateField.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "⚠️ Error", JOptionPane.ERROR_MESSAGE);
    }
}
