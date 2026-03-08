/*
package lk.ijse.recycle.model;

import lk.ijse.recycle.db.DBConnection;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    public boolean save(ProductDto dto) throws SQLException {
        String sql = "INSERT INTO product (product_id, product_name, material_id, kg_per_unit, selling_price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.executeUpdate(sql, 
            dto.getProductId(), 
            dto.getProductName(), 
            dto.getMaterialId(),
            dto.getKgPerUnit(),
            dto.getSellingPrice(),
            dto.getStockQuantity()
        );
    }

    public boolean update(ProductDto dto) throws SQLException {
        String sql = "UPDATE product SET product_name = ?, material_id = ?, kg_per_unit = ?, selling_price = ? WHERE product_id = ?";
        return CrudUtil.executeUpdate(sql, 
            dto.getProductName(), 
            dto.getMaterialId(),
            dto.getKgPerUnit(),
            dto.getSellingPrice(),
            dto.getProductId()
        );
    }

    public boolean delete(String productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id = ?";
        return CrudUtil.executeUpdate(sql, productId);
    }

    public ProductDto search(String productId) throws SQLException {
        String sql = "SELECT p.*, m.material_name FROM product p " +
                     "LEFT JOIN materials m ON p.material_id = m.material_id " +
                     "WHERE p.product_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, productId);

        if (rs.next()) {
            return new ProductDto(
                rs.getString("product_id"),
                rs.getString("product_name"),
                rs.getObject("material_id") != null ? rs.getInt("material_id") : null,
                rs.getString("material_name"),
                rs.getDouble("kg_per_unit"),
                rs.getDouble("selling_price"),
                rs.getInt("stock_quantity")
            );
        }
        return null;
    }

    public List<ProductDto> getAllProduct() throws SQLException {
        String sql = "SELECT p.*, m.material_name FROM product p " +
                     "LEFT JOIN materials m ON p.material_id = m.material_id";
        ResultSet rs = CrudUtil.executeQuery(sql);

        List<ProductDto> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new ProductDto(
                rs.getString("product_id"),
                rs.getString("product_name"),
                rs.getObject("material_id") != null ? rs.getInt("material_id") : null,
                rs.getString("material_name"),
                rs.getDouble("kg_per_unit"),
                rs.getDouble("selling_price"),
                rs.getInt("stock_quantity")
            ));
        }
        return list;
    }


    public List<MaterialDto> getAllMaterials() throws SQLException {
        String sql = "SELECT * FROM materials ORDER BY material_name";
        ResultSet rs = CrudUtil.executeQuery(sql);

        List<MaterialDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new MaterialDto(
                rs.getInt("material_id"),
                rs.getString("material_name"),
                rs.getDouble("price_per_kg")
            ));
        }
        return list;
    }


    public boolean manufacture(String productId, int quantity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        
        try {
            connection.setAutoCommit(false);
            

            ProductDto product = search(productId);
            if (product == null || product.getMaterialId() == null) {
                connection.rollback();
                return false;
            }

            double requiredKg = product.getKgPerUnit() * quantity;


            double availableKg = getMaterialTotalStock(product.getMaterialId());
            
            if (availableKg < requiredKg) {
                connection.rollback();
                return false;
            }


            double remainingToDeduct = requiredKg;
            String selectSql = "SELECT item_id, item_kg FROM recycle_item WHERE material_id = ? AND item_kg > 0 ORDER BY date ASC FOR UPDATE";
            ResultSet rs = CrudUtil.executeQuery(selectSql, product.getMaterialId());

            while (rs.next() && remainingToDeduct > 0) {
                int itemId = rs.getInt("item_id");
                double itemKg = rs.getDouble("item_kg");

                if (itemKg <= remainingToDeduct) {

                    String updateSql = "UPDATE recycle_item SET item_kg = 0 WHERE item_id = ?";
                    CrudUtil.executeUpdate(updateSql, itemId);
                    remainingToDeduct -= itemKg;
                } else {

                    String updateSql = "UPDATE recycle_item SET item_kg = item_kg - ? WHERE item_id = ?";
                    CrudUtil.executeUpdate(updateSql, remainingToDeduct, itemId);
                    remainingToDeduct = 0;
                }
            }


            String addStockSql = "UPDATE product SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
            boolean stockAdded = CrudUtil.executeUpdate(addStockSql, quantity, productId);

            if (!stockAdded) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
            
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public double getMaterialTotalStock(Integer materialId) throws SQLException {
        if (materialId == null) return 0;
        
        String sql = "SELECT COALESCE(SUM(item_kg), 0) as total_kg FROM recycle_item WHERE material_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, materialId);

        if (rs.next()) {
            return rs.getDouble("total_kg");
        }
        return 0;
    }

    public boolean reduceStock(String productId, int quantity) throws SQLException {
        String sql = "UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?";
        return CrudUtil.executeUpdate(sql, quantity, productId, quantity);
    }

    public boolean hasStock(String productId, int quantity) throws SQLException {
        String sql = "SELECT stock_quantity FROM product WHERE product_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, productId);

        if (rs.next()) {
            return rs.getInt("stock_quantity") >= quantity;
        }
        return false;
    }
}


*/
