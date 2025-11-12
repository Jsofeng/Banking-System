import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AccountFileManager {
    static DateTimeFormatter currentDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");

     /**
     * @param account -> retrieves the account information and writes the info in "accounts.txt" (Meant for saving accounts individually_
     */

    public static void saveAccount(DataBase account) {


        try (FileWriter fw = new FileWriter("accounts.txt", true)) {
            String dtf = LocalDateTime.now().format(currentDateTime);
            fw.write("[" + account.accountNumber + "] " + ", " +
                     "[" + account.accountHolder +"] " + ", " +
                    "[BALANCE: $" + String.format("%.2f", account.balance) + "] -----> " + dtf + "\n");
            System.out.println("[" + account.accountNumber + "]" + " SAVED TO SYSTEM ");

        }catch (IOException e) {
            System.out.println("ERROR" + e.getMessage());
        }

    }

    /**
     * @param accounts -> retrieves multiple account's information and writes the info in "accounts.txt" (Meant for saving multiple accounts at once)
     */

    public static void saveAccounts(List<DataBase> accounts) {
        String dtf = LocalDateTime.now().format(currentDateTime);
        try(FileWriter fw = new FileWriter("accounts.txt", true)) {
         for(DataBase account : accounts) {
             fw.write("[" + account.accountNumber + "] " + ", " +
                     "[" + account.accountHolder +"] " + ", " +
                     "[BALANCE: $" + String.format("%.2f", account.balance) + "] -----> " + dtf + "\n");
         }
            System.out.println("ACCOUNTS SAVED TO SYSTEM " + accounts + "\t"); // java automatically applies .toString because of string concatenation
        } catch (IOException e) {
            System.out.println("ERROR WHILE SAVING ACCOUNTS" + e.getMessage());
        }
    }
    
     /**
     * @param fromAccount -> Takes in the account that's e-transferring
     * @param toAccount -> Takes in the account that is the amount is being transferred to
     * @param amount -> Amount being transferred
     */
    public static void logTransaction(DataBase fromAccount, DataBase toAccount, double amount) {
        String dtf = LocalDateTime.now().format(currentDateTime);
        try (FileWriter fw = new FileWriter("accounts.txt", true)) {
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
            System.out.println("ERROR LOGGING TRANSACTION: " + e.getMessage());
        }
    }

    /**
     * @return reads all data stored in accounts.txt
     */
    public static List<String> loadAccounts() {
        List<String> accounts = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while((line = br.readLine()) != null) {
                accounts.add(line);
            }
        }catch (IOException e) {
            System.out.println("ERROR READING FILE LINE: " + e.getMessage());
        }
        return accounts;
    }


}
