package com.training.java.model.dao.impl;

import com.training.java.model.dao.mapper.UserMapper;
import com.training.java.model.dao.UserDao;
import com.training.java.model.entity.UserAccount;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private ResourceBundle mybundle;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
        mybundle = ResourceBundle.getBundle("sqlQuery/userAcc");

    }

    public int getUserIdByLogin(String login) {
        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("getUserIdByLogin"))) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            return rs.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveNewUser(UserAccount userAccount) throws SQLException {
        int maxId = -1;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("maxId"));
            while (rs.next()) {
                maxId = rs.getInt("max(id)");
            }
            userAccount.setId(maxId + 1);
        } catch (SQLException e) {
            maxId = 1;
            userAccount.setId(maxId);
        }

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("insertUser"))) {
            ps.setInt(1, userAccount.getId());
            ps.setString(2, userAccount.getLastName());
            ps.setBigDecimal(3, userAccount.getMoneyHave());
            ps.setString(4, userAccount.getPassword());
            ps.setString(5, userAccount.getUserName());
            ps.setNull(6, java.sql.Types.INTEGER);
            ps.execute();
        } catch (SQLException e) {
            throw e;
        }

        int id = 0;
        id = getUserIdByLogin(userAccount.getUserName());

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("insertURole"))) {
            for (String role : userAccount.getRoles()) {
                ps.setInt(1, id);
                ps.setString(2, role);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Optional<UserAccount> findByUsername(String login) {
        Optional<UserAccount> user;
        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("findByUsername"))) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                user = Optional.of(userMapper
                        .extractFromResultSet(rs));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int findUserId(String username) {
        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("findUserId"))) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return 0;
    }

    ;

    @Override
    public Optional<UserAccount> getUserByLgnAndPswrd(String login, String password) {
        Optional<UserAccount> user;
        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("getUserByLgnAndPswrd"))) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            UserMapper userMapper = new UserMapper();
            while (rs.next()) {
                user = Optional.of(userMapper
                        .extractFromResultSet(rs));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserAccount> findById(int id) {
        Optional<UserAccount> user;
        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("getUserById"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            UserMapper userMapper = new UserMapper();
            while (rs.next()) {
                user = Optional.of(userMapper
                        .extractFromResultSet(rs));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<String> getRolesByLgn(String login) {
        List<String> roles = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("getRolesByLgn"))) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("roles"));
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setRoles(UserAccount userAccount) {

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("deleteRoles"))) {
            ps.setInt(1, userAccount.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(String role :userAccount.getRoles()){
            try (PreparedStatement ps = connection.prepareCall(mybundle.getString("updateRoles"))) {
                ps.setInt(1,userAccount.getId());
                ps.setString(2,role);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }

    @Override
    public void create(UserAccount entity) {
    }


    @Override
    public List<UserAccount> findAll() {

        Map<Integer, UserAccount> dishes = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("findAll"));
            UserMapper userMapper = new UserMapper();


            while (rs.next()) {
                UserAccount userAccount = userMapper
                        .extractFromResultSet(rs);
                userAccount = userMapper
                        .makeUnique(dishes, userAccount);
                String role = rs.getString("roles");
                if (userAccount.getRoles() == null) {
                    userAccount.setRoles(new ArrayList<>());
                }
                userAccount.getRoles().add(role);
            }
            return new ArrayList<>(dishes.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    @Override
    public void update(UserAccount entity) {
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("update"))) {
            ps.setBigDecimal(1, entity.getMoneyHave());
            if (entity.getProdLikeNow() != null) {
                ps.setInt(2, entity.getProdLikeNow().getId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setInt(3, entity.getId());
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
