package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.PaymentDAO;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public boolean save(Payment dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO payment(order_id, invoice, payment_value, payment_date) VALUES (?, ?, ?, ?)",
                dto.getOrderId(),
                dto.getInvoice(),
                dto.getPaymentValue(),
                dto.getPaymentDate()
        );
    }

    public boolean update(Payment  dto) throws SQLException, ClassNotFoundException {
        //String sql = "UPDATE payment SET order_id = ?, invoice = ?, payment_value = ?, payment_date = ? WHERE payment_id = ?";
        return CrudUtil.execute("UPDATE payment SET order_id = ?, invoice = ?, payment_value = ?, payment_date = ? WHERE payment_id = ?",
                dto.getOrderId(),
                dto.getInvoice(),
                dto.getPaymentValue(),
                dto.getPaymentDate(),
                dto.getPaymentId()
        );
    }


    public boolean delete(int paymentId) throws SQLException, ClassNotFoundException {
       // String sql = "DELETE FROM payment WHERE payment_id = ?";
        return CrudUtil.execute("DELETE FROM payment WHERE payment_id = ?", paymentId);
    }

    public Payment  search(String contact) throws SQLException {
        return null;
    }

    public Payment  search(int paymentId) throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute("SELECT * FROM payment WHERE payment_id = ?", paymentId);
        if (rs.next()) {
            return new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getString("invoice"),
                    rs.getDouble("payment_value"),
                    rs.getString("payment_date")
            );
        }
        return null;
    }

    public List<Payment> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM payment");

        List<Payment> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getString("invoice"),
                    rs.getDouble("payment_value"),
                    rs.getString("payment_date")
            ));
        }

        return list;
    }

    public int getNextId() throws SQLException, ClassNotFoundException {
       // String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'payment'";
        ResultSet rs = CrudUtil.execute("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'payment'");
        if (rs.next()) {
            return rs.getInt("AUTO_INCREMENT");
        }
        return 1;
    }

    public double getOrderTotalValue(int orderId) throws SQLException, ClassNotFoundException {
       // String sql = "SELECT total_price FROM orders WHERE order_id = ?";
        ResultSet rs = CrudUtil.execute("SELECT total_price FROM orders WHERE order_id = ?", orderId);
        if (rs.next()) {
            return rs.getDouble("total_price");
        }
        return 0.0;
    }
}






