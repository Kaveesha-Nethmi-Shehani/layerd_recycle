package lk.ijse.recycle.entity;

public class Product {

    private String productId;
    private String product_name;
    private Integer materialId;
    private String materialName;
    private double kgPerUnit;
    private double sellingPrice;
    private int stockQuantity;

    public Product() {}


    public Product(String productId, String product_name, Integer materialId, double kgPerUnit, double sellingPrice, int stockQuantity) {
        this.productId = productId;
        this.product_name = product_name;
        this.materialId = materialId;
        this.kgPerUnit = kgPerUnit;
        this.sellingPrice = sellingPrice;
        this.stockQuantity = stockQuantity;
    }


    public Product (String productId, String product_name, Integer materialId, String materialName, double kgPerUnit, double sellingPrice, int stockQuantity) {
        this.productId = productId;
        this.product_name = product_name;
        this.materialId = materialId;
        this.materialName = materialName;
        this.kgPerUnit = kgPerUnit;
        this.sellingPrice = sellingPrice;
        this.stockQuantity = stockQuantity;
    }


    public String getProductId() { return productId; }
    public String getProduct_name() { return product_name; }
    public Integer getMaterialId() { return materialId; }
    public String getMaterialName() { return materialName; }
    public double getKgPerUnit() { return kgPerUnit; }
    public double getSellingPrice() { return sellingPrice; }
    public int getStockQuantity() { return stockQuantity; }


    public void setProductId(String productId) { this.productId = productId; }
    public void setProduct_name(String productName) { this.product_name = productName; }
    public void setMaterialId(Integer materialId) { this.materialId = materialId; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }
    public void setKgPerUnit(double kgPerUnit) { this.kgPerUnit = kgPerUnit; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

}
