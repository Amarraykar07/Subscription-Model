import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class PlatformPanel {

    private PlatformDAO platformDAO = new PlatformDAO();

    private JTextField idField, nameField, urlField, descField;
    private DefaultTableModel tableModel;
    private JTable table;

    public JPanel getPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Platform Details"));

        formPanel.add(new JLabel("Platform ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("URL:"));
        urlField = new JTextField();
        formPanel.add(urlField);

        formPanel.add(new JLabel("Description:"));
        descField = new JTextField();
        formPanel.add(descField);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "URL", "Description"}, 0);
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

        // Load existing platforms
        loadPlatforms();

        // Table click → load to form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                urlField.setText(tableModel.getValueAt(row, 2).toString());
                descField.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        // Insert action
        insertBtn.addActionListener(e -> {
            try {
                Platform p = getPlatformFromFields();
                platformDAO.insertPlatform(p);
                loadPlatforms();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        // Update action
        updateBtn.addActionListener(e -> {
            try {
                Platform p = getPlatformFromFields();
                platformDAO.updatePlatform(p);
                loadPlatforms();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        // Delete action
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                platformDAO.deletePlatform(id);
                loadPlatforms();
                clearFields();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        return mainPanel;
    }

    private void loadPlatforms() {
        try {
            tableModel.setRowCount(0);
            List<Platform> platforms = platformDAO.getAllPlatforms();
            for (Platform p : platforms) {
                tableModel.addRow(new Object[]{
                        p.getPlatformId(),
                        p.getPlatformName(),
                        p.getPlatformUrl(),
                        p.getPlatformDescription()
                });
            }
        } catch (SQLException e) {
            showError("Error loading platforms: " + e.getMessage());
        }
    }

    private Platform getPlatformFromFields() {
        int id = Integer.parseInt(idField.getText().trim());
        String name = nameField.getText().trim();
        String url = urlField.getText().trim();
        String desc = descField.getText().trim();
        return new Platform(id, name, url, desc);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        urlField.setText("");
        descField.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "⚠️ Error", JOptionPane.ERROR_MESSAGE);
    }
}
