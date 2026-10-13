/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dao.AccountDAO;
import dao.TransactionDAO;
import models.Account;
import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class ReportHelper {

    public static String generateSystemReport() {

        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        ArrayList<Account> accounts = accountDAO.getAllAccounts();

        StringBuilder report = new StringBuilder();

        report.append("BANK SYSTEM REPORT\n");
        report.append("============================\n\n");

        report.append("Total Accounts: ").append(accounts.size()).append("\n");
        report.append("Total Transactions: ").append(transactionDAO.getTransactionCount()).append("\n");
        report.append("Total Deposits: ").append(transactionDAO.getTotalDeposits()).append("\n");
        report.append("Total Withdraws: ").append(transactionDAO.getTotalWithdraws()).append("\n\n");

        report.append("ACCOUNTS LIST\n");
        report.append("----------------------------\n");

        for (Account acc : accounts) {
            report.append("Account No: ").append(acc.getAccountNo()).append("\n");
            report.append("Type: ").append(acc.getAccountType()).append("\n");
            report.append("Balance: ").append(acc.getBalance()).append(" ").append(acc.getCurrency()).append("\n");
            report.append("Active: ").append(acc.isActive()).append("\n");
            report.append("----------------------------\n");
        }

        report.append("\nLOG FILE CONTENT\n");
        report.append("============================\n");
        report.append(FileLogger.readLogs());

        return report.toString();
    }

    public static String generateUserReport(int userId) {

        dao.AccountDAO accountDAO = new dao.AccountDAO();

        java.util.ArrayList<models.Account> accounts
                = accountDAO.getAccountsByUserId(userId);

        StringBuilder report = new StringBuilder();

        report.append("HESAP ÖZET RAPORU\n");
        report.append("============================\n\n");

        report.append("Kullanıcı ID: ").append(userId).append("\n");
        report.append("Toplam Hesap Sayısı: ").append(accounts.size()).append("\n\n");

        for (models.Account acc : accounts) {
            report.append("Hesap ID: ").append(acc.getAccountId()).append("\n");
            report.append("Hesap No: ").append(acc.getAccountNo()).append("\n");
            report.append("Hesap Türü: ").append(acc.getAccountType()).append("\n");
            report.append("Bakiye: ")
                    .append(acc.getBalance())
                    .append(" ")
                    .append(acc.getCurrency())
                    .append("\n");
            report.append("----------------------------\n");
        }

        return report.toString();
    }
}
