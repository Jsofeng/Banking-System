import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class DBTestcases {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Create accounts
        DataBase acc1C = new ChequingAccount("123456789101112", "John McQueen");
        DataBase acc2S = new SavingsAccount("200034567891", "Sarah Smith");
        DataBase acc2C = new ChequingAccount("696969696969", "sixty-Nine");
        // Perform transactions
        acc1C.deposit(100000);
        acc2S.deposit(100000);

        acc1C.withdraw(2300);
        acc2S.withdraw(5000);

        // Show balances
        acc1C.showBalance();
        acc2S.showBalance();

        AccountFileManager.saveAccount(acc1C);
        AccountFileManager.saveAccount(acc2S);
        AccountFileManager.saveAccount(acc2C);


        List<DataBase> addAccounts = new ArrayList<>();
        addAccounts.add(new ChequingAccount("100231313131311", "lightning McQueen"));
        addAccounts.add(new SavingsAccount("9204810983131", "Lewis Hamilton"));

        AccountFileManager.saveAccounts(addAccounts);

        acc1C.eTransfer(acc2S, 10000);

        List<String> accounts = AccountFileManager.loadAccounts(); // BufferedReader
        for(String acc : accounts) {
            System.out.println(acc);
        }

        scan.close();
    }
}
