import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PaymentTableModel extends AbstractTableModel {
    private final String[] columns = {
        "Payment ID", "Order ID", "Date", "Status", "Method", "Amount 💵"
    };

    private final List<Payment> paymentList;

    public PaymentTableModel(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    @Override
    public int getRowCount() {
        return paymentList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Payment payment = paymentList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> payment.getPaymentId();
            case 1 -> payment.getOrderId();
            case 2 -> payment.getPaymentDate();
            case 3 -> payment.getPaymentStatus();
            case 4 -> payment.getPaymentMethod();
            case 5 -> payment.getPaymentAmount();
            default -> null;
        };
    }

    // Optional: to make cells editable (currently false)
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    // Optional: update table dynamically if needed
    public void refreshData(List<Payment> newData) {
        paymentList.clear();
        paymentList.addAll(newData);
        fireTableDataChanged();
    }
}
