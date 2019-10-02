package com.training.java.model.dao.mapper;

import com.training.java.model.entity.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DishMapper implements ObjectMapper<Dish>{
    @Override
    public Dish extractFromResultSet(ResultSet rs) throws SQLException {
        Dish dish = new Dish();
        dish.setId(rs.getInt("id"));
        dish.setFileName(rs.getString("file_name"));
        dish.setNameUkr(rs.getString("name_ukr"));
        dish.setName(rs.getString("name"));
        dish.setPrice(rs.getBigDecimal("price"));

        return dish;
    }

    @Override
    public Dish makeUnique(Map<Integer, Dish> cache,
                           Dish dish) {
        cache.putIfAbsent(dish.getId(), dish);
        return cache.get(dish.getId());
    }
}
