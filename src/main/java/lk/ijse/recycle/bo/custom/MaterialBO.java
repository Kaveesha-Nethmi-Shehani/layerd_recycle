package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dto.MaterialDto;

import java.sql.SQLException;
import java.util.List;

public interface MaterialBO extends SuperBO {


    public boolean saveMaterial(MaterialDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updateMaterial(MaterialDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteMaterial(int materialId) throws SQLException, ClassNotFoundException ;

    public MaterialDto searchMaterial(int materialId) throws SQLException, ClassNotFoundException ;

    public List<MaterialDto> getAllMaterial() throws SQLException, ClassNotFoundException ;

  //  public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException ;

}



