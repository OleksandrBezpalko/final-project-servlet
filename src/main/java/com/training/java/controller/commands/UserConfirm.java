package com.training.java.controller.commands;

import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.OrderDishService;
import com.training.java.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class  UserConfirm implements Command {
    private OrderDishService orderDishService;
    private UserService userService;

    public UserConfirm(OrderDishService orderDishService, UserService userService) {
        this.orderDishService = orderDishService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        UserAccount loginedUser = (UserAccount) request.getSession().getAttribute("loginedUser");
        String name = null;
        if (loginedUser != null) {
            name = loginedUser.getUsername();
        }
        request.setAttribute("ind", orderDishService.confirmToUser
                (userService.getUserIdByUsername(name)));
        return "/WEB-INF/views/PayOrder.jsp";
    }
}
