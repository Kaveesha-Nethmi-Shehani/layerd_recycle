package lk.ijse.recycle.bo.custom.impl;

import lk.ijse.recycle.bo.custom.ProductBO;
import lk.ijse.recycle.dao.DAOFactory;
import lk.ijse.recycle.dao.custom.ProductDAO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Product);


    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDAO.save(
                new Product(
                        dto.getProductId(),
                        dto.getProduct_name(),
                        dto.getMaterialId(),
                        dto.getMaterialName(),
                        dto.getKgPerUnit(),
                        dto.getSellingPrice(),
                        dto.getStockQuantity()

                )
        );
    }

    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDAO.update(
                new Product(
                        dto.getProductId(),
                        dto.getProduct_name(),
                        dto.getMaterialId(),
                        dto.getMaterialName(),
                        dto.getKgPerUnit(),
                        dto.getSellingPrice(),
                        dto.getStockQuantity()


                )
        );

    }

    @Override
    public boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        return productDAO.delete(productId);
    }

    public ProductDto searchProduct(String productId) throws SQLException, ClassNotFoundException {

        Product product = productDAO.search(productId);

        if (product != null) {
            return new ProductDto(
                    product.getProductId(),
                    product.getProduct_name(),
                    product.getMaterialId(),
                    product.getMaterialName(),
                    product.getKgPerUnit(),
                    product.getSellingPrice(),
                    product.getStockQuantity()
            );
        }

        return null;
    }

    public List<ProductDto> getAllProduct() throws SQLException, ClassNotFoundException {
        ArrayList<Product> productList = (ArrayList<Product>) productDAO.getAll();
        ArrayList<ProductDto> dtoList = new ArrayList<>();

        for (Product product : productList) {
            dtoList.add(new ProductDto(
                    product.getProductId(),
                    product.getProduct_name(),
                    product.getMaterialId(),
                    product.getMaterialName(),
                    product.getKgPerUnit(),
                    product.getSellingPrice(),
                    product.getStockQuantity()


            ));
        }
        return dtoList;
    }


    public int getNextProductId() throws SQLException, ClassNotFoundException {
        return productDAO.getNextId();
    }


    public List<MaterialDto> getAllProductMaterials() throws SQLException, ClassNotFoundException {
        return productDAO.getAllMaterials();
    }


    public boolean manufactureProduct(String productId, int quantity) throws SQLException, ClassNotFoundException {
        return productDAO.manufacture(productId, quantity);
    }


    public double getMaterialTotalProductStock(Integer materialId) throws SQLException, ClassNotFoundException {
        return productDAO.getMaterialTotalStock(materialId);
    }

    public boolean reduceProductStock(String productId, int quantity) throws SQLException, ClassNotFoundException {
        return productDAO.reduceStock(productId, quantity);
    }

    public boolean hasProductStock(String productId, int quantity) throws SQLException, ClassNotFoundException {
        return productDAO.hasStock(productId, quantity);
    }
}


