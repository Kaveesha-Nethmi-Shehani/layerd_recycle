package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dto.CustomerDto;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {


    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteCustomer(int customerId) throws SQLException, ClassNotFoundException ;

    public CustomerDto searchCustomer(String contact) throws SQLException, ClassNotFoundException ;

    public CustomerDto searchCustomerById(int id) throws SQLException, ClassNotFoundException ;

    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException ;

    public int getNextCustomerId() throws SQLException, ClassNotFoundException ;


}
