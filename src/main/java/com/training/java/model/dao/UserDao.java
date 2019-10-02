package com.training.java.model.dao;


import com.training.java.model.entity.UserAccount;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<UserAccount> {
    public Optional<UserAccount> getUserByLgnAndPswrd(String login, String password);

    public List<String> getRolesByLgn(String login);

    public void setRoles(UserAccount userAccount);

    public int getUserIdByLogin(String login);

    public void saveNewUser(UserAccount userAccount) throws SQLException;

    public Optional<UserAccount> findByUsername(String username);

    public int findUserId(String username);
}
