package com.training.java.controller.commands;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;

class CommandUtility {

    static boolean checkUserIsLogged(HttpServletRequest request, String userName) {
        Optional<HashSet<String>>  loggedUsers =Optional.ofNullable(
                (HashSet<String>) request.getSession().getServletContext()
                        .getAttribute("loggedUsers"));

        if (loggedUsers.isPresent() && userName != null
                && loggedUsers.get().stream().anyMatch(userName::equals)) {
            return true;
        }
        loggedUsers.get().add(userName);
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers.get());
        return false;
    }
}
