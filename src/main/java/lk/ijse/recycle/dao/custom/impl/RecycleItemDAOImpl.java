package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.RecycleItemDAO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.PaymentDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dto.RecycleItemDto;
import lk.ijse.recycle.dao.CrudUtil;
import lk.ijse.recycle.entity.Material;
import lk.ijse.recycle.entity.RecycleItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecycleItemDAOImpl implements RecycleItemDAO {

    public boolean save(RecycleItem dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO recycle_item(material_id, item_kg, item_price, date) VALUES (?, ?, ?, ?)",
                dto.getMaterialId(),
                dto.getItemKg(),
                dto.getItemPrice(),
                dto.getDate());
    }

    public boolean update(RecycleItem dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE recycle_item SET material_id = ?, item_kg = ?, item_price = ?, date = ? WHERE item_id = ?",
                dto.getMaterialId(),
                dto.getItemKg(),
                dto.getItemPrice(),
                dto.getDate(),
                dto.getItemId());
    }

    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM recycle_item WHERE item_id = ?", id);
    }

    public RecycleItem search(String id) throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT r.*, m.material_name FROM recycle_item r " +
                "LEFT JOIN materials m ON r.material_id = m.material_id " +
                "WHERE r.item_id = ?", id);

        if (rs.next()) {
            return new RecycleItem(
                    rs.getInt("item_id"),
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("item_kg"),
                    rs.getDouble("item_price"),
                    rs.getString("date"));
        }
        return null;
    }

    public List<RecycleItem> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT r.*, m.material_name FROM recycle_item r " +
                "LEFT JOIN materials m ON r.material_id = m.material_id " +
                "ORDER BY r.item_id DESC");

        List<RecycleItem> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new RecycleItem(
                    rs.getInt("item_id"),
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("item_kg"),
                    rs.getDouble("item_price"),
                    rs.getString("date")));
        }
        return list;
    }

    public int getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT COALESCE(MAX(item_id), 0) + 1 AS next_id FROM recycle_item");
        if (rs.next()) {
            return rs.getInt("next_id");
        }
        return 1;
    }

    public List<Material> getAllMaterials() throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM materials");

        List<Material> list = new ArrayList<>();

        while (rs.next()) {
            list.add(
                    new Material(
                            rs.getInt("material_id"),
                            rs.getString("material_name"),
                            rs.getDouble("price_per_kg")));
        }

        return list;
    }

    public double getMaterialTotalStock(int materialId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COALESCE(SUM(item_kg), 0) as total_kg FROM recycle_item WHERE material_id = ?", materialId);
        if (rs.next()) {
            return rs.getDouble("total_kg");
        }
        return 0;
    }

}
