package lk.ijse.recycle.entity;

public class Payment {
    private int paymentId;
    private int orderId;
    private String invoice;
    private double paymentValue;
    private String paymentDate;

    public Payment () {}

    public Payment (int paymentId, int orderId, String invoice, double paymentValue, String paymentDate) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.invoice = invoice;
        this.paymentValue = paymentValue;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() { return paymentId; }
    public int getOrderId() { return orderId; }
    public String getInvoice() { return invoice; }
    public double getPaymentValue() { return paymentValue; }
    public String getPaymentDate() { return paymentDate; }

    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setInvoice(String invoice) { this.invoice = invoice; }
    public void setPaymentValue(double paymentValue) { this.paymentValue = paymentValue; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate;
    }
}



