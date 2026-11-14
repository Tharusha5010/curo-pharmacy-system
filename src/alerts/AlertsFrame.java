package alerts;

import models.Notification;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlertsFrame extends JFrame {

    private JTable alertTable;
    private DefaultTableModel tableModel;
    private JLabel timerLabel;
    private NotificationManagaer notificationManager;
    private Timer refreshTimer;
    private int countdownSeconds = 10;

    public AlertsFrame() {
        setTitle("System Notifications");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Position on the bottom right corner as a notification panel
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenBounds = ge.getMaximumWindowBounds();
        setLocation(screenBounds.width - 530, screenBounds.height - 500);

        notificationManager = new NotificationManagaer();

        // Top Header
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Active Alerts");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        timerLabel = new JLabel("Closing in 10s");
        timerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timerLabel.setForeground(Color.RED);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center Table with Type, Title, and Message
        String[] columns = {"Type", "Title", "Message"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        alertTable = new JTable(tableModel);
        alertTable.setRowHeight(26);
        alertTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        alertTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        alertTable.getColumnModel().getColumn(2).setPreferredWidth(270);

        // Styling for alert types
        alertTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String type = (String) value;

                if ("OUT OF STOCK".equalsIgnoreCase(type) || "EXPIRED".equalsIgnoreCase(type) || "SECURITY".equalsIgnoreCase(type)) {
                    c.setForeground(new Color(200, 0, 0));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if ("LOW STOCK".equalsIgnoreCase(type) || "EXPIRING SOON".equalsIgnoreCase(type) || "WARNING".equalsIgnoreCase(type)) {
                    c.setForeground(new Color(220, 100, 0));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if ("BILLING".equalsIgnoreCase(type)) {
                    c.setForeground(new Color(46, 125, 50));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(new Color(0, 100, 180));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                }
                return c;
            }
        });

        add(new JScrollPane(alertTable), BorderLayout.CENTER);

        // Bottom Dismiss Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeBtn = new JButton("Dismiss");
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load data
        loadAlerts();

        // 10-second countdown to auto-close
        refreshTimer = new Timer(1000, e -> {
            countdownSeconds--;
            if (countdownSeconds <= 0) {
                refreshTimer.stop();
                dispose();
            } else {
                timerLabel.setText("Closing in " + countdownSeconds + "s");
            }
        });
        refreshTimer.start();
    }

    private void loadAlerts() {
        tableModel.setRowCount(0);
        List<Notification> list = notificationManager.fetchLiveNotifications();
        for (Notification n : list) {
            tableModel.addRow(new Object[]{n.getType(), n.getTitle(), n.getMessage()});
        }
    }

    @Override
    public void dispose() {
        if (refreshTimer != null && refreshTimer.isRunning()) {
            refreshTimer.stop();
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AlertsFrame().setVisible(true));
    }
}