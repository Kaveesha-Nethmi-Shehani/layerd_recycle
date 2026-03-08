package lk.ijse.recycle.bo.custom;

import lk.ijse.recycle.bo.SuperBO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductBO extends SuperBO {


    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException ;

    public ProductDto searchProduct(String productId) throws SQLException, ClassNotFoundException;

    public List<ProductDto> getAllProduct() throws SQLException, ClassNotFoundException ;


    public List<MaterialDto> getAllProductMaterials() throws SQLException, ClassNotFoundException ;


    public boolean manufactureProduct(String productId, int quantity) throws SQLException, ClassNotFoundException;


    public double getMaterialTotalProductStock(Integer materialId) throws SQLException, ClassNotFoundException ;

    public boolean reduceProductStock(String productId, int quantity) throws SQLException, ClassNotFoundException ;

    public boolean hasProductStock(String productId, int quantity) throws SQLException, ClassNotFoundException ;


}

