package lk.ijse.recycle.bo.custom;

import java.sql.SQLException;

public interface QueryBO {

    public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException ;

}
