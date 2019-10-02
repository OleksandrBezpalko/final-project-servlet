package com.training.java.controller.commands;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.training.java.model.entity.Dish;
import com.training.java.model.entity.OrderDish;
import com.training.java.model.entity.Product;
import com.training.java.model.service.DishService;
import com.training.java.model.service.ProductService;


public class OrderController implements Command {
    private DishService dishService;
    private ProductService productService;

    public OrderController(DishService dishService, ProductService productService) {
        this.dishService = dishService;
        this.productService = productService;
    }

    public OrderController() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<OrderDish> orderDish = Optional.ofNullable((OrderDish)
                request.getSession().getAttribute("orderDish"));

        if (!orderDish.isPresent()||orderDish.get().getPriceAll().compareTo(BigDecimal.ZERO)==0) {

            request.setAttribute("message", "Nothing is ordered");
            request.setAttribute("title", "Menu of restaurent");
            request.setAttribute("dishes", dishService.findAll());
            return "/WEB-INF/views/menuPage.jsp";
        }

        Map<Dish, Long> orderClient = new TreeMap<>();
        orderDish.get().getDishes()
                .stream().distinct().forEach(s -> orderClient.put(s, orderDish.get().getDishes().stream()
                .filter(x -> x.equals(s)).count()));

        request.setAttribute("mapD", orderClient);
        request.setAttribute("amount", orderDish.get().getPriceAll());

        Map<Product, Integer> enoughProducts = enoughProducts(orderDish.get());

        if (enoughProducts.keySet().stream()
                .anyMatch(s -> (productService.getAmountHave(s) - enoughProducts.get(s)) < 0)) {
            request.setAttribute("notEnought", "We dont have enought products");
        }
        return "/WEB-INF/views/order.jsp";
    }

    public Map<Product, Integer> enoughProducts(OrderDish orderDish) {
        List<Product> products = new ArrayList<>();
        orderDish.getDishes().forEach(s -> s.getProductsForDish()
                .stream().filter(Objects::nonNull).forEach(products::add));

        Map<Product, Integer> enoughProducts = new HashMap<>();
        products
                .stream()
                .map(s -> enoughProducts.containsKey(s) ?
                        enoughProducts.put(s, enoughProducts.get(s) + 1)
                        : enoughProducts.put(s, 1)).collect(Collectors.toList());
        return enoughProducts;
    }
}
