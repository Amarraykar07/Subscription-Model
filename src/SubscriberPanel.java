import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SubscriberPanel {

    private SubscriberDAO subscriberDAO = new SubscriberDAO();

    private JTextField idField, nameField, emailField, addressField, phoneField, startDateField, endDateField, statusField;
    private DefaultTableModel tableModel;
    private JTable table;

    public JPanel getPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Subscriber Details"));

        formPanel.add(new JLabel("Subscriber ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Subscription Start Date (yyyy-mm-dd):"));
        startDateField = new JTextField();
        formPanel.add(startDateField);

        formPanel.add(new JLabel("Subscription End Date (yyyy-mm-dd):"));
        endDateField = new JTextField();
        formPanel.add(endDateField);

        formPanel.add(new JLabel("Subscription Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Address", "Phone", "Start Date", "End Date", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton insertBtn = new JButton("➕ Insert");
        JButton updateBtn = new JButton("✏️ Update");
        JButton deleteBtn = new JButton("🗑️ Delete");

        buttonPanel.add(insertBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Load existing data
        loadSubscribers();

        // Table click → load to form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                emailField.setText(tableModel.getValueAt(row, 2).toString());
                addressField.setText(tableModel.getValueAt(row, 3).toString());
                phoneField.setText(tableModel.getValueAt(row, 4).toString());
                startDateField.setText(tableModel.getValueAt(row, 5).toString());
                endDateField.setText(tableModel.getValueAt(row, 6).toString());
                statusField.setText(tableModel.getValueAt(row, 7).toString());
            }
        });

        // Insert action
        insertBtn.addActionListener(e -> {
            try {
                Subscriber s = getSubscriberFromFields();
                subscriberDAO.insertSubscriber(s);
                loadSubscribers();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        // Update action
        updateBtn.addActionListener(e -> {
            try {
                Subscriber s = getSubscriberFromFields();
                subscriberDAO.updateSubscriber(s);
                loadSubscribers();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        // Delete action
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                subscriberDAO.deleteSubscriber(id);
                loadSubscribers();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        return mainPanel;
    }

    private void loadSubscribers() {
        try {
            tableModel.setRowCount(0);
            List<Subscriber> subscribers = subscriberDAO.getAllSubscribers();
            for (Subscriber s : subscribers) {
                tableModel.addRow(new Object[]{
                        s.getSubscriberId(),
                        s.getName(),
                        s.getEmail(),
                        s.getAddress(),
                        s.getPhone(),
                        s.getSubscriptionStartDate(),
                        s.getSubscriptionEndDate(),
                        s.getSubscriptionStatus()
                });
            }
        } catch (SQLException e) {
            showError("Error loading subscribers: " + e.getMessage());
        }
    }

    private Subscriber getSubscriberFromFields() {
        int id = Integer.parseInt(idField.getText().trim());
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String start = startDateField.getText().trim();
        String end = endDateField.getText().trim();
        String status = statusField.getText().trim();
        return new Subscriber(id, name, email, address, phone, start, end, status);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        addressField.setText("");
        phoneField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        statusField.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "⚠️ Error", JOptionPane.ERROR_MESSAGE);
    }
}