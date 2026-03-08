package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.ProductDAO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dto.RecycleItemDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RecycleItemBO  extends SuperBO {

    public boolean saveRecycleItem(RecycleItemDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updateRecycleItem(RecycleItemDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteRecycleItem(int id) throws SQLException, ClassNotFoundException ;

    public RecycleItemDto searchRecycleItem(String id) throws SQLException, ClassNotFoundException ;

    public List<RecycleItemDto> getAllRecycleItem() throws SQLException, ClassNotFoundException ;

    public int getNextRecycleItemId() throws SQLException, ClassNotFoundException ;


    public List<MaterialDto> getAllRecycleItemMaterials() throws SQLException, ClassNotFoundException ;


   public double getMaterialRecycleItemTotalStock(int materialId) throws SQLException, ClassNotFoundException ;
}
