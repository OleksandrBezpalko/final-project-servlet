package com.training.java.model.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.logging.FileHandler;

import com.training.java.model.dao.DishDao;
import com.training.java.model.dao.mapper.DishMapper;
import com.training.java.model.dao.mapper.ProductMapper;
import com.training.java.model.entity.Dish;
import com.training.java.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCDishDao implements DishDao {
    static final Logger LOGGER = LogManager.getLogger(JDBCDishDao.class);
    private Connection connection;
    private ResourceBundle mybundle;
    private FileHandler fh;

    public JDBCDishDao(Connection connection) {
        this.connection = connection;
        mybundle = ResourceBundle.getBundle("sqlQuery/dishDao");
    }



    @Override
    public long findCount() {
        long count = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(mybundle.getString("findCount"));) {

            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                    count = resultSet.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }



    @Override
    public List<Dish> findAll(Integer page, Integer size) {
        Map<Integer, Dish> dishes = new HashMap<>();
        Map<Integer, Product> products = new HashMap<>();
        try (PreparedStatement st = connection.prepareStatement(mybundle.getString("findAllPaginated"))) {
            st.setInt(1, (page - 1) * size);
            st.setInt(2, size);
            ResultSet rs = st.executeQuery();
            ProductMapper productMapper = new ProductMapper();
            DishMapper dishMapper = new DishMapper();

            while (rs.next()) {
                Dish dish = dishMapper
                        .extractFromResultSet(rs);
                Product product = productMapper
                        .extractFromResultSet(rs);
                dish = dishMapper
                        .makeUnique(dishes, dish);
                product = productMapper
                        .makeUnique(products, product);
                if (dish.getProductsForDish() == null) {
                    dish.setProductsForDish(new ArrayList<>());
                }
                dish.getProductsForDish().add(product);
            }
            return new ArrayList<>(dishes.values());
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);

        }
    }



    @Override
    public void create(Dish entity) {

    }

    @Override
    public Optional<Dish> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Dish> findAll() {
        Map<Integer, Dish> dishes = new HashMap<>();
        Map<Integer, Product> products = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("findAll"));
            ProductMapper productMapper = new ProductMapper();
            DishMapper dishMapper = new DishMapper();

            while (rs.next()) {
                Dish dish = dishMapper
                        .extractFromResultSet(rs);
                Product product = productMapper
                        .extractFromResultSet(rs);
                dish = dishMapper
                        .makeUnique(dishes, dish);
                product = productMapper
                        .makeUnique(products, product);
                if (dish.getProductsForDish() == null) {
                    dish.setProductsForDish(new ArrayList<>());
                }
                dish.getProductsForDish().add(product);
            }
            return new ArrayList<>(dishes.values());
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @Override
    public void update(Dish entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDish(Dish dish) throws SQLIntegrityConstraintViolationException {
        int maxId = -1;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(mybundle.getString("saveDish1"));
            while (rs.next()) {
                maxId = rs.getInt("max(id)");
            }

            dish.setId(++maxId);

        } catch (SQLException e) {
            maxId = 1;
            dish.setId(maxId);

        }

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("insertDish"))) {
            ps.setInt(1, dish.getId());
            ps.setString(2, dish.getFileName());
            ps.setString(3, dish.getName());
            ps.setString(4, dish.getNameUkr());
            ps.setBigDecimal(5, dish.getPrice());

            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e){
            throw e;
        }
        catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);
        }
        for (Product product : dish.getProductsForDish()) {

            try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("insertDp"))) {
                ps.setInt(1, maxId);
                ps.setInt(2, product.getId());
                ps.execute();

            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                throw new RuntimeException(e);

            }
        }

    }

    @Override
    public Dish findByDishId(int id) {

        Map<Integer, Product> products = new HashMap<>();
        Map<Integer, Dish> dishes = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("findByDishId"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ProductMapper productMapper = new ProductMapper();
            DishMapper dishMapper = new DishMapper();
            while (rs.next()) {
                Dish dish1 = dishMapper
                        .extractFromResultSet(rs);
                id = dish1.getId();
                Product product = productMapper
                        .extractFromResultSet(rs);
                dish1 = dishMapper
                        .makeUnique(dishes, dish1);
                product = productMapper
                        .makeUnique(products, product);
                if (dish1.getProductsForDish() == null) {
                    dish1.setProductsForDish(new ArrayList<>());
                }
                dish1.getProductsForDish().add(product);
            }
            return dishes.get(id);
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> findByOrder(int id) {
        ArrayList<Dish> dishes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("findByOrder"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ProductMapper productMapper = new ProductMapper();
            DishMapper dishMapper = new DishMapper();

            while (rs.next()) {
                Dish dish = dishMapper
                        .extractFromResultSet(rs);
                dishes.add(dish);
            }
            return dishes;
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("deleteById"))) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);

        }

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("deleteById2"))) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<Dish> findByOrderToUser(int id){
        Map<Integer, Dish> dishes = new HashMap<>();
        Map<Integer, Product> products = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(mybundle.getString("findByOrderToUser"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ProductMapper productMapper = new ProductMapper();
            DishMapper dishMapper = new DishMapper();

            while (rs.next()) {
                Dish dish = dishMapper
                        .extractFromResultSet(rs);
                Product product = productMapper
                        .extractFromResultSet(rs);
                dish = dishMapper
                        .makeUnique(dishes, dish);
                product = productMapper
                        .makeUnique(products, product);
                if (dish.getProductsForDish() == null) {
                    dish.setProductsForDish(new ArrayList<>());
                }
                dish.getProductsForDish().add(product);
            }
            return new ArrayList<>(dishes.values());
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
