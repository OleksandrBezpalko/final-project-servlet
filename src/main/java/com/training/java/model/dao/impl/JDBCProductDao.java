package com.training.java.model.dao.impl;

import com.training.java.model.dao.ProductDao;
import com.training.java.model.dao.mapper.ProductMapper;
import com.training.java.model.entity.Product;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class JDBCProductDao implements ProductDao {
    private Connection connection;
    private ResourceBundle mybundle;

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
        mybundle = ResourceBundle.getBundle("sqlQuery/productDao");
    }

    @Override
    public Optional<Product> findByProduct(String product) {
        Optional<Product> product1 = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("findByProduct"))) {
            ps.setString(1, product);
            ResultSet rs = ps.executeQuery();
            ProductMapper productMapper = new ProductMapper();
            if (rs.next()) {
                product1 = Optional.of(productMapper
                        .extractFromResultSet(rs));
            }
            return product1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getIdByProductName(String product){
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("getIdByProductName"))) {
            ps.setString(1, product);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("product_id");
            }
            return rs.getInt("product_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<String> getAllProductsFromOrder(int orderID){
        List<String> nededproducts = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("getAllProductsFromOrder"))) {
            ps.setInt(1, orderID);
            ps.setInt(2, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nededproducts.add(rs.getString("product"));
            }
            return nededproducts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getAmountOfProduct(Product product) {
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("getAmountOfProduct"))) {
            ps.setString(1, product.getProduct());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("amount_have");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void create(Product entity) {
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll(){
        List<Product> products = new CopyOnWriteArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("findAll"));

            ProductMapper productMapper = new ProductMapper();

            while (rs.next()) {
                Product product;
                product = productMapper
                        .extractFromResultSet(rs);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Product entity){
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("update"))) {
            ps.setInt(1, entity.getAmountHave());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
