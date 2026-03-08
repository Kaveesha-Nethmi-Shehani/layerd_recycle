/*
package lk.ijse.recycle.model;

import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialModel {

    public boolean save(MaterialDto dto) throws SQLException {
        String sql = "INSERT INTO materials (material_name, price_per_kg) VALUES (?, ?)";
        return CrudUtil.executeUpdate(sql, dto.getMaterialName(), dto.getPricePerKg());
    }

    public boolean update(MaterialDto dto) throws SQLException {
        String sql = "UPDATE materials SET material_name = ?, price_per_kg = ? WHERE material_id = ?";
        return CrudUtil.executeUpdate(sql, dto.getMaterialName(), dto.getPricePerKg(), dto.getMaterialId());
    }

    public boolean delete(int materialId) throws SQLException {
        String sql = "DELETE FROM materials WHERE material_id = ?";
        return CrudUtil.executeUpdate(sql, materialId);
    }

    public MaterialDto search(int materialId) throws SQLException {
        String sql = "SELECT * FROM materials WHERE material_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, materialId);

        if (rs.next()) {
            return new MaterialDto(
                rs.getInt("material_id"),
                rs.getString("material_name"),
                rs.getDouble("price_per_kg")
            );
        }
        return null;
    }

    public List<MaterialDto> getAll() throws SQLException {
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


    public double getTotalStock(int materialId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(item_kg), 0) as total_kg FROM recycle_item WHERE material_id = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, materialId);
        if (rs.next()) {
            return rs.getDouble("total_kg");
        }
        return 0;
    }


    public List<MaterialDto> getAllWithStock() throws SQLException {
        String sql = "SELECT m.*, COALESCE(SUM(r.item_kg), 0) as total_kg " +
                     "FROM materials m " +
                     "LEFT JOIN recycle_item r ON m.material_id = r.material_id " +
                     "GROUP BY m.material_id, m.material_name, m.price_per_kg " +
                     "ORDER BY m.material_name";
        ResultSet rs = CrudUtil.executeQuery(sql);

        List<MaterialDto> list = new ArrayList<>();
        while (rs.next()) {
            MaterialDto dto = new MaterialDto(
                rs.getInt("material_id"),
                rs.getString("material_name"),
                rs.getDouble("price_per_kg")
            );
            list.add(dto);
        }
        return list;
    }
}
*/
