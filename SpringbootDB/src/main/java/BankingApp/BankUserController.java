package com.example.springbootdb.BankingApp;
import java.util.ArrayList;
import java.util.List;

import FileManager.FileManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/banking-users")
public class BankingUserController {
    @GetMapping
    public List<BankingUser> getBankingUsers() {
        return List.of(new BankingUser("446916010129332", "Jonathan", 27, 10000),
                new BankingUser("298318321932132", "Joji", 67, 200000),
                new BankingUser("8231939123", "Leclerc", 16, 500000),
                new BankingUser("29873839198312", "Max Verstappen", 1, 823281));
    }

    /*
    @GetMapping("/display")
    public List<String> displayUserInfo() {
        BankingUser account1 = new BankingUser("123456789", "Lewis Hamilton", 44, 9012932);
        BankingUser account2 = new BankingUser("16394392423", "Charles Leclerc", 16, 281313);
        return List.of(account1.accountDetails(), account2.accountDetails());
    }
*/
    @GetMapping(value = "/display", produces = "text/plain") // formats it so that after each statement it adds \n
    public String displayUserInfo() {

        ArrayList<BankingUser> accounts = new ArrayList<>();
        accounts.add(new BankingUser("123456789", "Lewis Hamilton", 44, 9012932));
        accounts.add(new BankingUser("1601923921", "Charles Leclerc", 16, 16161616));
        accounts.add(new BankingUser("0101010101", "Max Verstappen", 1, 1001010101));

        StringBuilder sb = new StringBuilder();
        for (BankingUser u : accounts) {
            sb.append(u.accountDetails()).append("\n\n");
        }

        return sb.toString();
    }

    @GetMapping(value = "/save", produces = "text/plain")
    public String fileReading() {
        ArrayList<BankingUser> accounts = new ArrayList<>();
        accounts.add(new BankingUser("5152006", "Kang Haerin", 15, 92013201));
        accounts.add(new BankingUser("08262005", "Pharita", 26, 2925232));

        FileManager.saveAccounts("wives.txt", accounts);
        StringBuilder sb = new StringBuilder();
        sb.append("ACCOUNTS SAVED: \n\n");
        for(BankingUser u : accounts) {
            sb.append(u.accountDetails()).append("\n\n");
        }
        return sb.toString();
    }

     @GetMapping(value = "/readAcc", produces = "text/plain")
    public String readAccType() {
        String pathName = "AccountsDB.txt";
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error While Reading File" + e.getMessage());
        }

        return sb.toString();

    }

    @GetMapping(value = "transactions", produces = "text/plain")
    public String readTransactions() {
        String pathName = "Transactions.txt";
        StringBuilder sb = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }catch (IOException e) {
            System.out.println("Error While Reading File" + e.getMessage());
        }
        return sb.toString();
    }

    @GetMapping(value = "ACCDB", produces = "text/plain")
    public String accountDataBase() {
        String file = "AccountsDB.txt";
        StringBuilder sb = new StringBuilder();
        sb.append("ACCOUNTS DATABASE\n");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null ) {
                sb.append("\n").append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error While Reading File" + e.getMessage());
        }
        return sb.toString();
    }



    @GetMapping(value = "Loans", produces = "text/plain")
    public String loansDataBase() {
        String file = "LoansDB.txt";
        StringBuilder sb = new StringBuilder();
        
        try(BufferedReader br = new BufferedReader((new FileReader(file)))) {
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }catch (IOException e) {
            System.out.println("Error While Reading File: " + e.getMessage());
        }
        
        return sb.toString();
    }

    @GetMapping(value = "eTransfer", produces = "text/plain")
    public String transferDataBase() {
        String file = "e-transfer.txt";
        StringBuilder sb = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error While Reading File: " + e.getMessage());
        }

        return sb.toString();
    }
	private double parseBalance(String balanceString) {
        	String cleaned = balanceString.replace("Balanced: $", "").trim();
       	        return Double.parseDouble(cleaned);

	{

 @GetMapping("/search") // /search?accountNumber=219321312&fileName=AccountsDB.txt
    public AccountType searchUser(@RequestParam String accountNumber, @RequestParam String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                if(line.trim().isEmpty()) continue;

                String[] parts = line.split(" , "); // index 0 will have [ [812939821231] ] followed by index 1 [ [Max Verstappen] ] ...etc [BALANCE: $20000.00] -----> [30/11/2025  22:39:06]


                if(parts.length < 3) continue;
                String accNum = parts[0].replace("[", "").replace("]", "").trim();
                String name = parts[1].replace("[", "").replace("]", "").trim();
                String balStr = parts[2].split("----->")[0] // split into 2 indexes and only taking the first half [0] and removing the second
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                double balance =  parseBalance(balStr);

                if(accNum.equals(accountNumber)) {
                    return new ChequingAccount(accNum, name, balance);
                }

            }
        } catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }
        return null;
    }

	@GetMapping("/sortByBalance")
	public List<AccountType> sortBalance(@RequestParam String fileName) {
        	ArrayList<AccountType> accounts  = new ArrayList<>();
       		try(BufferedReader br = new BufferedReader(new FileReader("AccountsDB.txt"))) {
           	 String line;
           	 while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" , ");
                    if(parts.length < 3) continue;
                    String accNum = parts[0].replace("[", "").replace("]", "").trim();
                    String name = parts[1].replace("[", "").replace("]", "").trim();
                    String balStr = parts[2]
                        .split("----->")[0]
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                    double balance = parseBalance(balStr);
                    accounts.add(new ChequingAccount(accNum, name, balance));
            }
    
        } catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }

        accounts.sort((x, y) -> Double.compare(y.getBalance(), x.getBalance()));
        return accounts;
}


}
