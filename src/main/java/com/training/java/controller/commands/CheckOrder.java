package com.training.java.controller.commands;

import com.training.java.model.entity.Dish;
import com.training.java.model.service.DishService;
import com.training.java.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class CheckOrder implements Command {
    private DishService dishService;
    private ProductService productService;

    public CheckOrder(DishService dishService, ProductService productService) {
        this.dishService = dishService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int ind = Integer.parseInt(request.getParameter("ind"));

        request.setAttribute("index", ind);

        Map<Dish, Long> orderClient = new ConcurrentSkipListMap<>();
        dishService.findByOrderID(ind)
                .stream().distinct().forEach(s -> orderClient.put(s, dishService
                .findByOrderID(ind).stream()
                .filter(x -> x.equals(s)).count()));
        request.setAttribute("map", orderClient);
        Map<String, Long> neededProducts = new ConcurrentSkipListMap<>();
        productService.getAllProductsFromOrder(ind).stream().map(s -> neededProducts.containsKey(s) ?
                neededProducts.put(s, neededProducts.get(s) + 1L)
                : neededProducts.put(s, 1L)).collect(Collectors.toList());
        request.setAttribute("map2", neededProducts);
        return "/WEB-INF/views/checkPage.jsp";
    }
}
