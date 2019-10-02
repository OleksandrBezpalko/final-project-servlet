package com.training.java.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.training.java.controller.commands.*;
import com.training.java.model.service.DishService;
import com.training.java.model.service.OrderDishService;
import com.training.java.model.service.ProductService;
import com.training.java.model.service.UserService;

public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static final Logger LOGGER = LogManager.getLogger(Servlet.class);

    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        commands.put("remove", new RemoveDishAdmin(new DishService()));
        commands.put("logout",
                new LogOut());
        commands.put("login",
                new LoginCommand(new UserService()));
        commands.put("registrate",
                new Registration(new UserService()));
        commands.put("menu",
                new Menu(new DishService(), new UserService()));
        commands.put("add",
                new AddDish(new ProductService(), new DishService()));
        commands.put("addToCard",
                new AddToCard(new UserService(), new DishService()));
        commands.put("order",
                new OrderController(new DishService(), new ProductService()));
        commands.put("removeD",
                new RemoveDish());
        commands.put("addedOrder",
                new ConfirmOrder(new OrderDishService(),new ProductService()));
        commands.put("adminOrder",
                new AdminOrder(new ProductService(), new OrderDishService()));
        commands.put("replenish_stock_of_products",
                new Replenish(new ProductService()));
        commands.put("checkOrder",
                new CheckOrder(new DishService(), new ProductService()));
        commands.put("checkOrder_confirm",
                new Confirm(new OrderDishService()));
        commands.put("addMoney",
                new AddMoney());
        commands.put("addBalance",
                new AddBalance(new UserService()));
        commands.put("user_confirm",
                new UserConfirm(new OrderDishService(), new UserService()));
        commands.put("checkOrderUser",
                new CheckOrderUser(new DishService()
                        , new ProductService(), new OrderDishService()));
        commands.put("checkOrderUser_confirm",
                new PayUser(new ProductService()
                        , new UserService(), new OrderDishService()));
        commands.put("user",
                new Users(new UserService()));

    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final HttpSession session = request.getSession();
        String path = request.getRequestURI();
        path = path.replaceAll(".*/", "");
        Command command = commands.getOrDefault(path,
                new Menu(new DishService(), new UserService()));
        String page = command.execute(request);

        if (page.startsWith("redirect")) {
            response.sendRedirect(page.substring(9));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }

    }
}
