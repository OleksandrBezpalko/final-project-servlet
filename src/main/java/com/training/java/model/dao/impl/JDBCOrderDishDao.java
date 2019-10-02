package com.training.java.model.dao.impl;

import com.training.java.model.dao.OrderDishDao;
import com.training.java.model.dao.mapper.OrderMapper;
import com.training.java.model.entity.Dish;
import com.training.java.model.entity.OrderDish;
import com.training.java.model.entity.UserAccount;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class JDBCOrderDishDao implements OrderDishDao {

    private Connection connection;
    private ResourceBundle mybundle;

    public JDBCOrderDishDao(Connection connection) {
        this.connection = connection;
        mybundle = ResourceBundle.getBundle("sqlQuery/orderDao");
    }

    @Override
    public void create(OrderDish entity) {

    }

    @Override
    public Optional<OrderDish> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<OrderDish> findAll() {
        return null;
    }

    @Override
    public void update(OrderDish entity) {

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

    @Override
    public void save(OrderDish orderDish) {
        int maxId = -1;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("maxID"));
            while (rs.next()) {
                maxId = rs.getInt("max(id)");
            }

            orderDish.setId(++maxId);
        } catch (SQLException e) {
            maxId = 1;
            orderDish.setId(maxId);
        }

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("insertOrder"))) {
            ps.setInt(1, orderDish.getId());
            ps.setBoolean(2, orderDish.isChecked());
            ps.setBoolean(3, orderDish.isPayed());
            ps.setBigDecimal(4, orderDish.getPriceAll());
            ps.setBoolean(5, orderDish.isToAdmin());
            ps.setInt(6, orderDish.getUser().getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Dish dish : orderDish.getDishes()) {

            try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("inserOD"))) {

                ps.setInt(1, maxId);
                ps.setInt(2, dish.getId());
                ps.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }

    }

    @Override
    public List<Integer> findOrderUncheckedIndex() {
        List<Integer> orders = new CopyOnWriteArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("findOrderUncheckedIndex"));

            while (rs.next()) {
                orders.add(rs.getInt("id"));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setChecked(int ind) {
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("setChecked"))) {
            ps.setInt(1, ind);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<Integer> findUnConfirmed(int id) {
        List<Integer> ordersUn = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("findUnConfirmed"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ordersUn.add(rs.getInt("id"));
            }
            return ordersUn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<OrderDish> getByOrderID(int id){
        Optional<OrderDish> orderDish;
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("getByOrderID"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            OrderMapper orderMapper = new OrderMapper();

            while (rs.next()) {
                orderDish = Optional.of(orderMapper
                        .extractFromResultSet(rs));
                return orderDish;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void setPayed(int id) {
        try (PreparedStatement ps = connection.prepareCall(mybundle.getString("setPayed"))) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void payTheOrder(UserAccount user, UserAccount restaurant, BigDecimal sum) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareCall(mybundle.getString("payTheOrder"));
            ps.setBigDecimal(1, user.getMoneyHave().add(sum.multiply(BigDecimal.valueOf(-1))));
            ps.setString(2, user.getUserName());
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareCall(mybundle.getString("payTheOrder"));
            ps2.setBigDecimal(1, restaurant.getMoneyHave().add(sum));
            ps2.setString(2, restaurant.getUserName());
            ps2.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
