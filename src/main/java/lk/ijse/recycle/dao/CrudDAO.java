package lk.ijse.recycle.dao;

import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dto.ProductDto;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{

    public boolean save(T dto) throws SQLException, ClassNotFoundException;

    public boolean update(T dto) throws SQLException, ClassNotFoundException;

    public boolean delete(int customerId) throws SQLException, ClassNotFoundException;

    public  T search(String contact) throws SQLException, ClassNotFoundException;

    public List<T> getAll() throws SQLException, ClassNotFoundException;

    public int getNextId() throws SQLException, ClassNotFoundException;

}


