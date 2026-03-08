package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.OrderBO;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.OrderDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Order);


    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(
                new Order(
                        dto.getOrderId(),
                        dto.getProductId(),
                        dto.getCustomerId(),
                        dto.getQuantity(),
                        dto.getTotalPrice(),
                        dto.getOrderDate()

                )
        );
    }

    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException {
        List<Order> orders = orderDAO.getAll();
        List<OrderDto> dtoList = new ArrayList<>();

        for (Order o : orders) {
            dtoList.add(new OrderDto(
                    o.getOrderId(),
                    o.getProductId(),
                    o.getProduct_name(),
                    o.getCustomerId(),
                    o.getCustomerName(),
                    o.getQuantity(),
                    o.getTotalPrice(),
                    o.getOrderDate()
            ));
        }

        return dtoList;
    }

    public boolean updateOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(
                new Order(
                        dto.getOrderId(),
                        dto.getProductId(),
                        dto.getCustomerId(),
                        dto.getQuantity(),
                        dto.getTotalPrice(),
                        dto.getOrderDate()
                )
        );
    }

    public boolean deleteOrder(int orderId) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(orderId);
    }

    public OrderDto searchOrder(String contact) throws SQLException, ClassNotFoundException {
        Order order = orderDAO.search(String.valueOf(Integer.parseInt(contact)));

        if (order == null) return null;
        return new OrderDto(
                order.getOrderId(),
                order.getProductId(),
                order.getCustomerId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate()
        );
    }

    public int getNextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getNextId();
    }

    public List<ProductDto> getAvailableProductsOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.getAvailableProducts();
    }

    public List<CustomerDto> getAllCustomersOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllCustomers();
    }
}





