/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class AccountDAO {

    public boolean addAccount(Account account) {
        String sql = "INSERT INTO accounts(user_id, account_no, account_type, balance, currency, is_active) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getAccountNo());
            ps.setString(3, account.getAccountType());
            ps.setDouble(4, account.getBalance());
            ps.setString(5, account.getCurrency());
            ps.setBoolean(6, account.isActive());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM accounts";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_no"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        rs.getBoolean("is_active")
                );

                accounts.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public boolean updateAccount(Account account) {
        String sql = "UPDATE accounts SET account_type = ?, balance = ?, currency = ?, is_active = ? WHERE account_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, account.getAccountType());
            ps.setDouble(2, account.getBalance());
            ps.setString(3, account.getCurrency());
            ps.setBoolean(4, account.isActive());
            ps.setInt(5, account.getAccountId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int accountId) {

        String deleteTransactions = "DELETE FROM transactions WHERE account_id = ?";
        String deleteAccount = "DELETE FROM accounts WHERE account_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps1 = con.prepareStatement(deleteTransactions);
            ps1.setInt(1, accountId);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(deleteAccount);
            ps2.setInt(1, accountId);

            int result = ps2.executeUpdate();

            System.out.println("Deleted account id: " + accountId);
            System.out.println("Delete result: " + result);

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_no"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        rs.getBoolean("is_active")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Account> getAccountsByUserId(int userId) {
        ArrayList<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_no"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        rs.getBoolean("is_active")
                );

                accounts.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public Account getAccountByAccountNo(String accountNo) {
        String sql = "SELECT * FROM accounts WHERE account_no = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_no"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        rs.getBoolean("is_active")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getTotalBalanceByUserId(int userId) {
        String sql = "SELECT SUM(balance) AS total FROM accounts WHERE user_id = ?";

        try {
            java.sql.Connection con = database.DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Account getFirstAccountByUserId(int userId) {

        String sql = "SELECT * FROM accounts WHERE user_id = ? LIMIT 1";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_no"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        rs.getBoolean("is_active")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
