package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dao.SuperDAO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Material;
import lk.ijse.recycle.entity.Order;
import lk.ijse.recycle.entity.RecycleItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    public Material getAllWithStockMaterial() throws SQLException, ClassNotFoundException ;

    public OrderDto search(int orderId) throws SQLException, ClassNotFoundException ;

    public List<ProductDto> getAvailableProducts() throws SQLException, ClassNotFoundException;

    public List<MaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException;

    public RecycleItem search(String id) throws SQLException, ClassNotFoundException ;

    public List<RecycleItem> getAll() throws SQLException, ClassNotFoundException ;





//    public List<Order> getAll() throws SQLException, ClassNotFoundException ;



}
