/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author moham
 */
public class Validator {

    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]{4,}$");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^05[0-9]{9}$");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$");
    }
}
