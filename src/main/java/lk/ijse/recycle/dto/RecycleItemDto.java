package lk.ijse.recycle.dto;

public class RecycleItemDto {
    private int itemId;
    private int materialId;
    private String materialName;
    private double itemKg;
    private double itemPrice;
    private String date;

    public RecycleItemDto() {}

    public RecycleItemDto(int itemId, int materialId, double itemKg, double itemPrice, String date) {
        this.itemId = itemId;
        this.materialId = materialId;
        this.itemKg = itemKg;
        this.itemPrice = itemPrice;
        this.date = date;
    }

    public RecycleItemDto(int itemId, int materialId, double itemKg, double itemPrice, String date,String materialName) {
        this.itemId = itemId;
        this.materialId = materialId;
        this.itemKg = itemKg;
        this.itemPrice = itemPrice;
        this.date = date;
        this.materialName = materialName;
    }

    public RecycleItemDto(int materialId, double itemKg, double itemPrice, String date, int itemId) {
        this.materialId = materialId;
        this.itemKg = itemKg;
        this.itemPrice = itemPrice;
        this.date = date;
        this.itemId = itemId;

    }

    public RecycleItemDto(int materialId, String materialName, double itemKg, double itemPrice, String date, int itemId) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.itemKg = itemKg;
        this.itemPrice = itemPrice;
        this.date = date;
        this.itemId = itemId;
    }


    public int getItemId() { return itemId; }
    public int getMaterialId() { return materialId; }
    public String getMaterialName() { return materialName; }
    public double getItemKg() { return itemKg; }
    public double getItemPrice() { return itemPrice; }
    public String getDate() { return date; }


    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }
    public void setItemKg(double itemKg) { this.itemKg = itemKg; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "RecycleItemDto{" +
                "itemId=" + itemId +
                ", materialId=" + materialId +
                ", materialName='" + materialName + '\'' +
                ", itemKg=" + itemKg +
                ", itemPrice=" + itemPrice +
                ", date='" + date + '\'' +
                '}';
    }
}


