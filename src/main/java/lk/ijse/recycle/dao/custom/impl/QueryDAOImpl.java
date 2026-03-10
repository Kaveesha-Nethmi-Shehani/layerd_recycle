package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dao.custom.QueryDAO;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Material;
import lk.ijse.recycle.entity.Order;
import lk.ijse.recycle.entity.Product;
import lk.ijse.recycle.entity.RecycleItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    public Material getAllWithStockMaterial() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT m.*, COALESCE(SUM(r.item_kg), 0) as total_kg " +
                "FROM materials m " +
                "LEFT JOIN recycle_item r ON m.material_id = r.material_id " +
                "GROUP BY m.material_id, m.material_name, m.price_per_kg " +
                "ORDER BY m.material_name");

        List<Material> list = new ArrayList<>();
        while (rs.next()) {
            Material dto = new Material(
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("price_per_kg")
            );
            list.add(dto);
        }
        return (Material) list;
    }

    public OrderDto search(int orderId) throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT o.*, p.product_name, c.customer_name FROM orders o " +
                "LEFT JOIN product p ON o.product_id = p.product_id " +
                "LEFT JOIN customer c ON o.customer_id = c.customer_id " +
                "WHERE o.order_id = ?", orderId);

        if (rs.next()) {
            return new OrderDto(
                    rs.getInt("order_id"),
                    rs.getString("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price"),
                    rs.getString("order_date")
            );
        }
        return null;
    }


    public RecycleItem search(String id) throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT r.*, m.material_name FROM recycle_item r " +
                "LEFT JOIN materials m ON r.material_id = m.material_id " +
                "WHERE r.item_id = ?", id);

        if (rs.next()) {
            return new RecycleItem(
                    rs.getInt("item_id"),
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("item_kg"),
                    rs.getDouble("item_price"),
                    rs.getString("date")
            );
        }
        return null;
    }


    public List<RecycleItem> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT r.*, m.material_name FROM recycle_item r " +
                "LEFT JOIN materials m ON r.material_id = m.material_id " +
                "ORDER BY r.item_id DESC");

        List<RecycleItem> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new RecycleItem(
                    rs.getInt("item_id"),
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("item_kg"),
                    rs.getDouble("item_price"),
                    rs.getString("date")
            ));
        }
        return list;
    }





}

