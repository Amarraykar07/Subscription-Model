import java.util.List;
import javax.swing.table.AbstractTableModel;

public class SubscriptionTableModel extends AbstractTableModel {
    private final List<Subscription> subscriptions;
    private final String[] columns = {"Subscription ID", "Subscriber ID", "Platform ID", "Plan", "Start", "End", "Status", "Auto-Renewal"};

    public SubscriptionTableModel(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public int getRowCount() {
        return subscriptions.size();
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
        Subscription s = subscriptions.get(row);
        return switch (col) {
            case 0 -> s.getSubscriptionId();
            case 1 -> s.getSubscriberId();
            case 2 -> s.getPlatformId();
            case 3 -> s.getSubscriptionPlan();
            case 4 -> s.getStartDate();
            case 5 -> s.getEndDate();
            case 6 -> s.getSubscriptionStatus();
            case 7 -> s.isRenewalAuto();
            default -> null;
        };
    }
}
