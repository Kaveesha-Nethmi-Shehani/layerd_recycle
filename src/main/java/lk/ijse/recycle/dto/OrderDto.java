package lk.ijse.recycle.dto;

public class OrderDto {
    private int orderId;
    private String productId;
    private String product_name;
    private int customerId;
    private String customerName;
    private int quantity;
    private double totalPrice;
    private String orderDate;

    public OrderDto() {}

    public OrderDto(int orderId, String productId, int customerId, int quantity, double totalPrice, String orderDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }


    public OrderDto(int orderId, String productId, String product_name, int customerId, String customerName, int quantity, double totalPrice, String orderDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.product_name = product_name;
        this.customerId = customerId;
        this.customerName = customerName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public OrderDto(int orderId, String productId, int customerId, Object o, int quantity, double totalPrice, String orderDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;

    }



    public int getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public String getProduct_name() { return product_name; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public String getOrderDate() { return orderDate; }


    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setProduct_name(String productName) { this.product_name = productName; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", productId='" + productId + '\'' +
                ", product_name='" + product_name + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}


