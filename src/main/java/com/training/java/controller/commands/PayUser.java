package com.training.java.controller.commands;

import com.training.java.controller.utils.BankTransactionException;
import com.training.java.model.entity.Dish;
import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.DishService;
import com.training.java.model.service.OrderDishService;
import com.training.java.model.service.ProductService;
import com.training.java.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PayUser implements Command {
    private ProductService productService;
    private UserService userService;
    private OrderDishService orderDishService;

    public PayUser(ProductService productService, UserService userService, OrderDishService orderDishService) {
        this.productService = productService;
        this.userService = userService;
        this.orderDishService = orderDishService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int ind = Integer.parseInt(request.getParameter("ind"));
        BigDecimal price = BigDecimal.valueOf(Double.valueOf(request.getParameter("price")));

        List<String> products = productService.getAllProductsFromOrder(ind);

        Map<String, Integer> enoughProducts = new CheckOrderUser(new ProductService()).prodNeeded(products);
        List<String> best = enoughProducts.keySet().stream().filter(s -> enoughProducts.get(s)
                .equals(enoughProducts.keySet().stream()
                        .map(enoughProducts::get).max(Integer::compareTo).get())).collect(Collectors.toList());

        UserAccount loginedUser = (UserAccount) request.getSession().getAttribute("loginedUser");
        String name = null;
        if (loginedUser != null) {
            name = loginedUser.getUserName();
        }

        Optional<UserAccount> user = userService.findByUsername(name);
        user.get().setProdLikeNow(productService.getByProductName(best.get(0)).get());
        userService.update(user.get());


        if (products.stream().distinct().map(productService::getByProductName)
                .anyMatch(s -> s.get().getAmountHave() - enoughProducts.get(s.get().getProduct()) < 0)) {

            request.setAttribute("index", ind);
            CheckOrderUser checkOrderUser =
                    new CheckOrderUser(new DishService(),new ProductService(),new OrderDishService());
            Map<Dish, Long> orderClient = checkOrderUser.orderClient(request, ind);
            request.setAttribute("notEnought", "nEnPR");
            request.setAttribute("map", orderClient);
            request.setAttribute("price", orderDishService.getByOrderId(ind).get().getPriceAll());
            return "/WEB-INF/views/checkPageUser.jsp";

        }
        try {
            userService.payTheOrder(user.get(), price);
        } catch (BankTransactionException e) {
            request.setAttribute("moneyBalance", userService.findByUsername(name)
                    .get().getMoneyHave());
            request.setAttribute("notEnoughtMoney", "You don't have enough money, please add balance before");
            return "/WEB-INF/views/addBalance.jsp";
        }
        productService.getAllProductsFromOrder(ind).stream().map(productService::getByProductName)
                .forEach(s -> {
                    s.get().setAmountHave(s.get().getAmountHave() - 1);
                    productService.update(s.get());
                });

        orderDishService.payed(ind);
        return "redirect:/menu";
    }
}
