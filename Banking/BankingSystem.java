import java.util.Scanner;

public class BankingSystem {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isOpen = true;
        int choice;

        // Store accounts
        DataBase currentAccount = null;

        while (isOpen) {
            System.out.println("*****************");
            System.out.println("AMERICAN EXPRESS");
            System.out.println("1: CHECK BALANCE");
            System.out.println("2: DEPOSIT");
            System.out.println("3: WITHDRAW");
            System.out.println("4: REQUEST LOAN");
            System.out.println("5: REQUEST CREDIT CARD");
            System.out.println("6: PAY OFF LOAN");
            System.out.println("7: CREDIT CARD PAYMENT");
            System.out.println("8: CREATE ACCOUNT");
            System.out.println("9: EXIT");
            System.out.println("******************");
            System.out.println("CHOOSE A NUMBER 1 - 9");

            choice = scan.nextInt();

            switch (choice) {
                case 1: {
                    if (currentAccount != null) {
                        currentAccount.showBalance();
                    } else {
                        System.out.println("NO ACCOUNT FOUND. CREATE ONE FIRST!");
                    }
                    break;
                }
                case 2: {
                    if (currentAccount != null) {
                        double amount = BankingMethods.deposit();
                        currentAccount.deposit(amount);
                        AccountFileManager.saveAccount(currentAccount);
                    } else {
                        System.out.println("NO ACCOUNT FOUND. CREATE ONE FIRST!");
                    }
                    break;
                }
                case 3: {
                    if (currentAccount != null) {
                        double amount = BankingMethods.withDraw(currentAccount.balance);
                        currentAccount.withdraw(amount);
                        AccountFileManager.saveAccount(currentAccount);
                    } else {
                        System.out.println("NO ACCOUNT FOUND. CREATE ONE FIRST!");
                    }
                    break;
                }
                case 8: {
                    System.out.println("CREATE ACCOUNT");
                    System.out.print("Enter Account Number: ");
                    scan.nextLine();
                    String accNum = scan.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String accHolder = scan.nextLine();

                    currentAccount = new ChequingAccount(accNum, accHolder);
                    System.out.println("ACCOUNT CREATED: " + currentAccount);
                    AccountFileManager.saveAccount(currentAccount);
                    System.out.println();
                    break;
                }
                case 9: {
                    System.out.println("THANK YOU FOR BANKING WITH AMEX");
                    isOpen = false;
                    break;
                }
                default:
                    System.out.println("INVALID NUMBER, CHOOSE BETWEEN 1 - 9");
            }
        }
        scan.close();
    }
}
