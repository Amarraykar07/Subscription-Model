import java.util.List;
import javax.swing.table.AbstractTableModel;

public class OrderTableModel extends AbstractTableModel {
    private final List<Order> orders;
    private final String[] columns = {"Order ID", "Subscriber ID", "Amount", "Date", "Status", "Payment Status", "Delivery Date"};

    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Order o = orders.get(row);
        return switch (col) {
            case 0 -> o.getOrderId();
            case 1 -> o.getSubscriberId();
            case 2 -> o.getAmount();
            case 3 -> o.getOrderDate();
            case 4 -> o.getStatus();
            case 5 -> o.getPaymentStatus();
            case 6 -> o.getDeliveryDate();
            default -> null;
        };
    }
}
