package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dao.CrudUtil ;
import lk.ijse.recycle.entity.Customer;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer>{

    public Customer searchCustomerById(int id) throws SQLException, ClassNotFoundException;

}
