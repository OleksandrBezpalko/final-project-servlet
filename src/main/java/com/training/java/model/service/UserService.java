package com.training.java.model.service;


import com.training.java.controller.utils.BankTransactionException;
import com.training.java.model.dao.DaoFactory;
import com.training.java.model.dao.OrderDishDao;
import com.training.java.model.dao.UserDao;
import com.training.java.model.entity.UserAccount;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<UserAccount> findUser(String login, String password) {
        try (UserDao dao = daoFactory.createUserDao()) {
            Optional<UserAccount> user = dao.getUserByLgnAndPswrd(login, password);
            if (!user.isPresent()) return Optional.empty();
            List<String> roles = dao.getRolesByLgn(login);
            user.get().setRoles(roles);
            return user;
        }
    }

    public Optional<UserAccount> findByUsername(String login) {

        try (UserDao dao = daoFactory.createUserDao()) {

            Optional<UserAccount> user = dao.findByUsername(login);

            if (!user.isPresent()) return Optional.empty();
            List<String> roles = dao.getRolesByLgn(login);

            user.get().setRoles(roles);
            return user;
        }
    }

    public Optional<UserAccount> findById(int id) {

        try (UserDao dao = daoFactory.createUserDao()) {

            Optional<UserAccount> user = dao.findById(id);

            if (!user.isPresent()) return Optional.empty();
            List<String> roles = dao.getRolesByLgn(user.get().getUsername());
            user.get().setRoles(roles);
            return user;
        }
    }

    public void saveUser(UserAccount user) throws SQLException {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.saveNewUser(user);
        }
    }


    public List<UserAccount> getAllUsers() {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAll();
        }
    }

    public void update(UserAccount user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.update(user);
        }
    }

    public void updateRoles(UserAccount user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.setRoles(user);
        }
    }

    public int getUserIdByUsername(String username) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findUserId(username);
        }
    }

    public void payTheOrder(UserAccount userAccount, BigDecimal sum) throws BankTransactionException {
        Optional<UserAccount> restaurant;
        if (userAccount.getMoneyHave().compareTo(sum) < 0) {
            throw new BankTransactionException(
                    "The money in the account is not enough ("
                            + userAccount.getMoneyHave() + ")");
        }
        try (UserDao dao = daoFactory.createUserDao()) {
            restaurant = dao.findByUsername("restaurant");
        }
        if (restaurant.isPresent()) {
            try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
                dao.payTheOrder(userAccount, restaurant.get(), sum);
            }
        }
    }
}
