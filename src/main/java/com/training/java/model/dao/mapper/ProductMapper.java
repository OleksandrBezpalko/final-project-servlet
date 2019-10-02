package com.training.java.model.dao.mapper;


import com.training.java.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProductMapper implements ObjectMapper<Product> {
    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("product_id"));
        product.setAmountHave(rs.getInt("amount_have"));
        product.setMaxAmount(rs.getInt("max_amount"));
        product.setMinAmount(rs.getInt("min_amount"));
        product.setProductInBox(rs.getInt("product_in_box"));
        product.setProduct(rs.getString("product"));

        return product;
    }

    @Override
    public Product makeUnique(Map<Integer, Product> cache,
                              Product product) {
        cache.putIfAbsent(product.getId(), product);
        return cache.get(product.getId());
    }
}
