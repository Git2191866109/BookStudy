package com.litian.javaweb;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: Customer.java
 * @time: 2020/6/22 10:21
 * @desc: |
 */

public class Customer {
    private String name;
    private String address;
    private String cardType;
    private String card;

    public Customer() {
    }

    public Customer(String name, String address, String cardType, String card) {
        this.name = name;
        this.address = address;
        this.cardType = cardType;
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
