/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import exceptions.InvalidAmountException;
import models.Account;
import models.Transaction;
import util.FileLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class TransactionDAO {

    public boolean deposit(int accountId, double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Tutar sıfırdan büyük olmalıdır.");
        }

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountById(accountId);

        if (account == null) {
            return false;
        }

        double newBalance = account.getBalance() + amount;

        String updateSql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        String insertSql = "INSERT INTO transactions(account_id, transaction_type, amount, description) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setDouble(1, newBalance);
            updatePs.setInt(2, accountId);
            updatePs.executeUpdate();

            PreparedStatement insertPs = con.prepareStatement(insertSql);
            insertPs.setInt(1, accountId);
            insertPs.setString(2, "Deposit");
            insertPs.setDouble(3, amount);
            insertPs.setString(4, "Money deposited to account");
            insertPs.executeUpdate();

            FileLogger.writeLog("Deposit: " + amount + " to account ID: " + accountId);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(int accountId, double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Tutar sıfırdan büyük olmalıdır.");
        }

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountById(accountId);

        if (account == null) {
            return false;
        }

        if (account.getBalance() < amount) {
            throw new InvalidAmountException("Yetersiz bakiye.");
        }

        double newBalance = account.getBalance() - amount;

        String updateSql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        String insertSql = "INSERT INTO transactions(account_id, transaction_type, amount, description) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setDouble(1, newBalance);
            updatePs.setInt(2, accountId);
            updatePs.executeUpdate();

            PreparedStatement insertPs = con.prepareStatement(insertSql);
            insertPs.setInt(1, accountId);
            insertPs.setString(2, "Withdraw");
            insertPs.setDouble(3, amount);
            insertPs.setString(4, "Money withdrawn from account");
            insertPs.executeUpdate();

            FileLogger.writeLog("Withdraw: " + amount + " from account ID: " + accountId);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Tutar sıfırdan büyük olmalıdır.");
        }

        AccountDAO accountDAO = new AccountDAO();

        Account fromAccount = accountDAO.getAccountById(fromAccountId);
        Account toAccount = accountDAO.getAccountById(toAccountId);

        if (fromAccount == null || toAccount == null) {
            return false;
        }

        if (fromAccount.getBalance() < amount) {
            throw new InvalidAmountException("Yetersiz bakiye.");
        }

        try {
            boolean withdrawResult = withdraw(fromAccountId, amount);
            boolean depositResult = deposit(toAccountId, amount);

            if (withdrawResult && depositResult) {
                FileLogger.writeLog("Transfer: " + amount + " from account ID: "
                        + fromAccountId + " to account ID: " + toAccountId);
                return true;
            }

        } catch (InvalidAmountException e) {
            throw e;
        }

        return false;
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getString("description")
                );

                transactions.add(transaction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public double getTotalDeposits() {
        String sql = "SELECT SUM(amount) AS total FROM transactions WHERE transaction_type = 'Deposit'";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getTotalWithdraws() {
        String sql = "SELECT SUM(amount) AS total FROM transactions WHERE transaction_type = 'Withdraw'";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTransactionCount() {
        String sql = "SELECT COUNT(*) AS count FROM transactions";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<Transaction> getTransactionsByUserId(int userId) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT t.* FROM transactions t "
                + "JOIN accounts a ON t.account_id = a.account_id "
                + "WHERE a.user_id = ? "
                + "ORDER BY t.transaction_date DESC";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getString("description")
                );

                transactions.add(transaction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public boolean transferByUserId(int senderUserId, int receiverUserId, double amount)
            throws InvalidAmountException {

        AccountDAO accountDAO = new AccountDAO();

        Account senderAccount = accountDAO.getFirstAccountByUserId(senderUserId);
        Account receiverAccount = accountDAO.getFirstAccountByUserId(receiverUserId);

        if (senderAccount == null || receiverAccount == null) {
            return false;
        }

        return transfer(
                senderAccount.getAccountId(),
                receiverAccount.getAccountId(),
                amount
        );
    }
}
