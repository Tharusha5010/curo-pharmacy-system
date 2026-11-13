package billing;

import java.util.ArrayList;
import java.util.List;
import models.Bill;
import models.BillItem;

public class BillingDAO {

    public static class MedSearchItem {
        public int id;
        public String name;
        public double price;
        public int stockQty;
        public boolean requiresRx;

        public MedSearchItem(int id, String name, double price, int stockQty, boolean requiresRx) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stockQty = stockQty;
            this.requiresRx = requiresRx;
        }

        @Override
        public String toString() {
            return name + " (Rs. " + price + " | Stock: " + stockQty + ")";
        }
    }

    public List<MedSearchItem> getAvailableMedicines() {
        List<MedSearchItem> list = new ArrayList<>();
        list.add(new MedSearchItem(1, "Paracetamol 500mg", 15.00, 100, false));
        list.add(new MedSearchItem(2, "Amoxicillin 250mg", 45.00, 50, true));
        list.add(new MedSearchItem(3, "Cetirizine 10mg", 8.50, 60, false));
        list.add(new MedSearchItem(4, "Metformin 500mg", 22.00, 80, true));
        list.add(new MedSearchItem(5, "Vitamin C 100mg", 5.00, 200, false));
        return list;
    }

    public boolean verifyOwner(String staffId, String password) {
        return "ADMIN01".equalsIgnoreCase(staffId.trim()) && "ict25954".equals(password.trim());
    }

    public boolean verifyAdmin(String staffId, String password) {
        return "ADMIN01".equalsIgnoreCase(staffId.trim()) && "ict25954".equals(password.trim());
    }

    public boolean processBillTransaction(Bill bill, List<BillItem> items) {
        int mockId = (int) (System.currentTimeMillis() % 100000);
        bill.setBill_id(mockId);
        // Mock transaction handling for bill and items list
        return true;
    }

    public String cancelPastBill(int targetBillId, String adminUser, String reason) {
        if (targetBillId <= 0) {
            return "Invalid Bill ID!";
        }
        return "SUCCESS";
    }
}