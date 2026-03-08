package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.ProductDAO;
import lk.ijse.recycle.db.DBConnection;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.entity.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    public boolean save(Product dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute("INSERT INTO product (product_id, product_name, material_id, kg_per_unit, selling_price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getProductId(),
                dto.getProduct_name(),
                dto.getMaterialId(),
                dto.getKgPerUnit(),
                dto.getSellingPrice(),
                dto.getStockQuantity()
        );
    }

    public boolean update(Product  dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute("UPDATE product SET product_name = ?, material_id = ?, kg_per_unit = ?, selling_price = ? WHERE product_id = ?",
                dto.getProduct_name(),
                dto.getMaterialId(),
                dto.getKgPerUnit(),
                dto.getSellingPrice(),
                dto.getProductId()
        );
    }

    @Override
    public boolean delete(int customerId) throws SQLException, ClassNotFoundException {
        return false;
    }


    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM product WHERE product_id = ?", productId);
    }


    public Product search(String productId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT p.*, m.material_name FROM product p " +
                "LEFT JOIN materials m ON p.material_id = m.material_id " +
                "WHERE p.product_id = ?", productId);

        if (rs.next()) {
            return new Product(
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

    public List<Product> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT p.*, m.material_name FROM product p " +
                "LEFT JOIN materials m ON p.material_id = m.material_id");

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Product(
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

    @Override
    public int getNextId() throws SQLException {
        return 0;
    }


    public List<MaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute("SELECT * FROM materials ORDER BY material_name");

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


    public boolean manufacture(String productId, int quantity) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        try {
            connection.setAutoCommit(false);


            Product product = search(productId);
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
             ResultSet rs = CrudUtil.execute("SELECT item_id, item_kg FROM recycle_item WHERE material_id = ? AND item_kg > 0 ORDER BY date ASC FOR UPDATE", product.getMaterialId());

            while (rs.next() && remainingToDeduct > 0) {
                int itemId = rs.getInt("item_id");
                double itemKg = rs.getDouble("item_kg");

                if (itemKg <= remainingToDeduct) {

                    String updateSql = "UPDATE recycle_item SET item_kg = 0 WHERE item_id = ?";
                    CrudUtil.execute(updateSql, itemId);
                    remainingToDeduct -= itemKg;
                } else {

                    String updateSql = "UPDATE recycle_item SET item_kg = item_kg - ? WHERE item_id = ?";
                    CrudUtil.execute(updateSql, remainingToDeduct, itemId);
                    remainingToDeduct = 0;
                }
            }


            String addStockSql = "UPDATE product SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
            boolean stockAdded = CrudUtil.execute(addStockSql, quantity, productId);

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


    public double getMaterialTotalStock(Integer materialId) throws SQLException, ClassNotFoundException {
        if (materialId == null) return 0;

         ResultSet rs = CrudUtil.execute("SELECT COALESCE(SUM(item_kg), 0) as total_kg FROM recycle_item WHERE material_id = ?", materialId);

        if (rs.next()) {
            return rs.getDouble("total_kg");
        }
        return 0;
    }

    public boolean reduceStock(String productId, int quantity) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute("UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?", quantity, productId, quantity);
    }

    public boolean hasStock(String productId, int quantity) throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute("SELECT stock_quantity FROM product WHERE product_id = ?", productId);

        if (rs.next()) {
            return rs.getInt("stock_quantity") >= quantity;
        }
        return false;
    }
}


