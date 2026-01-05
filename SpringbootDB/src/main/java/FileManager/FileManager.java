package FileManager;
import com.example.bankingsystemsb.*;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class FileManager {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

    public FileManager() throws IOException {
    }

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

    public static void logTransaction(AccountType fromAccount, AccountType toAccount, double amount) {
        try (FileWriter fw = new FileWriter("Transactions.json", true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[E-TRANSFER] "
                    + "[" + fromAccount.accountNumber + "] "
                    + "sent $" + String.format("%.2f", amount)
                    + " to [" + toAccount.accountNumber + "]"
                    + " -----> " + dtf + "\n");

            fw.write("[" + fromAccount.accountNumber + "] NEW BALANCE: $"
                    + String.format("%.2f", fromAccount.balance) + "\n");
            fw.write("[" + toAccount.accountNumber + "] NEW BALANCE: $"
                    + String.format("%.2f", toAccount.balance) + "\n\n");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void readFiles(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void logDeposit(AccountType accountType, double amount) {
        try (FileWriter fw = new FileWriter("AccountDep.txt", true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[" + accountType.accountNumber + "] Deposited --> $" + String.format("%.2f", amount) + " At [" + currentTime + "]\n");
            fw.write("[" + accountType.accountNumber + "] NEW BALANCE: $" + String.format("%.2f", accountType.getBalance()) + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());
        }
    }

    public static void logDepositBU(BankingUser bankingUser, double amount) {
        try (FileWriter fw = new FileWriter("AccountDep.txt", true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[" + bankingUser.getDebitCardNumber() + "] Deposited --> $" + String.format("%.2f", amount) + " At [" + currentTime + "]\n");
            fw.write("[" + bankingUser.getDebitCardNumber() + "] NEW BALANCE: $" + String.format("%.2f", bankingUser.getBalance()) + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());
        }
    }

    public static void logWithdraw(AccountType accountType, double amount) {
        try (FileWriter fw = new FileWriter("AccountWithDraw.txt", true)) {
            String currentTime = LocalDateTime.now().format(dtf);
            fw.write("[" + accountType.accountNumber + "] Withdrew --> $" + String.format("%.2f", amount) + " At [" + currentTime + "]\n");
            fw.write("[" + accountType.accountNumber + "] NEW BALANCE: $" + String.format("%.2f", accountType.getBalance()) + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());
        }
    }


    public static void logLoans(AccountType accountType, double loan) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Loans.txt", true))) {
            String currentTime = LocalDateTime.now().format(dtf);
            bw.write("[" + accountType.accountNumber + "]" + "[" + accountType.accountHolder + "]" + " Loaned --> $" + String.format("%.2f", loan) + " At [" + currentTime + "]\n");
        } catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());
        }
    }

    public static void eTransaction(AccountType fromAccount, AccountType toAccount, double amount) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("e-transfer.txt", true))) {
            String currentTime = LocalDateTime.now().format(dtf);
            bw.write("[" + fromAccount.accountHolder + "]" + " e-Transfered" + " [" + toAccount.accountHolder + "] $" + String.format("%.2f", amount) + " At [" + currentTime + "]\n");

        } catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());
        }
    }

    public static void eTransactionBU(BankingUser fromAccount, BankingUser toAccount, double amount) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("e-transfer.txt", true))) {
            String currentTime = LocalDateTime.now().format(dtf);
            bw.write("[" + fromAccount.getName() + "]" + " e-Transfered" + " [" + toAccount.getName() + "] $" + String.format("%.2f", amount) + " At [" + currentTime + "]\n");

        } catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());
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

    public static void saveTransferData(String fileName, List<Transaction> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {

            bw.write("[\n");

            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);

                bw.write("  {\n");
                bw.write("    \"accountNumber\": \"" + t.getAccountNumber() + "\",\n");
	        bw.write("    \"accountType\": \"" + t.getAccountType() + "\",\n");
                bw.write("    \"type\": \"" + t.getType() + "\",\n");
                bw.write("    \"balance\": " + t.getBalance() + ",\n");
                bw.write("    \"amount\": " + t.getAmount() + ",\n");
                bw.write("    \"date\": \"" + t.getDate() + "\"\n");
                bw.write("  }");

                if (i < transactions.size() - 1) {
                    bw.write(",");
                }
                bw.write("\n");
            }

            bw.write("]");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save transactions", e);
        }
    }

    public static List<Transaction> loadTransactions(String fileName) {
        List<Transaction> transactions = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String accountNumber = null;
	    AccountTP accType = null;
            TransactionType type = null;
            double balance = 0.0;
            double amount = 0;
            LocalDate date = null;

            while((line = br.readLine()) != null) {
                line = line.trim();

                if (line.equals("[") || line.equals("]") || line.isEmpty()) continue; // Skip array brackets

                if(line.equals("{")) {
                    accountNumber = null;
		    accType = null;
                    type = null;
                    balance = 0.0;
                    amount = 0;
                    date = null;
                }

                if (line.contains("\"accountNumber\"")) {
                    accountNumber = line.split(":")[1] // retrieves the second half aka the accountNumber
                            .replace("\"", "")
                            .replace(",", "")
                            .trim();
                }

		if(line.contains("\"accountType\"")) {
                    String strAccType = line.split(":")[1]
                            .replace("\"", "")
                            .replace(",", "")
                            .trim();

                    accType = AccountTP.valueOf(strAccType);
                }

                if (line.contains("\"type\"")) {
                    String typeStr = line.split(":")[1]
                            .replace("\"", "")
                            .replace(",", "")
                            .trim();

                    type = TransactionType.valueOf(typeStr);
                }

                if (line.contains("\"balance\"")) {
                    balance = Double.parseDouble(
                            line.split(":")[1]
                                    .replace(",", "")
                                    .trim()
                    );
                }

                if (line.contains("\"amount\"")) {
                    amount = Double.parseDouble(
                            line.split(":")[1]
                                    .replace(",", "")
                                    .trim()
                    );
                }

                if (line.contains("\"date\"")) {
                    date = LocalDate.parse(
                            line.split(":")[1]
                                    .replace("\"", "")
                                    .replace(",", "")
                                    .trim()
                    );
                }

                if(line.startsWith("}") && accountNumber != null) {
                    transactions.add(new Transaction(accountNumber, accType, type, balance, amount, date));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return transactions;
    }
}
