package com.training.java.model.dao.mapper;

import com.training.java.model.entity.OrderDish;
import com.training.java.model.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<OrderDish> {
    private UserService userService = new UserService();

    @Override
    public OrderDish extractFromResultSet(ResultSet rs) throws SQLException {
        OrderDish orderDish = new OrderDish();
        orderDish.setId(rs.getInt("id"));
        orderDish.setChecked(rs.getBoolean("checked"));
        orderDish.setPriceAll(rs.getBigDecimal("price_all"));
        orderDish.setToAdmin(rs.getBoolean("to_admin"));
        orderDish.setUser(userService.findById(rs.getInt("user_id")).get());

        return orderDish;
    }

    @Override
    public OrderDish makeUnique(Map<Integer, OrderDish> cache, OrderDish orderDish) {
        cache.putIfAbsent(orderDish.getId(), orderDish);

        return cache.get(orderDish.getId());
    }
}
