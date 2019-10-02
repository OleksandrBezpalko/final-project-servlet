package com.training.java.model.service;

import com.training.java.model.dao.DaoFactory;
import com.training.java.model.dao.ProductDao;
import com.training.java.model.entity.Product;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Product> findAllProducts() {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findAll();
        }


    }

    public int getProductId(String product) {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.getIdByProductName(product);
        }
    }

    public Optional<Product> getByProductName(String productName) {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findByProduct(productName);
        }

    }
    public int getAmountHave(Product product){
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.getAmountOfProduct(product);
        }
    }

    public List<String> getAllProductsFromOrder(int orderID) {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.getAllProductsFromOrder(orderID);
        }

    }
    public void update(Product product){
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.update(product);
        }
    }
}
