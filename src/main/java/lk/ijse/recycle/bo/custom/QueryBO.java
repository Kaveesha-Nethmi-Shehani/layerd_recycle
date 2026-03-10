package lk.ijse.recycle.bo.custom;

import java.sql.SQLException;

import lk.ijse.recycle.bo.SuperBO;

public interface QueryBO extends SuperBO {

    public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException;

}
