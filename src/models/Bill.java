package models;

import java.time.LocalDateTime;

public class Bill {

    //Declare Class and Encapsulated Variables
    private int bill_id;
    private String cashier_id;
    private double total_amount;
    private double discount_amount;
    private double net_total;
    private boolean is_prescription_checked;
    private boolean is_free_issue;
    private LocalDateTime create_at;

    //Data Assigning (Creating Obects)
    public Bill() {
        this.create_at = LocalDateTime.now();
    }

    public Bill(String cashier_id, double total_amount, double discount_amount, double net_total, boolean is_prescription_checked, boolean is_free_issue) {
        this.cashier_id = cashier_id;
        this.total_amount = total_amount;
        this.discount_amount = discount_amount;
        this.net_total = net_total;
        this.is_prescription_checked = is_prescription_checked;
        this.is_free_issue = is_free_issue;
        this.create_at = LocalDateTime.now();
    }

    public Bill(int bill_id, String cashier_id, double total_amount, double discount_amount, double net_total, boolean is_prescription_checked, boolean is_free_issue, LocalDateTime create_at) {
        this.bill_id = bill_id;
        this.cashier_id = cashier_id;
        this.total_amount = total_amount;
        this.discount_amount = discount_amount;
        this.net_total = net_total;
        this.is_prescription_checked = is_prescription_checked;
        this.is_free_issue = is_free_issue;
        this.create_at = (create_at != null) ? create_at : LocalDateTime.now();
    }

    //Accessing and Writting private fields
    public int getBill_id() {
        return bill_id;
    }
    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getCashier_id() {
        return cashier_id;
    }
    public void setCashier_id(String cashier_id) {
        this.cashier_id = cashier_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }
    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public double getNet_total() {
        return net_total;
    }
    public void setNet_total(double net_total) {
        this.net_total = net_total;
    }

    public boolean isIs_prescription_checked() {
        return is_prescription_checked;
    }
    public void setIs_prescription_checked(boolean is_prescription_checked) {
        this.is_prescription_checked = is_prescription_checked;
    }

    public boolean isIs_free_issue() {
        return is_free_issue;
    }
    public void setIs_free_issue(boolean is_free_issue) {
        this.is_free_issue = is_free_issue;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }
    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = (create_at != null) ? create_at : LocalDateTime.now();
    }
}
