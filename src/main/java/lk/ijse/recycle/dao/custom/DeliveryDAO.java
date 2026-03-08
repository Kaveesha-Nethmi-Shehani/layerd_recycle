package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.entity.Delivery;

import java.sql.SQLException;

public interface DeliveryDAO extends CrudDAO <Delivery>{

    public Delivery searchD(int deliveryId) throws SQLException, ClassNotFoundException;

}
