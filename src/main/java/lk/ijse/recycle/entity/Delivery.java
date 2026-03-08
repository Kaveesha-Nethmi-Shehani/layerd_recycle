package lk.ijse.recycle.entity;

public class Delivery {
    private int deliveryId;
    private int orderId;
    private String detailsLocation;

    public Delivery() {
    }

    public Delivery(int deliveryId, int orderId, String detailsLocation) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.detailsLocation = detailsLocation;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDetailsLocation() {
        return detailsLocation;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setDetailsLocation(String detailsLocation) {
        this.detailsLocation = detailsLocation;
    }
}

