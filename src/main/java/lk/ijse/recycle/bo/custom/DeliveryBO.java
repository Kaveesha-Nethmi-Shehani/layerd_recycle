package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dao.custom.DeliveryDAO;
import lk.ijse.recycle.dto.DeliveryDto;

import javax.xml.catalog.CatalogResolver;
import java.sql.SQLException;
import java.util.List;

public interface DeliveryBO extends SuperBO {


    public boolean saveDelivery(DeliveryDto dto) throws SQLException, ClassNotFoundException;

    public boolean updateDelivery(DeliveryDto dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteDelivery(int deliveryId) throws SQLException, ClassNotFoundException ;

    public DeliveryDto searchDelivery(String contact) throws SQLException, ClassNotFoundException ;

    public List<DeliveryDto> getAllDelivery() throws SQLException, ClassNotFoundException;

    public int getNextDeliveryId() throws SQLException, ClassNotFoundException ;
}


