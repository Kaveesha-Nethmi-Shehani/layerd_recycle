package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.RecycleItemBO;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.RecycleItemDAO;
import lk.ijse.recycle.db.DBConnection;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dto.RecycleItemDto;
import lk.ijse.recycle.entity.Material;
import lk.ijse.recycle.entity.RecycleItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecycleItemBOImpl implements RecycleItemBO {

    RecycleItemDAO recycleItemDAO = (RecycleItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RecycleItems);

    public boolean saveRecycleItem(RecycleItemDto dto) throws SQLException, ClassNotFoundException {
        return  recycleItemDAO.save(
                new RecycleItem(
                        dto.getMaterialId(),
                        dto.getItemKg(),
                        dto.getItemPrice(),
                        dto.getDate(),
                        dto.getItemId()
                )

        );
    }

    public boolean updateRecycleItem(RecycleItemDto dto) throws SQLException, ClassNotFoundException {
        return  recycleItemDAO.update(
                new RecycleItem(
                        dto.getMaterialId(),
                        dto.getItemKg(),
                        dto.getItemPrice(),
                        dto.getDate(),
                        dto.getItemId()


                )
        );
    }

    public boolean deleteRecycleItem(int id) throws SQLException, ClassNotFoundException {
        //String sql = "DELETE FROM recycle_item WHERE item_id = ?";
        return CrudUtil.execute("DELETE FROM recycle_item WHERE item_id = ?", id);
    }

    public RecycleItemDto searchRecycleItem(String id) throws SQLException, ClassNotFoundException {

        RecycleItem recycleItem = recycleItemDAO.search(id);

        if (recycleItem != null) {
            return new RecycleItemDto(
                    recycleItem.getMaterialId(),
                    recycleItem.getItemKg(),
                    recycleItem.getItemPrice(),
                    recycleItem.getDate(),
                    recycleItem.getItemId()
            );
        }

        return null;
    }

    public List<RecycleItemDto> getAllRecycleItem() throws SQLException, ClassNotFoundException {

        List<RecycleItem> recycleItemList = recycleItemDAO.getAll();
        List<RecycleItemDto> dtoList = new ArrayList<>();

        for (RecycleItem recycleItem : recycleItemList) {

            dtoList.add(
                    new RecycleItemDto(
                            recycleItem.getMaterialId(),
                            recycleItem.getMaterialName(),
                            recycleItem.getItemKg(),
                            recycleItem.getItemPrice(),
                            recycleItem.getDate(),
                            recycleItem.getItemId()
                    )
            );
        }

        return dtoList;
    }

    public int getNextRecycleItemId() throws SQLException, ClassNotFoundException {
      return recycleItemDAO.getNextId();
    }

    public List<MaterialDto> getAllRecycleItemMaterials() throws SQLException, ClassNotFoundException {

        List<Material> materialList = recycleItemDAO.getAllMaterials();
        List<MaterialDto> dtoList = new ArrayList<>();

        for (Material material : materialList) {
            dtoList.add(
                    new MaterialDto(
                            material.getMaterialId(),
                            material.getMaterialName(),
                            material.getUnitPrice()
                    )
            );
        }

        return dtoList;
    }

    public double getMaterialRecycleItemTotalStock(int materialId) throws SQLException, ClassNotFoundException {
        return recycleItemDAO.getMaterialTotalStock(materialId);
    }
}

