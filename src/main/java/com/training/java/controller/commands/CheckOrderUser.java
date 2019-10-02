package com.training.java.controller.commands;

import com.training.java.model.entity.Dish;
import com.training.java.model.service.DishService;
import com.training.java.model.service.OrderDishService;
import com.training.java.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class CheckOrderUser implements Command {
    private DishService dishService;
    private ProductService productService;
    private OrderDishService orderDishService;

    public CheckOrderUser(DishService dishService, ProductService productService, OrderDishService orderDishService) {
        this.dishService = dishService;
        this.productService = productService;
        this.orderDishService = orderDishService;
    }

    public CheckOrderUser(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int ind = Integer.parseInt(request.getParameter("ind"));
        request.setAttribute("index", ind);
        Map<Dish, Long> orderClient = orderClient(request, ind);

        List<String> products = productService.getAllProductsFromOrder(ind);

        Map<String, Integer> enoughtProducts = prodNeeded(products);
        if (products.stream().distinct().map(productService::getByProductName)
                .anyMatch(s -> s.get().getAmountHave() - enoughtProducts.get(s.get().getProduct()) < 0)) {
            request.setAttribute("notEnought", "prodLess");
        }
        request.setAttribute("map", orderClient);
        request.setAttribute("price", orderDishService.getByOrderId(ind).get().getPriceAll());
        return "/WEB-INF/views/checkPageUser.jsp";
    }

    public Map<Dish, Long> orderClient(HttpServletRequest request, int ind) {
        Map<Dish, Long> orderClient = new ConcurrentSkipListMap<>();
        dishService.findByOrderID(ind)
                .stream().distinct().forEach(s -> orderClient.put(s, dishService
                .findByOrderID(ind).stream()
                .filter(x -> x.equals(s)).count()));
        return orderClient;
    }

    public Map<String, Integer> prodNeeded(List<String> products) {
        Map<String, Integer> enoughtProducts = new HashMap<>();

        products.stream()
                .map(productService::getByProductName)
                .map(s -> enoughtProducts.containsKey(s.get().getProduct()) ?
                        enoughtProducts.put(s.get().getProduct(), enoughtProducts.get(s.get().getProduct()) + 1)
                        : enoughtProducts.put(s.get().getProduct(), 1)).collect(Collectors.toList());
        return enoughtProducts;
    }
}
