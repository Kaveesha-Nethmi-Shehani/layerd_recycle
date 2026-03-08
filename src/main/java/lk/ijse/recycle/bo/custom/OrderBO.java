package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Order;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderBO extends SuperBO {


    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updateOrder(OrderDto dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteOrder(int orderId) throws SQLException, ClassNotFoundException;

    public int getNextOrderId() throws SQLException, ClassNotFoundException ;

  public OrderDto searchOrder(String contact) throws SQLException, ClassNotFoundException;

    public List<ProductDto> getAvailableProductsOrder() throws SQLException, ClassNotFoundException ;

    public List<CustomerDto> getAllCustomersOrder() throws SQLException, ClassNotFoundException ;

    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException;

}
