package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.DeliveryDAO;
import lk.ijse.recycle.dto.DeliveryDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAO {

    public boolean save(Delivery dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute ("INSERT INTO delivery (order_id, details_location) VALUES (?, ?)",
                dto.getOrderId(),
                dto.getDetailsLocation()
        );
    }

    public boolean update(Delivery dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.execute("UPDATE delivery SET order_id = ?, details_location = ? WHERE delivery_id = ?",
                dto.getOrderId(),
                dto.getDetailsLocation(),
                dto.getDeliveryId()
        );
    }


    public boolean delete(int deliveryId) throws SQLException, ClassNotFoundException {
        //String sql = "DELETE FROM delivery WHERE delivery_id = ?";
        return CrudUtil.execute ("DELETE FROM delivery WHERE delivery_id = ?", deliveryId);
    }

    @Override
    public Delivery search(String contact) throws SQLException {
        return null;
    }

    public Delivery searchD(int deliveryId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute ("SELECT * FROM delivery WHERE delivery_id = ?", deliveryId);

        if (rs.next()) {
            return new Delivery(
                    rs.getInt("delivery_id"),
                    rs.getInt("order_id"),
                    rs.getString("details_location")
            );
        }
        return null;
    }

    public List<Delivery> getAll() throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute("SELECT * FROM delivery");

        List<Delivery> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Delivery(
                    rs.getInt("delivery_id"),
                    rs.getInt("order_id"),
                    rs.getString("details_location")
            ));
        }
        return list;
    }

    public int getNextId() throws SQLException, ClassNotFoundException {
         ResultSet rs = CrudUtil.execute ("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'delivery'");
        if (rs.next()) {
            return rs.getInt("AUTO_INCREMENT");
        }
        return 1;
    }
}



