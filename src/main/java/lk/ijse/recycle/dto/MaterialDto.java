package lk.ijse.recycle.dto;

public class MaterialDto {
    private int materialId;
    private String materialName;
    private double pricePerKg;
    private double totalStockMaterial;

    public MaterialDto() {}

    public MaterialDto(int materialId, String materialName, double pricePerKg) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.pricePerKg = pricePerKg;
    }

    public MaterialDto(int materialId, String materialName, double pricePerKg, int totalStockMaterial) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.pricePerKg = pricePerKg;
        this.totalStockMaterial = totalStockMaterial;
    }

    public int getMaterialId() { return materialId; }
    public String getMaterialName() { return materialName; }
    public double getPricePerKg() { return pricePerKg; }
    public double getTotalStockMaterial() { return totalStockMaterial; }

    public void setMaterialId(int materialId) { this.materialId = materialId; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }
    public void setPricePerKg(double pricePerKg) { this.pricePerKg = pricePerKg; }
    public double setTotalStockMaterial(int totalStockMaterial) { this.totalStockMaterial = totalStockMaterial;
        return 0;
    }

    @Override
    public String toString() {
        return materialName + " (LKR " + pricePerKg + "/kg)";
    }
}
