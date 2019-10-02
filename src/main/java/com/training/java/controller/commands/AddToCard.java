package com.training.java.controller.commands;

import com.training.java.model.entity.Dish;
import com.training.java.model.entity.OrderDish;
import com.training.java.model.service.DishService;
import com.training.java.model.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddToCard implements Command {
    private UserService userService;
    private DishService dishService;

    public AddToCard(UserService userService, DishService dishService) {
        this.userService = userService;
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int dishId = Integer.parseInt(request.getParameter("dish"));
        HttpSession session = request.getSession();
        Optional<OrderDish>  orderDish = Optional.ofNullable((OrderDish) session.getAttribute("orderDish"));

        ServletContext context = request.getServletContext();
        if (!orderDish.isPresent()) {
            orderDish = Optional.of(new OrderDish());
            orderDish.get().setDishes(new CopyOnWriteArrayList<>());
            orderDish.get().setPayed(false);
            orderDish.get().setChecked(false);
            orderDish.get().setToAdmin(false);
            String name = (String) context.getAttribute("userName");
            orderDish.get().setUser(userService.findByUsername(name).get());

        }
        orderDish.get().addDish(dishService.findByDishId(dishId));
        orderDish.get().setPriceAll(BigDecimal.valueOf(orderDish.get().getDishes().stream()
                .map(Dish::getPrice).mapToInt(BigDecimal::intValue).sum()));
        session.setAttribute("orderDish", orderDish.get());
        return "redirect:/menu";
    }
}
