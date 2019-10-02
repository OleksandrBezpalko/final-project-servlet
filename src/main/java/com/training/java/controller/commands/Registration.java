package com.training.java.controller.commands;

import com.training.java.config.SecurityConfig;
import com.training.java.controller.utils.Password;
import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Registration implements Command {
    private UserService userService;

    public Registration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String lastname = request.getParameter("lastname");

        if (userName == null || password == null||lastname==null) {

            return "/WEB-INF/views/register.jsp";
        }

        UserAccount newUser = new UserAccount();
        newUser.setUserName(userName);
        newUser.setLastName(lastname);
        try {
            newUser.setPassword(Password.getSaltedHash(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> roles = new ArrayList<>();
        roles.add(SecurityConfig.ROLE_USER);
        newUser.setRoles(roles);
        try {
            userService.saveUser(newUser);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "existEx");
            return "/WEB-INF/views/register.jsp";
        }

        return "redirect:/login";
    }
}
