package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.DeliveryBO;
import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.CustomerDAO;
import lk.ijse.recycle.dao.custom.DeliveryDAO;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.dto.DeliveryDto;
import lk.ijse.recycle.entity.Customer;
import lk.ijse.recycle.entity.Delivery;
import lk.ijse.recycle.entity.Material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Delivery);


    public boolean saveDelivery(DeliveryDto dto) throws SQLException, ClassNotFoundException {
        return deliveryDAO.save(
                new Delivery(
                        dto.getDeliveryId(),
                        dto.getOrderId(),
                        dto.getDetailsLocation()
                )
        );
    }

    public boolean updateDelivery(DeliveryDto dto) throws SQLException, ClassNotFoundException {
        return deliveryDAO.update(
                new Delivery(
                        dto.getDeliveryId(),
                        dto.getOrderId(),
                        dto.getDetailsLocation()
                )
        );
    }

    public boolean deleteDelivery(int deliveryId) throws SQLException, ClassNotFoundException {
        return deliveryDAO.delete(deliveryId);
    }


    public DeliveryDto searchDelivery(String contact) throws SQLException, ClassNotFoundException {
        Delivery delivery = deliveryDAO.search(contact);

        if (delivery == null) return null;

        return new DeliveryDto(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getDetailsLocation()
        );
    }



    public DeliveryDto searchDelivery(int deliveryId) throws SQLException, ClassNotFoundException {
    Delivery delivery = deliveryDAO.search(String.valueOf(deliveryId));

        if (delivery == null) return null;

        return new DeliveryDto(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getDetailsLocation()
        );
    }

    public List<DeliveryDto> getAllDelivery() throws SQLException, ClassNotFoundException {
        List<Delivery> deliveryList = deliveryDAO.getAll();
        ArrayList<DeliveryDto> dtoList = new ArrayList<>();

        if (deliveryList != null && !deliveryList.isEmpty()) {
            for (Delivery delivery : deliveryList) {
                dtoList.add(new DeliveryDto(
                        delivery.getDeliveryId(),
                        delivery.getOrderId(),
                        delivery.getDetailsLocation()
                ));
            }
        }
        return dtoList;
    }




    public int getNextDeliveryId() throws SQLException, ClassNotFoundException {
        return deliveryDAO.getNextId();
    }
}




