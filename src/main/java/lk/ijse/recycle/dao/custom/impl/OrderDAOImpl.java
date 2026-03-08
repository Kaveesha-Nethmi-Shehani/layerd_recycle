package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.OrderDAO;
import lk.ijse.recycle.db.DBConnection;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public boolean save(Order dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        try {
            connection.setAutoCommit(false);

            ResultSet rs = CrudUtil.execute("SELECT stock_quantity FROM product WHERE product_id = ? FOR UPDATE", dto.getProductId());

            if (!rs.next()) {
                connection.rollback();
                return false;
            }

            int availableStock = rs.getInt("stock_quantity");
            if (availableStock < dto.getQuantity()) {
                connection.rollback();
                return false;
            }

            boolean saved = CrudUtil.execute("INSERT INTO orders(product_id, customer_id, quantity, total_price, order_date) VALUES (?, ?, ?, ?, ?)",
                    dto.getProductId(),
                    dto.getCustomerId(),
                    dto.getQuantity(),
                    dto.getTotalPrice(),
                    dto.getOrderDate()
            );

            if (!saved) {
                connection.rollback();
                return false;
            }

            boolean reduced = CrudUtil.execute("UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ?",
                    dto.getQuantity(),
                    dto.getProductId());

            if (!reduced) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } catch (ClassNotFoundException e) {

        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE orders SET product_id = ?, customer_id = ?, quantity = ?, total_price = ?, order_date = ? WHERE order_id = ?",
                dto.getProductId(),
                dto.getCustomerId(),
                dto.getQuantity(),
                dto.getTotalPrice(),
                dto.getOrderDate(),
                dto.getOrderId()
        );
    }


    public boolean delete(int orderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM orders WHERE order_id = ?", orderId);
    }

    public Order  search(String contact) throws SQLException {
        return null;
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

    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT o.*, p.product_name, c.customer_name FROM orders o " +
                "LEFT JOIN product p ON o.product_id = p.product_id " +
                "LEFT JOIN customer c ON o.customer_id = c.customer_id " +
                "ORDER BY o.order_id DESC");

        List<Order> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Order(
                    rs.getInt("order_id"),
                    rs.getString("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price"),
                    rs.getString("order_date")
            ));
        }
        return list;
    }

    public int getNextId() throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute("SELECT COALESCE(MAX(order_id), 0) + 1 AS next_id FROM orders");
        if (rs.next()) {
            return rs.getInt("next_id");
        }
        return 1;
    }

    public List<ProductDto> getAvailableProducts() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT p.*, m.material_name FROM product p " +
                "LEFT JOIN materials m ON p.material_id = m.material_id");

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

    public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer");

        List<CustomerDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new CustomerDto(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_contact"),
                    rs.getString("customer_date")
            ));
        }
        return list;
    }
}




