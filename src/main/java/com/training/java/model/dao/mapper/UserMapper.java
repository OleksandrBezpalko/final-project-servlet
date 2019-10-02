package com.training.java.model.dao.mapper;


import com.training.java.model.entity.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<UserAccount> {

    @Override
    public UserAccount extractFromResultSet(ResultSet rs) throws SQLException {
        UserAccount user = new UserAccount();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("username"));
        user.setLastName("last_name");
        user.setPassword(rs.getString("password"));
        user.setMoneyHave(rs.getBigDecimal("money_have"));

        return user;
    }

    @Override
    public UserAccount makeUnique(Map<Integer, UserAccount> cache,
                                  UserAccount user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
