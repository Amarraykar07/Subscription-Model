import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductPanel {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, platformIdField, nameField, descriptionField, priceField, categoryField;
    private JCheckBox planApplicableBox;
    private ProductDAO dao = new ProductDAO();

    public ProductPanel() {
        panel = new JPanel(new BorderLayout());

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Platform ID", "Name", "Desc", "Price", "Category", "Plan Applicable"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Form inputs
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));

        idField = new JTextField();
        platformIdField = new JTextField();
        nameField = new JTextField();
        descriptionField = new JTextField();
        priceField = new JTextField();
        categoryField = new JTextField();
        planApplicableBox = new JCheckBox("Applicable?");

        formPanel.add(new JLabel("Product ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Platform ID:")); formPanel.add(platformIdField);
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Description:")); formPanel.add(descriptionField);
        formPanel.add(new JLabel("Price:")); formPanel.add(priceField);
        formPanel.add(new JLabel("Category:")); formPanel.add(categoryField);
        formPanel.add(new JLabel("Plan Applicable:")); formPanel.add(planApplicableBox);

        panel.add(formPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        refreshTable();

        // Action listeners
        addBtn.addActionListener(e -> {
            try {
                Product p = getProductFromForm();
                dao.insertProduct(p);
                refreshTable();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                Product p = getProductFromForm();
                dao.updateProduct(p);
                refreshTable();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                dao.deleteProduct(id);
                refreshTable();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        refreshBtn.addActionListener(e -> refreshTable());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idField.setText(model.getValueAt(row, 0).toString());
                    platformIdField.setText(model.getValueAt(row, 1).toString());
                    nameField.setText(model.getValueAt(row, 2).toString());
                    descriptionField.setText(model.getValueAt(row, 3).toString());
                    priceField.setText(model.getValueAt(row, 4).toString());
                    categoryField.setText(model.getValueAt(row, 5).toString());
                    planApplicableBox.setSelected(Boolean.parseBoolean(model.getValueAt(row, 6).toString()));
                }
            }
        });
    }

    private Product getProductFromForm() {
        return new Product(
                Integer.parseInt(idField.getText()),
                Integer.parseInt(platformIdField.getText()),
                nameField.getText(),
                descriptionField.getText(),
                Double.parseDouble(priceField.getText()),
                categoryField.getText(),
                planApplicableBox.isSelected()
        );
    }

    private void refreshTable() {
        try {
            List<Product> products = dao.getAllProducts();
            model.setRowCount(0);
            for (Product p : products) {
                model.addRow(new Object[]{
                        p.getProductId(),
                        p.getPlatformId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getCategory(),
                        p.isSubscriptionPlanApplicable()
                });
            }
        } catch (SQLException e) {
            showError(e);
        }
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(panel, e.getMessage(), "❌ Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel() {
        return panel;
    }
}
