package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.dto.DeliveryDto;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.RecycleItemDto;
import lk.ijse.recycle.dao.CrudUtil ;
import lk.ijse.recycle.entity.Material;
import lk.ijse.recycle.entity.RecycleItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RecycleItemDAO extends CrudDAO<RecycleItem> {


    public boolean save(RecycleItem dto) throws SQLException, ClassNotFoundException ;

    public boolean update(RecycleItem dto) throws SQLException, ClassNotFoundException ;

    public boolean delete(int id) throws SQLException, ClassNotFoundException ;

//    public RecycleItem search(String id) throws SQLException, ClassNotFoundException ;

//    public List<RecycleItem> getAll() throws SQLException, ClassNotFoundException ;

    public int getNextId() throws SQLException, ClassNotFoundException ;


    public List<Material> getAllMaterials() throws SQLException, ClassNotFoundException ;


    public double getMaterialTotalStock(int materialId) throws SQLException, ClassNotFoundException ;
}


