package com.example.bankingsystemsb;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import FileManager.FileManager;
import jdk.dynalink.linker.ConversionComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/banking-users")
public class BankingUserController {

    private double parseBalance(String balanceString) {
        String cleaned = balanceString.replace("Balanced: $", "").trim();
        return Double.parseDouble(cleaned);

    }

    @PostMapping("/create")
    public ResponseEntity<BankingUser> createUser(@RequestBody BankingUser bankingUser) {
        FileManager.saveBankingUser("AccountsDB.txt", bankingUser);
        return ResponseEntity.status(201).body(bankingUser);
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        if(amount <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount");
        }

        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        for(BankingUser user : users) {
            if(user.getDebitCardNumber().equals(accountNumber)) {
                user.setBalance(user.getBalance() + amount);
                FileManager.saveAllUsers("AccountsDB.txt", users);
                FileManager.logDepositBU(user, amount);
                return ResponseEntity.ok("Deposited Successfully");
            }
        }
        return ResponseEntity.status(404).body("Account not found");
    }

    @PutMapping("/update/{accountNumber}") // PathVariable doesn't need to add accountNumber=... in the URL you js write it there
    public ResponseEntity<String> updateUser(@PathVariable String accountNumber, @RequestBody BankingUser updatedUser) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        boolean found = false;

        for (int i = 0; i < users.size(); i++) {
            BankingUser u = users.get(i);

            if (u.getDebitCardNumber().equals(accountNumber)) {
                u.setDebitCardNumber(updatedUser.getDebitCardNumber());
                u.setName(updatedUser.getName());
                u.setId(updatedUser.getId());
                found = true;
                break;
            }
        }

        if (!found) {
            return ResponseEntity.status(404).body("Account not found");
        }

        FileManager.saveAllUsers("AccountsDB.txt", users);
        return ResponseEntity.ok("Account updated");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String accountNumber) {

        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        boolean removed = users.removeIf(user -> user.getDebitCardNumber().equals(accountNumber)
        );

        if (!removed) {
            return ResponseEntity.status(404).body("Account not found");
        }

        FileManager.saveAllUsers("AccountsDB.txt", users);

        return ResponseEntity.ok("Account deleted");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam double amount
    ) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount");
        }

        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        BankingUser sender = null;
        BankingUser receiver = null;

        for (BankingUser u : users) {
            if (u.getDebitCardNumber().equals(fromAccount)) sender = u;
            if (u.getDebitCardNumber().equals(toAccount)) receiver = u;
        }

        if (sender == null || receiver == null) {
            return ResponseEntity.status(404).body("Account(s) Not Found");
        }

        if (sender.getBalance() < amount) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        FileManager.saveAllUsers("AccountsDB.txt", users);
        FileManager.eTransactionBU(sender, receiver, amount);

        return ResponseEntity.ok("Transfer Successful");
    }

    @GetMapping("/generateFake") // generates a random amount of f1 users
    public List<BankingUser> generateFake() {
        List<BankingUser> bankingUsers = new ArrayList<>();

        String[] names = {
                "Max Verstappen", "Liam Lawson", "Lando Norris", "Oscar Piastri",
                "George Russell", "Andrea Kimi Antonelli", "Fernando Alonso",
                "Lance Stroll", "Pierre Gasly", "Jack Doohan", "Esteban Ocon",
                "Oliver Bearman", "Yuki Tsunoda", "Isack Hadjar", "Alexander Albon",
                "Carlos Sainz", "Nico Hulkenberg", "Gabriel Bortoleto",
                "Charles Leclerc", "Lewis Hamilton",  "Michael Schumacher", "Ayrton Senna", "Alain Prost", "Niki Lauda", "Jim Clark",
                "Juan Manuel Fangio", "Jackie Stewart", "Sebastian Vettel", "Jenson Button", "Rubens Barrichello",
                "Nelson Piquet Sr.", "Gilles Villeneuve", "Jacques Villeneuve", "Mario Andretti", "Eddie Irvine",
                "Damon Hill", "Graham Hill", "Phil Hill", "Ronnie Peterson", "Riccardo Patrese",
                "Gerhard Berger", "Mika Hakkinen", "David Coulthard", "Mark Webber", "Ralf Schumacher",
                "Heikki Kovalainen", "Jarno Trulli", "Jean-Eric Vergne", "Timo Glock", "Nico Rosberg",
                "Kimi Raikkonen", "Robert Kubica", "Christian Klien", "Vitantonio Liuzzi", "Pedro de la Rosa",
                "Takuma Sato", "Anthony Davidson", "Adrian Sutil", "Bruno Senna", "Patrick Depailler",
                "Jean-Pierre Jabouille", "Maurice Trintignant", "Bruce McLaren", "Chris Amon", "Jochen Rindt",
                "Clay Regazzoni", "John Surtees", "Tony Brooks", "Alan Jones", "Carlos Reutemann",
                "Jacques Laffite", "Mike Hawthorn", "Peter Collins", "Hans Stuck", "Jo Bonnier",
                "Innes Ireland", "Guy Ligier", "Jean Alesi", "Stefan Bellof", "Marc Surer",
                "Elio de Angelis", "Henri Pescarolo", "Ivan Capelli", "Stefano Modena", "Christian Danner",
                "Emanuele Pirro", "Olivier Panis", "Pedro Rodriguez", "Ricardo Rodriguez", "Jo Siffert",
                "Dan Gurney", "Jack Brabham", "Denny Hulme", "Tim Schenken", "Carlos Pace",
                "Howden Ganley", "Lucien Bianchi", "Lella Lombardi", "Andrea de Cesaris", "Riccardo Paletti",
                "Rene Arnoux", "Jean-Pierre Beltoise", "Gabriele Tarquini", "Alfonso de Portago", "Giuseppe Farina"
        };

        int count = (int) (Math.random() * 20) + 1;
        for (int i = 0; i < count; i++) {

            Integer id = (int)  (Math.random() * 1000);
            String name = names[(int) (Math.random() * names.length)];
            String acc = String.valueOf((long) (Math.random() * 1_000_000_000000L));
            double bal = Math.round(Math.random() * 1_000_000 * 100.0) / 100.0; // rounds to 2 decimal places

            bankingUsers.add(new BankingUser(acc, name, id, bal));
        }
<<<<<<< HEAD
<<<<<<< HEAD
	FileManager.saveBankingUsers(bankingUsers);
=======
	FileManager.saveBankingUsers(bankingUsers)
>>>>>>> beta
        return bankingUsers; // auto converst to JSON format
=======
>>>>>>> beta

        FileManager.saveBankingUsers(bankingUsers);
        return bankingUsers;

    }

    //@RequestMapping takes in the parameters in the URL and stores it in the params
    @GetMapping("/search") // /search?accountNumber=219321312&fileName=AccountsDB.txt
    public AccountType searchUser(@RequestParam String accountNumber, @RequestParam String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" , "); // index 0 will have [ [812939821231] ] followed by index 1 [ [Max Verstappen] ] ...etc [BALANCE: $20000.00] -----> [30/11/2025  22:39:06]


                if (parts.length < 3) continue;
                String accNum = parts[0].replace("[", "").replace("]", "").trim();
                String name = parts[1].replace("[", "").replace("]", "").trim();
                String balStr = parts[2].split("----->")[0] // split into 2 indexes and only taking the first half [0] and removing the second
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                double balance = parseBalance(balStr);

                if (accNum.equals(accountNumber)) {
                    return new ChequingAccount(accNum, name, balance);
                }

            }
        } catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }
        return null;
    }

    private BankingUser findUser(String accountNumber) { // try replacing the try catch w loadUsers
        try (BufferedReader br = new BufferedReader(new FileReader("AccountsDB.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" , ");
                if (parts.length < 3) continue;

                String acc = parts[0]
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                String name = parts[1]
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                String balancePart = parts[2].split("----->")[0];

                String balStr = balancePart
                        .replace("[BALANCE:", "")
                        .replace("]", "")
                        .replace("$", "")
                        .trim();

                double balance = Double.parseDouble(balStr);

                System.out.println("FOUND ACCOUNT: " + acc);

                if (acc.equals(accountNumber)) {
                    return new BankingUser(acc, name, 0, balance);
                }
            }
        } catch (Exception e) {
            System.out.println("findUser error: " + e.getMessage());
        }

        return null;
    }


    @GetMapping(value = "/bankTotal", produces = "text/plain")
    public String bankTotal(@RequestParam String fileName) {
        StringBuilder sb = new StringBuilder();
        double balance = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                String[] parts = line.split(" , ");

                if (parts.length > 0) {
                    String balStr = parts[2].split("----->")[0]
                            .replace("[BALANCE:", "")
                            .replace("]", "")
                            .replace("$", "")
                            .trim();
                    balance += parseBalance(balStr);
                }
            }
        } catch (IOException e) {
            System.out.println("Error While Reading File" + e.getMessage());
        }

        sb.append("\nBANK NETWORTH: $ ").append(String.format("%.2f", balance)).append("\n");
        return sb.toString();

    }

    @GetMapping("/sortByBalance")
    public List<AccountType> sortBalance(@RequestParam String fileName) {
        ArrayList<AccountType> accounts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("AccountsDB.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length < 3) continue;
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
        // (Senario) Hey robot when you pick up any two accounts, use THIS rule to decide which goes first!"
        accounts.sort((x, y) -> Double.compare(y.getBalance(), x.getBalance()));
        return accounts;
    }


    @GetMapping("/sortByOrder")
    public ResponseEntity<List<BankingUser>> getUsers(
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultvalue = "5") int size,
            @RequestParam(required = false) String sort, // this typing sort= isn't required in the localhost url
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false) Double minBalance
    ) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        if (minBalance != null) { // if minBalance was provided
            users = users.stream() //A stream lets us process the list step-by-step
                    .filter(u -> u.getBalance() >= minBalance) //u -> u.getBalance() >= minBalance means:
                                                                        //•	Keeps only users whose balance is >= minBalance
                    .collect(Collectors.toList()); // Collects the filtered users back into a list

        }

        if ("balance".equalsIgnoreCase(sort)) {
            users.sort(
                    "desc".equalsIgnoreCase(order)
                            ? Comparator.comparing(BankingUser::getBalance).reversed()
                            // Creates a comparator that sorts BankingUser objects by balance in descending order
                            // basically does return user1.getBalance() - user2.getBalance();
                            : Comparator.comparing(BankingUser::getBalance) // ascending order is default
            );
        }

        int start = page * size;
	if(start >= users.size()) return ResponseEntity.ok(users.subList(start,end));

	int end = Math.min(start + size, users.size());
    }

@GetMapping("/paginatedData")
    public ResponseEntity<PaginatedResponse<BankingUser>> getData(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        int totalUsers = users.size();
        int start = page * size;
        int end = Math.min(start + size, users.size());

        if (start >= totalUsers) {
            return ResponseEntity.ok(new PaginatedResponse<BankingUser>(page, size, totalUsers, Collections.<BankingUser>emptyList()
                    )
            );
        }

        List<BankingUser> pagedUsers = users.subList(start, end);

        PaginatedResponse<BankingUser> response = new PaginatedResponse<>(page, size, totalUsers, pagedUsers);
        return ResponseEntity.ok(response);

    }



    @GetMapping(value = "/exist", produces = "text/plain")
    public String doesUserExist(@RequestParam String accountNumbers, @RequestParam String fileName) {
        HashSet<String> accounts = new HashSet<>();
        String[] numbersToCheck = accountNumbers.split(",");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) { // stores every single accnum to the hashset accounts
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length > 0) {
                    String accNum = parts[0].replace("[", "").replace("]", "").trim();
                    accounts.add(accNum);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        for (String number : numbersToCheck) { // goes through the numbersToCheck array which has all the requested accountnumbers to find if exist
            String trimmed = number.trim();
            if (accounts.contains(trimmed)) {
                sb.append(trimmed).append(": FOUND\n");
            } else {
                sb.append(trimmed).append(": NOT FOUND\n");
            }
        }
        return sb.toString();

    }


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


    @GetMapping(value = "/readFile", produces = "text/plain")
    public String readAccType(@RequestParam String fileName) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error While Reading File" + e.getMessage());
        }

        return sb.toString();

    }

}

