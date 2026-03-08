package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.db.DBConnection;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dao.CrudUtil ;
import lk.ijse.recycle.entity.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductDAO extends CrudDAO<Product> {


   public List<MaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException;

    public boolean manufacture(String productId, int quantity) throws SQLException, ClassNotFoundException;

    public double getMaterialTotalStock(Integer materialId) throws SQLException, ClassNotFoundException;

    public boolean reduceStock(String productId, int quantity) throws SQLException, ClassNotFoundException;

    public boolean hasStock(String productId, int quantity) throws SQLException, ClassNotFoundException;

    public boolean delete(String productId) throws SQLException, ClassNotFoundException;
}



