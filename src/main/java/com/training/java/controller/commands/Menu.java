package com.training.java.controller.commands;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.DishService;
import com.training.java.model.service.UserService;

public class Menu implements Command {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    private DishService dishService;
    private UserService userService;

    public Menu(DishService dishService, UserService userService) {
        this.dishService = dishService;
        this.userService = userService;
    }

    public Menu(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        loginedUser(request);

        Integer page = null;
        Integer size = null;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page = DEFAULT_PAGE;
        }

        try {
            size = Integer.parseInt(request.getParameter("size"));
        } catch (NumberFormatException e) {
            size = DEFAULT_SIZE;
        }

        long elementsCount = dishService.findCount();
        request.setAttribute("elements", dishService.findAll(page, size));
        request.setAttribute("elementsCount", elementsCount);
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        request.setAttribute("pagesCount", (int) Math.ceil(elementsCount * 1.0 / size));

        return "/WEB-INF/views/menuPage.jsp";
    }

    public void loginedUser(HttpServletRequest request) {
        Optional<UserAccount>  loginedUser =Optional.ofNullable
                ((UserAccount) request.getSession().getAttribute("loginedUser"));
        Optional<String> name= Optional.empty();
        if (loginedUser.isPresent()) {
            name =Optional.of(loginedUser.get().getUserName());

            if (userService.findByUsername(name.get()).isPresent()) {
                request.setAttribute("moneyBalance", userService.findByUsername(name.get())
                        .get().getMoneyHave());
            }

        }

    }

}
