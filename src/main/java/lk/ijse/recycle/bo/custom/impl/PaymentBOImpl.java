package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.PaymentBO;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.OrderDAO;
import lk.ijse.recycle.dao.custom.PaymentDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Payment;
//import lk.ijse.recycle.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO =(PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Payment);


    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
     return paymentDAO.save(
             new Payment(
                     dto.getPaymentId(),
                     dto.getOrderId(),
                     dto.getInvoice(),
                     dto.getPaymentValue(),
                     dto.getPaymentDate()
             )
     );
    }

    public boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
       return paymentDAO.update(
               new Payment(
                       dto.getPaymentId(),
                       dto.getOrderId(),
                       dto.getInvoice(),
                       dto.getPaymentValue(),
                       dto.getPaymentDate()
               )
       );
    }



    public boolean deletePayment(int paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }


    public PaymentDto searchPayment(String contact) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.search(contact);

        if (payment == null) {
            return new PaymentDto(
                    payment.getPaymentId(),
                    payment.getOrderId(),
                    payment.getInvoice(),
                    payment.getPaymentValue(),
                    payment.getPaymentDate()

            );
        }
        return null;
    }

    public PaymentDto searchPayment(int paymentId) throws SQLException, ClassNotFoundException {
       Payment payment = paymentDAO.search(String.valueOf(paymentId));

       if (payment == null) {
           return new PaymentDto(
                   payment.getPaymentId(),
                   payment.getOrderId(),
                   payment.getInvoice(),
                   payment.getPaymentValue(),
                   payment.getPaymentDate()
           );
       }
        return null;
    }

    public List<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> paymentList = (ArrayList<Payment>) paymentDAO.getAll();
        ArrayList<PaymentDto> dtoList = new ArrayList<>();

        for (Payment payment : paymentList) {
            dtoList.add(new PaymentDto(
                    payment.getPaymentId(),
                    payment.getOrderId(),
                    payment.getInvoice(),
                    payment.getPaymentValue(),
                    payment.getPaymentDate()


            ));
        }
        return dtoList;
    }

    public int getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    public double getOrderTotalPaymentValue(int orderId) throws SQLException, ClassNotFoundException {
        return paymentDAO.getOrderTotalValue(orderId);
    }
}






