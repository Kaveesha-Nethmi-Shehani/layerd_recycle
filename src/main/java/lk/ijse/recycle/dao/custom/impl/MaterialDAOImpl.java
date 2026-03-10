package lk.ijse.recycle.dao.custom.impl;

import lk.ijse.recycle.dao.custom.MaterialDAO;
import lk.ijse.recycle.entity.Material;
import lk.ijse.recycle.dao.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {

    public boolean save(Material dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO materials (material_name, price_per_kg) VALUES (?, ?)",
                dto.getMaterialName(),
                dto.getPricePerKg());
    }

    public boolean update(Material dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE materials SET material_name = ?, price_per_kg = ? WHERE material_id = ?",
                dto.getMaterialName(),
                dto.getPricePerKg(),
                dto.getMaterialId());
    }

    public boolean delete(int materialId) throws SQLException, ClassNotFoundException {
        //String sql = "DELETE FROM materials WHERE material_id = ?";
        return CrudUtil.execute("DELETE FROM materials WHERE material_id = ?", materialId);
    }

    @Override
    public Material search(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    public Material search(int materialId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM materials WHERE material_id = ?", materialId);

        if (rs.next()) {
            return new Material(
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("price_per_kg")
            );
        }
        return null;
    }

    public List<Material> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM materials ORDER BY material_name");

        List<Material> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Material(
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getDouble("price_per_kg")
            ));
        }
        return list;
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        return 0;
    }


    public double getTotalStockMaterial(int materialId) throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute(
                "SELECT COALESCE(SUM(item_kg), 0) as total_kg FROM recycle_item WHERE material_id = ?",
                materialId
        );

        if (rs.next()) {
            return rs.getDouble("total_kg");
        }
        return 0;
    }
//    public Material getAllWithStockMaterial() throws SQLException, ClassNotFoundException {
//        ResultSet rs = CrudUtil.execute("SELECT m.*, COALESCE(SUM(r.item_kg), 0) as total_kg " +
//                "FROM materials m " +
//                "LEFT JOIN recycle_item r ON m.material_id = r.material_id " +
//                "GROUP BY m.material_id, m.material_name, m.price_per_kg " +
//                "ORDER BY m.material_name");
//
//        List<Material> list = new ArrayList<>();
//        while (rs.next()) {
//            Material dto = new Material(
//                    rs.getInt("material_id"),
//                    rs.getString("material_name"),
//                    rs.getDouble("price_per_kg")
//            );
//            list.add(dto);
//        }
//        return (Material) list;
//    }
}





