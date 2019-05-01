package com.bank.account.model;

import java.math.BigDecimal;

public class Account {

    public Account() {
    }

    public Account(long id, String username, String name, BigDecimal balance, String currency) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public Account(String username, String name, BigDecimal balance, String currency) {
        this.username = username;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    private long id;
    private String username;
    private String name;
    private BigDecimal balance;
    private String currency;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
