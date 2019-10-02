package com.training.java.config;


import java.util.*;

public class SecurityConfig {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

    static {
        init();
    }

    private static void init() {

        List<String> urlPatterns1 = new ArrayList<String>();
        urlPatterns1.add("/userInfo");
        urlPatterns1.add("/employeeTask");
        urlPatterns1.add("/add");
        urlPatterns1.add("/addToCard");
        urlPatterns1.add("/order");
        urlPatterns1.add("/removeD");
        urlPatterns1.add("/addedOrder");
        urlPatterns1.add("/addMoney");
        urlPatterns1.add("/addBalance");
        urlPatterns1.add("/user_confirm");
        urlPatterns1.add("/checkOrderUser");
        urlPatterns1.add("/checkOrderUser_confirm");

        mapConfig.put(ROLE_USER, urlPatterns1);

        List<String> urlPatterns2 = new ArrayList<String>();
        urlPatterns2.add("/adminOrder");
        urlPatterns2.add("/replenish_stock_of_products");
        urlPatterns2.add("/adminPage");
        urlPatterns2.add("/checkOrder");
        urlPatterns2.add("/checkOrder_confirm");
        urlPatterns2.add("/user");

        mapConfig.put(ROLE_ADMIN, urlPatterns2);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
}