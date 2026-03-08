package lk.ijse.recycle.entity;

import lk.ijse.recycle.dto.MaterialDto;

public class Material extends MaterialDto {
        private int materialId;
        private String materialName;
        private double pricePerKg;
        private double unitPrice;

        public Material() {
        }

        public Material(int materialId, String materialName, double pricePerKg) {
            this.materialId = materialId;
            this.materialName = materialName;
            this.pricePerKg = pricePerKg;
        }

    public Material(int materialId, String materialName, double pricePerKg,double unitPrice) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.pricePerKg = pricePerKg;
        this.unitPrice = unitPrice;
    }

    public Material(int materialId, String materialName) {
            this.materialId = materialId;
            this.materialName = materialName;
    }

    public int getMaterialId() {
            return materialId;
        }

        public String getMaterialName() {
            return materialName;
        }

        public double getPricePerKg() {
            return pricePerKg;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public void setPricePerKg(double pricePerKg) {
            this.pricePerKg = pricePerKg;
        }
        public double getUnitPrice() {
            return unitPrice;
        }
        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

    }

