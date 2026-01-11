package com.example.bankingsystemsb.repository;

import com.example.bankingsystemsb.model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankUserRepository {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

    public static void saveBankingUser(String filePath, BankingUser bankingUser) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[" + bankingUser.getDebitCardNumber() + "] " + ", " +
                    "[" + bankingUser.getName() + "] " + ", " +
                    "[BALANCE: $" + String.format("%.2f", bankingUser.getBalance()) + "] -----> " + "[" + currentTime + "]" + "\n");
            System.out.println("[" + bankingUser.getDebitCardNumber() + "]" + " SAVED TO SYSTEM ");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void saveBankingUsers(List<BankingUser> bankingUsers) {
        try (FileWriter fw = new FileWriter("AccountsDB.txt", true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            for (BankingUser user : bankingUsers) {
                fw.write("[" + user.getDebitCardNumber() + "] , " +
                        "[" + user.getName() + "] , " +
                        "[BALANCE: $" + String.format("%.2f", user.getBalance()) + "] -----> " +
                        "[" + currentTime + "]\n"
                );
                System.out.println("[ID:" + user.getId() + "] SAVED TO SYSTEM");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void saveUser(String fileName, BankingUser bankingUser) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            String line =
                    "[" + bankingUser.getDebitCardNumber() + "] , " +
                            "[" + bankingUser.getName() + "] , " +
                            "[BALANCE: $" + String.format("%.2f", bankingUser.getBalance()) + "] , " +
                            "[ACTIVE:" + bankingUser.isActive() + "] -----> " +
                            "[" + LocalDateTime.now().format(formatter) + "]";

            bw.write(line);
            bw.newLine();
        } catch (IOException e)
        {
            System.out.println("Error saving users: " + e.getMessage());
        }

    }



    public static void saveAllUsers(String fileName, List<BankingUser> users) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (BankingUser u : users) {
                String line =
                        "[" + u.getDebitCardNumber() + "] , " +
                                "[" + u.getName() + "] , " +
                                "[BALANCE: $" + String.format("%.2f", u.getBalance()) + "] , " +
                                "[ACTIVE:" + u.isActive() + "] -----> " +
                                "[" + LocalDateTime.now().format(formatter) + "]";

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    public static void saveAccountType(String filePath, AccountType accountType) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[" + accountType.accountNumber + "] " + ", " +
                    "[" + accountType.accountHolder + "] " + ", " +
                    "[BALANCE: $" + String.format("%.2f", accountType.getBalance()) + "] -----> " + "[" + currentTime + "]" + "\n");
            System.out.println("[" + accountType.accountNumber + "]" + " SAVED TO SYSTEM ");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void saveAccountsType(String filePath, List<AccountType> accountType) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            for (AccountType a : accountType) {
                fw.write("[" + a.accountNumber + "] " + ", " +
                        "[" + a.accountHolder + "] " + ", " +
                        "[BALANCE: $" + String.format("%.2f", a.getBalance()) + "] -----> " + "[" + currentTime + "]" + "\n");
            }
            System.out.println("ACCOUNTS SAVED TO SYSTEM " + accountType + "\t");
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    public static List<BankingUser> loadUsers(String fileName) {
        List<BankingUser> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String cleanLine = line.split(" -----> ")[0];

                String[] parts = cleanLine.split(" , ");
                if (parts.length < 4) continue;

                String accountNumber = parts[0]
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                String name = parts[1]
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                double balance = Double.parseDouble(
                        parts[2]
                                .replace("[BALANCE:", "")
                                .replace("]", "")
                                .replace("$", "")
                                .trim()
                );

                boolean active = Boolean.parseBoolean(
                        parts[3]
                                .replace("[ACTIVE:", "")
                                .replace("]", "")
                                .trim()
                );

                users.add(new BankingUser(accountNumber, name, 0, balance, active));
            }

        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }
    public static void savePaginatedResponse(PaginatedResponse<BankingUser> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("PaginatedData.json"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"page\": ").append(data.getPage()).append(",\n");
            sb.append("  \"size\": ").append(data.getSize()).append(",\n");
            sb.append("  \"totalUsers\": ").append(data.getTotalUsers()).append(",\n");
            sb.append("  \"data\": [\n");

            for (int i = 0; i < data.getData().size(); i++) {
                BankingUser u = data.getData().get(i);
                sb.append("    {\n");
                sb.append("      \"debitCardNumber\": \"").append(u.getDebitCardNumber()).append("\",\n");
                sb.append("      \"name\": \"").append(u.getName()).append("\",\n");
                sb.append("      \"id\": ").append(u.getId()).append(",\n");
                sb.append("      \"balance\": ").append(u.getBalance()).append(",\n");
                sb.append("      \"active\": ").append(u.isActive()).append("\n");
                sb.append("    }");
                if (i < data.getData().size() - 1) sb.append(",");
                sb.append("\n");
            }

            sb.append("  ]\n");
            sb.append("}");

            bw.write(sb.toString());
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}

