//
//package lk.ijse.recycle.model;
//
//import lk.ijse.recycle.dto.CustomerDto;
//import lk.ijse.recycle.util.CrudUtil;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomerModel {
//
//    public boolean saveCustomer(CustomerDto dto) throws SQLException {
//        String sql = "INSERT INTO customer (customer_name, customer_contact, customer_date) VALUES (?, ?, ?)";
//        return CrudUtil.executeUpdate(sql, dto.getCustomerName(), dto.getCustomerContact(), dto.getCustomerDate());
//    }
//
//    public boolean updateCustomer(CustomerDto dto) throws SQLException {
//        String sql = "UPDATE customer SET customer_name = ?, customer_contact = ?, customer_date = ? WHERE customer_id = ?";
//        return CrudUtil.executeUpdate(sql, dto.getCustomerName(), dto.getCustomerContact(), dto.getCustomerDate(), dto.getCustomerId());
//    }
//
//    public boolean deleteCustomer(int customerId) throws SQLException {
//        String sql = "DELETE FROM customer WHERE customer_id = ?";
//        return CrudUtil.executeUpdate(sql, customerId);
//    }
//
//    public CustomerDto searchCustomer(String contact) throws SQLException {
//        String sql = "SELECT * FROM customer WHERE customer_contact = ?";
//        ResultSet rs = CrudUtil.executeQuery(sql, contact);
//
//        if (rs.next()) {
//            return new CustomerDto(
//                    rs.getInt("customer_id"),
//                    rs.getString("customer_name"),
//                    rs.getString("customer_contact"),
//                    rs.getString("customer_date")
//            );
//        }
//        return null;
//    }
//
//    public CustomerDto searchCustomerById(int id) throws SQLException {
//        String sql = "SELECT * FROM customer WHERE customer_id = ?";
//        ResultSet rs = CrudUtil.executeQuery(sql, id);
//
//        if (rs.next()) {
//            return new CustomerDto(
//                    rs.getInt("customer_id"),
//                    rs.getString("customer_name"),
//                    rs.getString("customer_contact"),
//                    rs.getString("customer_date")
//            );
//        }
//        return null;
//    }
//
//    public List<CustomerDto> getAllCustomers() throws SQLException {
//        String sql = "SELECT * FROM customer";
//        ResultSet rs = CrudUtil.executeQuery(sql);
//        List<CustomerDto> list = new ArrayList<>();
//
//        while (rs.next()) {
//            list.add(new CustomerDto(
//                    rs.getInt("customer_id"),
//                    rs.getString("customer_name"),
//                    rs.getString("customer_contact"),
//                    rs.getString("customer_date")
//            ));
//        }
//        return list;
//    }
//
//    public int getNextCustomerId() throws SQLException {
//        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'customer'";
//        PreparedStatement CrudUtil = null;
//        ResultSet rs = CrudUtil.executeQuery(sql);
//        if (rs.next()) {
//            return rs.getInt("AUTO_INCREMENT");
//        }
//        return 1;
//    }
//}
