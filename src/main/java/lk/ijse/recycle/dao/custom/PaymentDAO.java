package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dao.CrudUtil ;
import lk.ijse.recycle.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {

    public double getOrderTotalValue(int orderId) throws SQLException, ClassNotFoundException;
}
