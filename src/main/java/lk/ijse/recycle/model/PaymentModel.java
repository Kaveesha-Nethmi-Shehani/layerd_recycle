/*
package lk.ijse.recycle.model;

import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public boolean savePayment(PaymentDto dto) throws SQLException {
        return CrudUtil.executeUpdate(
                "INSERT INTO payment(order_id, invoice, payment_value, payment_date) VALUES (?, ?, ?, ?)",
                dto.getOrderId(),
                dto.getInvoice(),
                dto.getPaymentValue(),
                dto.getPaymentDate()
        );
    }

    public boolean updatePayment(PaymentDto dto) throws SQLException {
        String sql = "UPDATE payment SET order_id = ?, invoice = ?, payment_value = ?, payment_date = ? WHERE payment_id = ?";
        return CrudUtil.executeUpdate(sql,
                dto.getOrderId(),
                dto.getInvoice(),
                dto.getPaymentValue(),
                dto.getPaymentDate(),
                dto.getPaymentId()
        );
    }

    public boolean deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        return CrudUtil.executeUpdate(sql, paymentId);
    }

    public PaymentDto searchPayment(int paymentId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, paymentId);
        if (rs.next()) {
            return new PaymentDto(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getString("invoice"),
                    rs.getDouble("payment_value"),
                    rs.getString("payment_date")
            );
        }
        return null;
    }

    public List<PaymentDto> getAllPayments() throws SQLException {
        String sql = "SELECT * FROM payment";
        ResultSet rs = CrudUtil.executeQuery(sql);
        List<PaymentDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new PaymentDto(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getString("invoice"),
                    rs.getDouble("payment_value"),
                    rs.getString("payment_date")
            ));
        }
        return list;
    }

    public int getNextPaymentId() throws SQLException {
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'payment'";
        ResultSet rs = CrudUtil.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("AUTO_INCREMENT");
        }
        return 1;
    }

    public double getOrderTotalValue(int orderId) throws SQLException {
        String sql = "SELECT total_price FROM orders WHERE order_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, orderId);
        if (rs.next()) {
            return rs.getDouble("total_price");
        }
        return 0.0;
    }
}






*/
