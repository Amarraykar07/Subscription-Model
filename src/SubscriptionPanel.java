import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SubscriptionPanel {
    private SubscriptionDAO subscriptionDAO = new SubscriptionDAO();

    private JTextField idField, subscriberIdField, platformIdField, planField, startDateField, endDateField, statusField;
    private JCheckBox renewalCheckbox;
    private DefaultTableModel tableModel;
    private JTable table;

    public JPanel getPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("📄 Subscription Details"));

        formPanel.add(new JLabel("Subscription ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Subscriber ID:"));
        subscriberIdField = new JTextField();
        formPanel.add(subscriberIdField);

        formPanel.add(new JLabel("Platform ID:"));
        platformIdField = new JTextField();
        formPanel.add(platformIdField);

        formPanel.add(new JLabel("Plan:"));
        planField = new JTextField();
        formPanel.add(planField);

        formPanel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        startDateField = new JTextField();
        formPanel.add(startDateField);

        formPanel.add(new JLabel("End Date (yyyy-mm-dd):"));
        endDateField = new JTextField();
        formPanel.add(endDateField);

        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        formPanel.add(new JLabel("Auto Renewal:"));
        renewalCheckbox = new JCheckBox("Yes");
        formPanel.add(renewalCheckbox);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Sub ID", "Plat ID", "Plan", "Start", "End", "Status", "Auto"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton insertBtn = new JButton("➕ Insert");
        JButton updateBtn = new JButton("✏️ Update");
        JButton deleteBtn = new JButton("🗑️ Delete");

        buttonPanel.add(insertBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadSubscriptions();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                subscriberIdField.setText(tableModel.getValueAt(row, 1).toString());
                platformIdField.setText(tableModel.getValueAt(row, 2).toString());
                planField.setText(tableModel.getValueAt(row, 3).toString());
                startDateField.setText(tableModel.getValueAt(row, 4).toString());
                endDateField.setText(tableModel.getValueAt(row, 5).toString());
                statusField.setText(tableModel.getValueAt(row, 6).toString());
                renewalCheckbox.setSelected(Boolean.parseBoolean(tableModel.getValueAt(row, 7).toString()));
            }
        });

        insertBtn.addActionListener(e -> {
            try {
                Subscription s = getSubscriptionFromFields();
                subscriptionDAO.insertSubscription(s);
                loadSubscriptions();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                Subscription s = getSubscriptionFromFields();
                subscriptionDAO.updateSubscription(s);
                loadSubscriptions();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                subscriptionDAO.deleteSubscription(id);
                loadSubscriptions();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        return mainPanel;
    }

    private void loadSubscriptions() {
        try {
            tableModel.setRowCount(0);
            List<Subscription> list = subscriptionDAO.getAllSubscriptions();
            for (Subscription s : list) {
                tableModel.addRow(new Object[]{
                    s.getSubscriptionId(),
                    s.getSubscriberId(),
                    s.getPlatformId(),
                    s.getSubscriptionPlan(),
                    s.getStartDate(),
                    s.getEndDate(),
                    s.getSubscriptionStatus(),
                    s.isRenewalAuto()
                });
            }
        } catch (SQLException e) {
            showError("Error loading subscriptions: " + e.getMessage());
        }
    }

    private Subscription getSubscriptionFromFields() {
        int id = Integer.parseInt(idField.getText().trim());
        int subId = Integer.parseInt(subscriberIdField.getText().trim());
        int platId = Integer.parseInt(platformIdField.getText().trim());
        String plan = planField.getText().trim();
        String start = startDateField.getText().trim();
        String end = endDateField.getText().trim();
        String status = statusField.getText().trim();
        boolean auto = renewalCheckbox.isSelected();
        return new Subscription(id, subId, platId, plan, start, end, status, auto);
    }

    private void clearFields() {
        idField.setText("");
        subscriberIdField.setText("");
        platformIdField.setText("");
        planField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        statusField.setText("");
        renewalCheckbox.setSelected(false);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "⚠️ Error", JOptionPane.ERROR_MESSAGE);
    }
}