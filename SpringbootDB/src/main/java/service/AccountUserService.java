package com.example.bankingsystemsb.service;

import FileManager.FileManager;
import com.example.bankingsystemsb.model.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountUserService {

    /* ================= CREATE ================= */

    public BankingUser createUser(BankingUser bankingUser) {
        if (bankingUser.getDebitCardNumber() == null ||
                !bankingUser.getDebitCardNumber().matches("\\d{10,12}")) {
            throw new IllegalArgumentException("Invalid account number");
        }

        FileManager.saveUser("AccountsDB.txt", bankingUser);
        return bankingUser;
    }

    /* ================= UPDATE ================= */

    public void updateUser(String accountNumber, BankingUser updatedUser) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        boolean found = false;

        for (BankingUser u : users) {
            if (u.getDebitCardNumber().equals(accountNumber) && u.isActive()) {
                u.setDebitCardNumber(updatedUser.getDebitCardNumber());
                u.setName(updatedUser.getName());
                u.setId(updatedUser.getId());
                found = true;
                break;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Account not found");
        }

        FileManager.saveAllUsers("AccountsDB.txt", users);
    }

    /* ================= DEPOSIT ================= */

    public void deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        for (BankingUser user : users) {
            if (user.getDebitCardNumber().equals(accountNumber) && user.isActive()) {
                user.setBalance(user.getBalance() + amount);
                FileManager.saveAllUsers("AccountsDB.txt", users);
                FileManager.logDepositBU(user, amount);
                return;
            }
        }

        throw new NoSuchElementException("Account not found");
    }

    /* ================= DEACTIVATE / REACTIVATE ================= */

    public void deactivateUser(String accountNumber) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        for (BankingUser u : users) {
            if (u.getDebitCardNumber().equals(accountNumber)) {
                u.setActive(false);
                FileManager.saveAllUsers("AccountsDB.txt", users);
                return;
            }
        }

        throw new NoSuchElementException("Account not found");
    }

    public void reactivateUser(String accountNumber) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        for (BankingUser u : users) {
            if (u.getDebitCardNumber().equals(accountNumber)) {
                u.setActive(true);
                FileManager.saveAllUsers("AccountsDB.txt", users);
                return;
            }
        }

        throw new NoSuchElementException("Account not found");
    }

    /* ================= STATUS ================= */

    public List<BankingUser> getUsersByStatus(boolean active) {
        return FileManager.loadUsers("AccountsDB.txt")
                .stream()
                .filter(u -> u.isActive() == active)
                .toList();
    }

    /* ================= SEARCH ================= */

    public AccountType searchUser(String accountNumber, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" , ");
                if (parts.length < 3) continue;

                String accNum = parts[0].replace("[", "").replace("]", "").trim();
                String name = parts[1].replace("[", "").replace("]", "").trim();
                String balStr = parts[2].split("----->")[0]
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                double balance = Double.parseDouble(balStr);

                if (accNum.equals(accountNumber)) {
                    return new ChequingAccount(accNum, name, balance);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Search failed", e);
        }

        return null;
    }

    /* ================= SORTING ================= */

    public List<AccountType> sortByBalance() {
        List<AccountType> accounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("AccountsDB.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length < 3) continue;

                String acc = parts[0].replace("[", "").replace("]", "").trim();
                String name = parts[1].replace("[", "").replace("]", "").trim();
                String balStr = parts[2]
                        .split("----->")[0]
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                double balance = Double.parseDouble(balStr);
                accounts.add(new ChequingAccount(acc, name, balance));
            }
        } catch (Exception e) {
            throw new RuntimeException("Sort failed", e);
        }

        accounts.sort((a, b) -> Double.compare(b.getBalance(), a.getBalance()));
        return accounts;
    }

    /* ================= PAGINATION ================= */

    public List<BankingUser> getUsersPaginated(
            int page,
            int size,
            String sort,
            String order,
            Double minBalance
    ) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        users = users.stream()
                .filter(BankingUser::isActive)
                .collect(Collectors.toList());

        if (minBalance != null) {
            users = users.stream()
                    .filter(u -> u.getBalance() >= minBalance)
                    .toList();
        }

        if ("balance".equalsIgnoreCase(sort)) {
            users.sort(
                    "desc".equalsIgnoreCase(order)
                            ? Comparator.comparing(BankingUser::getBalance).reversed()
                            : Comparator.comparing(BankingUser::getBalance)
            );
        }

        int start = page * size;
        if (start >= users.size()) return List.of();

        int end = Math.min(start + size, users.size());
        return users.subList(start, end);
    }

    /* ================= BANK TOTAL ================= */

    public double calculateBankTotal(String fileName) {
        double total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length < 3) continue;

                String balStr = parts[2]
                        .split("----->")[0]
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                total += Double.parseDouble(balStr);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate bank total", e);
        }

        return total;
    }
}
