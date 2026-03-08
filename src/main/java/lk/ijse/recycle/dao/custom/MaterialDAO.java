package lk.ijse.recycle.dao.custom;

import lk.ijse.recycle.dao.CrudDAO;
import lk.ijse.recycle.entity.Material;

import java.sql.SQLException;

public interface MaterialDAO extends CrudDAO<Material> {
   public Material getAllWithStockMaterial() throws SQLException, ClassNotFoundException;

   public double getTotalStockMaterial(int materialId) throws SQLException,ClassNotFoundException;
}
