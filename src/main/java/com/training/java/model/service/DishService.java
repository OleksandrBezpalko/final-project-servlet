package com.training.java.model.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.training.java.model.dao.DaoFactory;
import com.training.java.model.dao.DishDao;
import com.training.java.model.entity.Dish;

public class DishService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Dish> findAll() {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findAll();
        }

    }

    public Dish findByDishId(int id) {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findByDishId(id);
        }

    }

    public void saveDish(Dish dish) throws SQLIntegrityConstraintViolationException {
        try (DishDao dao = daoFactory.createDishDao()) {
            dao.saveDish(dish);
        }

    }

    public void deleteByID(int id) {
        try (DishDao dao = daoFactory.createDishDao()) {
            dao.deleteById(id);
        }
    }

    public List<Dish> findByOrderID(int id) {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findByOrder(id);
        }
    }

    public List<Dish> findByOrderIDToUSer(int id) {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findByOrderToUser(id);
        }


    }

    public long findCount() {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findCount();
        }
    }

    public List<Dish> findAll(Integer page, Integer size) {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findAll(page, size);
        }
    }
}
