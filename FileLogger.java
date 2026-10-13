/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;

/**
 *
 * @author moham
 */
public class FileLogger {

    private static final String FILE_NAME = "bank_log.txt";

    public static void writeLog(String message) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);

            writer.write("[" + LocalDateTime.now() + "] " + message + "\n");

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readLogs() {
        StringBuilder logs = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

            String line;

            while ((line = reader.readLine()) != null) {
                logs.append(line).append("\n");
            }

            reader.close();

        } catch (Exception e) {
            logs.append("No log file found yet.");
        }

        return logs.toString();
    }
}
