package com.training.java.model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.training.java.model.entity.Dish;

public interface DishDao extends GenericDao<Dish> {
    public void saveDish(Dish dish) throws SQLIntegrityConstraintViolationException;

    public Dish findByDishId(int id);

    public  List<Dish> findByOrder(int id);

    public void deleteById(int id);

    public List<Dish> findByOrderToUser(int id);

    public long findCount();

    public List<Dish> findAll(Integer page, Integer size);
}
