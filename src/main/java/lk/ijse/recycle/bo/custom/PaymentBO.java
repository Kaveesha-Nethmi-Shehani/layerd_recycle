package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {

    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException ;

    public boolean deletePayment(int paymentId) throws SQLException, ClassNotFoundException ;

    public PaymentDto searchPayment(int paymentId) throws SQLException, ClassNotFoundException;

    public List<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException ;

    public int getNextPaymentId() throws SQLException, ClassNotFoundException ;

    public double getOrderTotalPaymentValue(int orderId) throws SQLException, ClassNotFoundException ;
}
