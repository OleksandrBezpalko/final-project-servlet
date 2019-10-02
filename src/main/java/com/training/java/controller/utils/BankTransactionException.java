package com.training.java.controller.utils;

public class BankTransactionException extends Exception {

    private static final long serialVersionUID = -1111L;

    public BankTransactionException(String message) {
        super(message);
    }
}