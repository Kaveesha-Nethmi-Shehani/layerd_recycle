package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {


//    public List<ProductDto> getAvailableProducts() throws SQLException, ClassNotFoundException;

     public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;




//   public  OrderDto search(int orderId) throws SQLException, ClassNotFoundException;

    //public List<Order> getAll() throws SQLException, ClassNotFoundException ;
}

