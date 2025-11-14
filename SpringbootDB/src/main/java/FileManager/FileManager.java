package FileManager;
import com.example.springbootdb.BankingApp.AccountType;
import com.example.springbootdb.BankingApp.BankingUser;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileManager {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

    public static void saveAccount(String filePath, BankingUser bankingUser) {
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

    public static void logTransaction(AccountType fromAccount, AccountType toAccount, double amount) {
        try(FileWriter fw = new FileWriter("Transactions.txt", true)) {
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
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String str;
              while((str = br.readLine()) != null) {
                  System.out.println(str);
              }
        }catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}


}


