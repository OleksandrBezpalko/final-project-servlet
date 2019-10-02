package com.training.java.controller.listeners;


import com.training.java.model.entity.UserAccount;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent.getSession().getServletContext()
                .getAttribute("loggedUsers");

        UserAccount loginedUser = (UserAccount) httpSessionEvent.getSession().getAttribute("loginedUser");
        if(loginedUser==null){
            return;
        }
        String name= loginedUser.getUserName();
        loggedUsers.remove(name);
        httpSessionEvent.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }
}