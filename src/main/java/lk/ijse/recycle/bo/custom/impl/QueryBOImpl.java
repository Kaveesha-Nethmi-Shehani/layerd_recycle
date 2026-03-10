package lk.ijse.recycle.bo.custom.impl;


import lk.ijse.recycle.bo.custom.QueryBO;
import lk.ijse.recycle.dao.custom.QueryDAO;
import lk.ijse.recycle.dao.DAOFactory;

import java.sql.SQLException;

public class QueryBOImpl implements QueryBO {


    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException {
        return queryDAO.getTotalStockMaterial(materialId);//meke QueryDAO eke meke methoda name ekata samana Qury ekak hduwa.
        //ethkota getTotalWithStockMaterial & getTotalStockMaterial kelaya dekak thynwa

    }


}
