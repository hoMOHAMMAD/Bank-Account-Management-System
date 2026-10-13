/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author moham
 */
public class Account {

    private int accountId;
    private int userId;
    private String accountNo;
    private String accountType;
    private double balance;
    private String currency;
    private boolean active;

    public Account() {
    }

    public Account(int accountId, int userId, String accountNo,
            String accountType, double balance,
            String currency, boolean active) {

        this.accountId = accountId;
        this.userId = userId;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
        this.active = active;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isActive() {
        return active;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
