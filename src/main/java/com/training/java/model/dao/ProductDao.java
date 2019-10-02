package com.training.java.model.dao;

import com.training.java.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {
    public Optional<Product> findByProduct(String product);

    public int getIdByProductName(String product);

    public List<String> getAllProductsFromOrder(int orderID);

    public int getAmountOfProduct(Product product);
}
