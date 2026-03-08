
package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.CustomerDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute("INSERT INTO customer (customer_name, customer_contact, customer_date) VALUES (?, ?, ?)",
                dto.getCustomerName(),
                dto.getCustomerContact(),
                dto.getCustomerDate());
    }

    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute( "UPDATE customer SET customer_name = ?, customer_contact = ?, customer_date = ? WHERE customer_id = ?",
                dto.getCustomerName(),
                dto.getCustomerContact(),
                dto.getCustomerDate(),
                dto.getCustomerId());
    }

    public boolean delete(int customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM customer WHERE customer_id = ?", customerId);
    }

    public Customer search(String contact) throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute("SELECT * FROM customer WHERE customer_contact = ?");

        if (rs.next()) {
            return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_contact"),
                    rs.getString("customer_date")
            );
        }
        return null;
    }

    public Customer searchCustomerById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        ResultSet rs = CrudUtil.execute(sql, id);

        if (rs.next()) {
            return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_contact"),
                    rs.getString("customer_date")
            );
        }
        return null;
    }

    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer");
        List<Customer> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_contact"),
                    rs.getString("customer_date")
            ));
        }
        return list;
    }

    public int getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'customer'");
        if (rs.next()) {
            return rs.getInt("AUTO_INCREMENT");
        }
        return 1;
    }
}
