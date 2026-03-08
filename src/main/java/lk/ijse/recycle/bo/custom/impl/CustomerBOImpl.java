
package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.CustomerBO;
import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.CustomerDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Customer);

    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(
                new Customer(
                        dto.getCustomerName(),
                        dto.getCustomerContact(),
                        dto.getCustomerDate()
                )
        );
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(
                new Customer(
                        dto.getCustomerId(),
                        dto.getCustomerName(),
                        dto.getCustomerContact(),
                        dto.getCustomerDate()
                )
        );
    }

    public boolean deleteCustomer(int customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    public CustomerDto searchCustomer(String contact) throws SQLException, ClassNotFoundException {
       Customer customer =customerDAO.search(contact);

       if (customer == null) return null;

       return new CustomerDto(
               customer.getCustomerId(),
               customer.getCustomerName(),
               customer.getCustomerContact(),
               customer.getCustomerDate()
       );
    }

    public CustomerDto searchCustomerById(int id) throws SQLException, ClassNotFoundException {

        Customer customer = customerDAO.searchCustomerById(id);

        if (customer == null) return null;

        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerContact(),
                customer.getCustomerDate()
        );
    }

    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerList = (ArrayList<Customer>) customerDAO.getAll();
        ArrayList<CustomerDto> dtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            dtoList.add(new CustomerDto(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerContact(),
                    customer.getCustomerDate()
            ));
        }
        return dtoList;
    }

    public int getNextCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

}


