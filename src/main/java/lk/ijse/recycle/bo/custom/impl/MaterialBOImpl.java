package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.MaterialBO;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.MaterialDAO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.entity.Material;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MaterialBOImpl implements MaterialBO {

    MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Material);

    public boolean saveMaterial(MaterialDto dto) throws SQLException, ClassNotFoundException {
        return materialDAO.save(
                new Material(
                        dto.getMaterialId(),
                        dto.getMaterialName(),
                        dto.getPricePerKg()
                )
        );
    }

    public boolean updateMaterial(MaterialDto dto) throws SQLException, ClassNotFoundException {
        return materialDAO.update(
                new Material(
                        dto.getMaterialId(),
                        dto.getMaterialName(),
                        dto.getPricePerKg()
                )
        );
    }

    public boolean deleteMaterial(int materialId) throws SQLException, ClassNotFoundException {
        return materialDAO.delete(materialId);
    }

    public MaterialDto searchMaterial(int materialId) throws SQLException, ClassNotFoundException {
        Material material = materialDAO.search(String.valueOf(materialId));

        if (material == null) return null;

        return new MaterialDto(
                material.getMaterialId(),
                material.getMaterialName(),
                material.getPricePerKg()
        );
    }

    public ArrayList<MaterialDto> getAllMaterial() throws SQLException, ClassNotFoundException {
        ArrayList<Material> materialList = (ArrayList<Material>) materialDAO.getAll();
        ArrayList<MaterialDto> dtoList = new ArrayList<>();

        for (Material material : materialList) {
            dtoList.add(new MaterialDto(
                    material.getMaterialId(),
                    material.getMaterialName(),
                    material.getPricePerKg()
            ));
        }
        return dtoList;
    }

//    public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException {
//       return materialDAO.getTotalStockMaterial(materialId);
//  }


    public int getNextMaterialId() throws SQLException, ClassNotFoundException {
        return materialDAO.getNextId();
    }

}