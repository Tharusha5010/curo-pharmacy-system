package billing;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Bill;
import models.BillItem;

public class BillingFrame extends JFrame {
    private JComboBox<BillingDAO.MedSearchItem> medComboBox;
    private JTextField qtyField;
    private JTextField discountField;
    private JCheckBox rxCheckBox;
    private JCheckBox freeIssueCheckBox;
    private JTable billTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JLabel netTotalLabel;

    private BillingDAO billingDAO;
    private List<BillItem> currentBillItems;
    private String activeCashierId;
    private boolean isFreeIssueActive = false;
    private double currentDiscount = 0.0;

    public BillingFrame(String cashierId) {
        this.activeCashierId = cashierId;
        this.currentBillItems = new ArrayList<>();

        setTitle("Pharmacy POS - Checkout & Billing [Cashier ID: " + cashierId + "]");
        setSize(920, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        billingDAO = new BillingDAO();

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Selection"));

        medComboBox = new JComboBox<>();
        loadMedicineDropdown();
        medComboBox.addActionListener(e -> rxCheckBox.setSelected(false));

        qtyField = new JTextField("1");
        discountField = new JTextField("0.00");
        rxCheckBox = new JCheckBox("Prescription Verified");
        freeIssueCheckBox = new JCheckBox("Free Issue (Owner Only)");

        freeIssueCheckBox.addActionListener(e -> handleFreeIssueToggle());

        JButton addItemBtn = new JButton("Add Item");
        addItemBtn.setBackground(new Color(220, 240, 255));
        addItemBtn.addActionListener(e -> addItemToBill());

        inputPanel.add(new JLabel("Select Medicine:"));
        inputPanel.add(medComboBox);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(qtyField);
        inputPanel.add(rxCheckBox);
        inputPanel.add(freeIssueCheckBox);
        inputPanel.add(new JLabel(""));
        inputPanel.add(addItemBtn);

        add(inputPanel, BorderLayout.NORTH);

        String[] columns = {"Medicine ID", "Medicine Name", "Unit Price", "Qty", "Line Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        billTable = new JTable(tableModel);
        add(new JScrollPane(billTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        totalLabel = new JLabel("Subtotal: Rs. 0.00");
        netTotalLabel = new JLabel("Net Total: Rs. 0.00");
        netTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton applyFivePercentBtn = new JButton("Apply 5% Discount");
        applyFivePercentBtn.addActionListener(e -> {
            if (!isFreeIssueActive) {
                double subtotal = calculateSubtotal();
                currentDiscount = subtotal * 0.05;
                discountField.setText(String.format("%.2f", currentDiscount));
                refreshSummary();
            }
        });

        summaryPanel.add(totalLabel);
        summaryPanel.add(new JLabel("Discount (Rs):"));
        summaryPanel.add(discountField);
        summaryPanel.add(applyFivePercentBtn);
        summaryPanel.add(netTotalLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton removeItemBtn = new JButton("Remove Item");
        removeItemBtn.addActionListener(e -> removeSelectedItem());

        JButton cancelBillBtn = new JButton("Cancel Past Bill (Admin Only)");
        cancelBillBtn.setBackground(new Color(211, 47, 47));
        cancelBillBtn.setForeground(Color.WHITE);
        cancelBillBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelBillBtn.addActionListener(e -> handlePastBillCancellation());

        JButton checkoutBtn = new JButton("Process Checkout");
        checkoutBtn.setBackground(new Color(46, 125, 50));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkoutBtn.addActionListener(e -> processCheckout());

        buttonPanel.add(removeItemBtn);
        buttonPanel.add(cancelBillBtn);
        buttonPanel.add(checkoutBtn);

        bottomPanel.add(summaryPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadMedicineDropdown() {
        medComboBox.removeAllItems();
        List<BillingDAO.MedSearchItem> list = billingDAO.getAvailableMedicines();
        for (BillingDAO.MedSearchItem item : list) {
            medComboBox.addItem(item);
        }
    }

    private void handleFreeIssueToggle() {
        if (freeIssueCheckBox.isSelected()) {
            JTextField userField = new JTextField("ADMIN01");
            JPasswordField passField = new JPasswordField("ict25954");
            Object[] message = {
                "Owner Authorization Required for Free Issue (Rs. 0.0):",
                "Username/ID:", userField,
                "Password:", passField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Owner Authorization", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION && billingDAO.verifyOwner(userField.getText().trim(), new String(passField.getPassword()).trim())) {
                isFreeIssueActive = true;
                currentDiscount = 0.0;
                discountField.setText("0.00");
                JOptionPane.showMessageDialog(this, "Free Issue Mode Activated. Prices adjusted to Rs. 0.0.", "Authorized", JOptionPane.INFORMATION_MESSAGE);
                rebuildTablePrices();
            } else {
                freeIssueCheckBox.setSelected(false);
                isFreeIssueActive = false;
                JOptionPane.showMessageDialog(this, "Access Denied: Owner privileges required!", "Unauthorized", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            isFreeIssueActive = false;
            rebuildTablePrices();
        }
    }

    private void addItemToBill() {
        BillingDAO.MedSearchItem selected = (BillingDAO.MedSearchItem) medComboBox.getSelectedItem();
        if (selected == null) return;

        try {
            int qty = Integer.parseInt(qtyField.getText().trim());
            if (qty <= 0) throw new NumberFormatException();

            if (qty > selected.stockQty) {
                JOptionPane.showMessageDialog(this, "Insufficient stock! Available: " + selected.stockQty, "Stock Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selected.requiresRx && !rxCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Prescription verification required for this medicine!", "Prescription Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price = isFreeIssueActive ? 0.0 : selected.price;
            
            // Using leader's exact BillItem constructor
            BillItem item = new BillItem(selected.id, selected.name, price, qty);

            currentBillItems.add(item);
            tableModel.addRow(new Object[]{
                item.getMedicineId(), item.getMedicineName(), item.getSoldPrice(), item.getQuantity(), item.getLineTotal()
            });

            refreshSummary();
            qtyField.setText("1");
            rxCheckBox.setSelected(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rebuildTablePrices() {
        tableModel.setRowCount(0);
        for (BillItem bi : currentBillItems) {
            if (isFreeIssueActive) {
                bi.setSoldPrice(0.0);
            }
            tableModel.addRow(new Object[]{
                bi.getMedicineId(), bi.getMedicineName(), bi.getSoldPrice(), bi.getQuantity(), bi.getLineTotal()
            });
        }
        if (isFreeIssueActive) {
            currentDiscount = 0.0;
            discountField.setText("0.00");
        }
        refreshSummary();
    }

    private void removeSelectedItem() {
        int row = billTable.getSelectedRow();
        if (row != -1) {
            currentBillItems.remove(row);
            tableModel.removeRow(row);
            refreshSummary();
        }
    }

    private double calculateSubtotal() {
        double subtotal = 0.0;
        for (BillItem bi : currentBillItems) {
            subtotal += bi.getLineTotal();
        }
        return subtotal;
    }

    private void refreshSummary() {
        double subtotal = calculateSubtotal();
        if (isFreeIssueActive) {
            subtotal = 0.0;
            currentDiscount = 0.0;
        }
        double netTotal = Math.max(0.0, subtotal - currentDiscount);

        totalLabel.setText("Subtotal: Rs. " + String.format("%.2f", subtotal));
        netTotalLabel.setText("Net Total: Rs. " + String.format("%.2f", netTotal));
    }

    private void processCheckout() {
        if (currentBillItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The bill is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double subtotal = calculateSubtotal();
        if (isFreeIssueActive) {
            subtotal = 0.0;
            currentDiscount = 0.0;
        }
        double netTotal = Math.max(0.0, subtotal - currentDiscount);

        Bill bill = new Bill();
        bill.setCashier_id(activeCashierId);
        bill.setTotal_amount(subtotal);
        bill.setDiscount_amount(currentDiscount);
        bill.setNet_total(netTotal);
        bill.setIs_prescription_checked(rxCheckBox.isSelected());
        bill.setIs_free_issue(isFreeIssueActive);

        boolean success = billingDAO.processBillTransaction(bill, currentBillItems);
        if (success) {
            JOptionPane.showMessageDialog(this, "Bill #" + bill.getBill_id() + " processed successfully! Net Total: Rs. " + String.format("%.2f", netTotal), "Success", JOptionPane.INFORMATION_MESSAGE);

            tableModel.setRowCount(0);
            currentBillItems.clear();
            isFreeIssueActive = false;
            currentDiscount = 0.0;
            freeIssueCheckBox.setSelected(false);
            discountField.setText("0.00");
            loadMedicineDropdown();
            refreshSummary();
        }
    }

    private void handlePastBillCancellation() {
        JTextField billIdField = new JTextField();
        JTextField adminUserField = new JTextField("ADMIN01");
        JPasswordField adminPassField = new JPasswordField("ict25954");
        JTextField reasonField = new JTextField();

        Object[] msg = {
            "Enter Printed Bill ID to Cancel:", billIdField,
            "Cancellation Reason:", reasonField,
            "---------------------------------------",
            "Admin / Owner Username:", adminUserField,
            "Admin Password:", adminPassField
        };

        int option = JOptionPane.showConfirmDialog(
            this, msg, "Privileged Past Bill Cancellation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String billIdText = billIdField.getText().trim();
            String reason = reasonField.getText().trim();
            String adminUser = adminUserField.getText().trim();
            String adminPass = new String(adminPassField.getPassword()).trim();

            if (billIdText.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bill ID and Cancellation Reason are mandatory!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!billingDAO.verifyAdmin(adminUser, adminPass)) {
                JOptionPane.showMessageDialog(this, "Access Denied: Admin or Owner privileges required!", "Unauthorized", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int billId = Integer.parseInt(billIdText);
                String result = billingDAO.cancelPastBill(billId, adminUser, reason);

                if ("SUCCESS".equals(result)) {
                    JOptionPane.showMessageDialog(this, 
                        "Bill #" + billId + " successfully canceled!\nMedicines returned to stock.", 
                        "Cancellation Complete", JOptionPane.INFORMATION_MESSAGE);

                    loadMedicineDropdown();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Cancellation Failed", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric Bill ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillingFrame("ADMIN01").setVisible(true));
    }
}