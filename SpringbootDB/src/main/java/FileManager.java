package com.example.springbootdb;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileManager {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

    public void writeToFile(String filePath, BankingUser bankingUser) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[" + bankingUser.getDebitCardNumber() + "] " + ", " +
                    "[" + bankingUser.getName() +"] " + ", " +
                    "[BALANCE: $" + String.format("%.2f", bankingUser.getBalance()) + "] -----> " + currentTime + "\n");
            System.out.println("[" + bankingUser.getDebitCardNumber() + "]" + " SAVED TO SYSTEM ");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void saveAccounts(String filePath, List<BankingUser> bankingUser) {
        try (FileWriter fw = new FileWriter(filePath,true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            for(BankingUser u : bankingUser) {
                fw.write("[" + u.getDebitCardNumber() + "] " + ", " +
                        "[" + u.getName() +"] " + ", " +
                        "[BALANCE: $" + String.format("%.2f", u.getBalance()) + "] -----> " + currentTime + "\n");
            }
            System.out.println("ACCOUNTS SAVED TO SYSTEM " + bankingUser + "\t");
        }catch (Exception e){
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

