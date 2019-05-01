package com.bank.account.model;

import java.math.BigDecimal;

public class Transfer {

    private long to;
    private long from;
    private BigDecimal amount;
    private String currency;

    public Transfer() {
    }

    public Transfer(long to, long from, BigDecimal amount, String currency) {
        this.to = to;
        this.from = from;
        this.amount = amount;
        this.currency = currency;
    }

    public long getTo() {
        return to;
    }

    public long getFrom() {
        return from;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
