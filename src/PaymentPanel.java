import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PaymentPanel {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtOrderId, txtDate, txtStatus, txtMethod, txtAmount;
    private PaymentDAO dao;

    public PaymentPanel() {
        dao = new PaymentDAO();
        panel = new JPanel(new BorderLayout());

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Order ID", "Date", "Status", "Method", "Amount"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // Form panel
        JPanel form = new JPanel(new GridLayout(6, 2));
        txtId = new JTextField();
        txtOrderId = new JTextField();
        txtDate = new JTextField();
        txtStatus = new JTextField();
        txtMethod = new JTextField();
        txtAmount = new JTextField();

        form.add(new JLabel("Payment ID")); form.add(txtId);
        form.add(new JLabel("Order ID")); form.add(txtOrderId);
        form.add(new JLabel("Payment Date (yyyy-mm-dd hh:mm:ss)")); form.add(txtDate);
        form.add(new JLabel("Payment Status")); form.add(txtStatus);
        form.add(new JLabel("Payment Method")); form.add(txtMethod);
        form.add(new JLabel("Payment Amount")); form.add(txtAmount);

        // Button panel
        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn); buttons.add(updateBtn); buttons.add(deleteBtn); buttons.add(refreshBtn);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        refreshTable();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtOrderId.setText(model.getValueAt(row, 1).toString());
                txtDate.setText(model.getValueAt(row, 2).toString());
                txtStatus.setText(model.getValueAt(row, 3).toString());
                txtMethod.setText(model.getValueAt(row, 4).toString());
                txtAmount.setText(model.getValueAt(row, 5).toString());
            }
        });

        addBtn.addActionListener(e -> {
            try {
                Payment p = new Payment(
                    Integer.parseInt(txtId.getText()),
                    Integer.parseInt(txtOrderId.getText()),
                    txtDate.getText(),
                    txtStatus.getText(),
                    txtMethod.getText(),
                    Double.parseDouble(txtAmount.getText())
                );
                dao.insertPayment(p);
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                Payment p = new Payment(
                    Integer.parseInt(txtId.getText()),
                    Integer.parseInt(txtOrderId.getText()),
                    txtDate.getText(),
                    txtStatus.getText(),
                    txtMethod.getText(),
                    Double.parseDouble(txtAmount.getText())
                );
                dao.updatePayment(p);
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                dao.deletePayment(Integer.parseInt(txtId.getText()));
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        refreshBtn.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        try {
            model.setRowCount(0);
            List<Payment> list = dao.getAllPayments();
            for (Payment p : list) {
                model.addRow(new Object[]{
                    p.getPaymentId(),
                    p.getOrderId(),
                    p.getPaymentDate(),
                    p.getPaymentStatus(),
                    p.getPaymentMethod(),
                    p.getPaymentAmount()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}