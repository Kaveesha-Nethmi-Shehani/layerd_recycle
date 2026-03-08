/*
package lk.ijse.recycle.model;

import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.RecycleItemDto;
import lk.ijse.recycle.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecycleItemModel {

    public boolean saveRecycleItems(RecycleItemDto dto) throws SQLException {
        String sql = "INSERT INTO recycle_item(material_id, item_kg, item_price, date) VALUES (?, ?, ?, ?)";
        return CrudUtil.executeUpdate(sql,
                dto.getMaterialId(),
                dto.getItemKg(),
                dto.getItemPrice(),
                dto.getDate()
        );
    }

    public boolean updateRecycleItems(RecycleItemDto dto) throws SQLException {
        String sql = "UPDATE recycle_item SET material_id = ?, item_kg = ?, item_price = ?, date = ? WHERE item_id = ?";
        return CrudUtil.executeUpdate(sql,
                dto.getMaterialId(),
                dto.getItemKg(),
                dto.getItemPrice(),
                dto.getDate(),
                dto.getItemId()
        );
    }

    public boolean deleteRecycleItem(int id) throws SQLException {
        String sql = "DELETE FROM recycle_item WHERE item_id = ?";
        return CrudUtil.executeUpdate(sql, id);
    }

    public RecycleItemDto searchRecycleItems(int id) throws SQLException {
        String sql = "SELECT r.*, m.material_name FROM recycle_item r " +
                     "LEFT JOIN materials m ON r.material_id = m.material_id " +
                     "WHERE r.item_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, id);

        if (rs.next()) {
            return new RecycleItemDto(
                    rs.getInt("item_id"),
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("item_kg"),
                    rs.getDouble("item_price"),
                    rs.getString("date")
            );
        }
        return null;
    }

    public List<RecycleItemDto> getAllItems() throws SQLException {
        String sql = "SELECT r.*, m.material_name FROM recycle_item r " +
                     "LEFT JOIN materials m ON r.material_id = m.material_id " +
                     "ORDER BY r.item_id DESC";
        ResultSet rs = CrudUtil.executeQuery(sql);

        List<RecycleItemDto> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new RecycleItemDto(
                    rs.getInt("item_id"),
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("item_kg"),
                    rs.getDouble("item_price"),
                    rs.getString("date")
            ));
        }
        return list;
    }

    public int getNextItemId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(item_id), 0) + 1 AS next_id FROM recycle_item";
        ResultSet rs = CrudUtil.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("next_id");
        }
        return 1;
    }


    public List<MaterialDto> getAllMaterials() throws SQLException {
        String sql = "SELECT * FROM materials ORDER BY material_name";
        ResultSet rs = CrudUtil.executeQuery(sql);

        List<MaterialDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new MaterialDto(
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("price_per_kg")
            ));
        }
        return list;
    }


    public double getMaterialTotalStock(int materialId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(item_kg), 0) as total_kg FROM recycle_item WHERE material_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, materialId);
        if (rs.next()) {
            return rs.getDouble("total_kg");
        }
        return 0;
    }
}





*/
