package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.QueryBO;
import lk.ijse.recycle.dao.custom.QueryDAO;
import lk.ijse.recycle.dao.custom.MaterialDAO;
import lk.ijse.recycle.dao.DAOFactory;

import java.sql.SQLException;

public class QueryBOImpl implements QueryBO {

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Query);
    MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Material);

    @Override
    public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException {
        return materialDAO.getTotalStockMaterial(materialId);
    }

}
