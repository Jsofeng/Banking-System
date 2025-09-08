import java.util.Random;
import java.util.Scanner;
class BankingMethods extends BankingAccount
{
    static Scanner scan = new Scanner(System.in);

    public BankingMethods(String accountNumber, String accountHolder)
    {
        super(accountNumber, accountHolder);
    }


    static void showBalance(double balance)
    {
        System.out.printf("BALANCE: $%.2f\n", balance);
    }

    static double deposit()
    {
        System.out.print("ENTER AMOUNT TO DEPOSIT: $");
        double amount = scan.nextDouble();
        if (amount < 0)
        {
            System.out.println("INVALID AMOUNT, TRY AGAIN");
            return deposit();
        }
        else
        {
            return amount;
        }
    }

    static double withDraw(double balance)
    {
        System.out.print("ENTER AMOUNT TO WITHDRAW: $");
        double amount = scan.nextDouble();
        if (amount > balance)
        {
            System.out.println("INSUFFICIENT FUNDS, TRY AGAIN");
            return withDraw(balance);
        }
        else if (amount < 0)
        {
            System.out.println("INVALID AMOUNT, TRY AGAIN");
            return withDraw(balance);
        }
        else
        {
            return amount;
        }
    }

    static void requestCreditCard(String name, int age, String ssn)
    {

        System.out.println("VERIFYING INFORMATION...");
        System.out.println(
                "PLEASE CHECK IF INFORMATION IS CORRECT " + "[ NAME: " + name + ", AGE: " + age + ", SSN: " + ssn +
                " ]");
        System.out.println("IF CORRECT, ENTER Y, IF NOT, ENTER N");
        char response = scan.next().toUpperCase().charAt(0);
        if (response == 'Y')
        {
            System.out.println("APPROVED! CREDIT CARD WILL BE SENT WITHIN 1-5 BUSINESS DAYS");
            System.out.println("5 BUSINESS DAYS LATER...");
            Random rand = new Random();
            long min = 100000000000000L;
            long max = 999999999999999L;
            long cardNumber =
                    min + ((long) (rand.nextDouble() * (max - min + 1))); // long turns the double into a whole number
            System.out.println("CONGRATULATIONS " + name + ", YOUR CREDIT CARD NUMBER IS: " + "[" + cardNumber + "]");
            System.out.println("PLEASE ACTIVATE YOUR CARD UPON RECEIPT");
        }
        else if (response == 'N')
        {
            System.out.println("PLEASE RE-ENTER YOUR INFORMATION\n");
            System.out.println("NAME: ");
            scan.nextLine();
            name = scan.nextLine();
            System.out.println("AGE: ");
            age = scan.nextInt();
            System.out.println("SSN: ");
            scan.nextLine();
            ssn = scan.nextLine();
            while (!ssn.matches("\\d{9}"))
            {
                System.out.println("INVALID SSN, MUST BE 9 DIGITS, PLEASE TRY AGAIN");
                ssn = scan.nextLine();

            }
            requestCreditCard(name, age, ssn); // recursive call to re-enter information if user enters N
        }
        else
        {
            System.out.println("INVALID RESPONSE, PLEASE TRY AGAIN");
        }


    }

    static void loan(double balance)
    {
        if (balance >= 10000)
        {
            System.out.println("YOU ARE ELIGIBLE FOR A LOAN");
            System.out.print("PLEASE ENTER AMOUNT TO LOAN: $ ");
            double loanAmount = scan.nextDouble();

            while (loanAmount >= balance)
            {
                System.out.println("LOAN AMOUNT EXCEEDS BALANCE, PLEASE TRY AGAIN");
                loanAmount = scan.nextDouble();
            }

            if (loanAmount < balance)

            {
                System.out.println("LOAN PROCESSING...");
                System.out.printf("LOAN APPROVED! YOU HAVE BEEN LOANED: $" + "%.2f\n", loanAmount);
                System.out.println();
                System.out.println("PLEASE READ THE TERMS AND CONDITIONS BELOW");
                System.out.println("PLEASE NOTE: LOAN MUST BE REPAID WITHIN 12 MONTHS WITH A 5% INTEREST RATE");
                System.out.println();
                System.out.printf("BALANCE: $: " + "%.2f\n", balance);
                System.out.printf("LOAN AMOUNT: $" + "%.2f\n", loanAmount);
            }
        }
        else
        {
            System.out.println("YOU ARE NOT ELIGIBLE FOR A LOAN");
            System.out.println("SUFFICIENT FUNDS: " + (10000.00 - balance) + " MORE TO QUALIFY");
        }


    }

    static void createAccount(String accountNumber, String accountHolder)
    {
        System.out.println("CREATE NEW ACCOUNT");
        System.out.println("PLEASE ENTER ACCOUNT HOLDER FULL NAME: ");
        accountHolder = scan.nextLine();
        while (!accountHolder.matches("[a-zA-Z ]+"))
        {
            System.out.println("LETTERS ONLY, PLEASE TRY AGAIN");
            System.out.println("PLEASE ENTER ACCOUNT HOLDER NAME: ");
            accountHolder = scan.nextLine();

        }
        System.out.println("PLEASE ENTER AGE: ");
        int age = scan.nextInt();
        while (age < 18)
        {
            System.out.println("YOU MUST BE 18 OR OLDER TO CREATE AN ACCOUNT, PLEASE TRY AGAIN");
            System.out.println("PLEASE ENTER AGE: ");
            age = scan.nextInt();
        }
        System.out.println("APPROVED! CREATING ACCOUNT...");
        System.out.println();
        System.out.println("HERE IS YOUR TEMPORARY DEBIT CARD");
        System.out.println("-----------------------------------");
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*V*");
        System.out.println("----------------------------------------------");
        System.out.println("*|" + "AMERICAN EXPRESS                      |*");
        System.out.println(">| _-------_                                 |<");
        System.out.println("*| I--I I--I                        }}}      |*");
        System.out.println(">| I__I_I__I                     }}}         |<");
        System.out.println("*|                              }}}   7997   |*");
        System.out.println(">|                               }}}         |<");
        System.out.println("*|                                  }}}      |*");
        System.out.println(">|" + "|" + accountHolder + "|               |<");
        System.out.println("*_____________________________________________");
        System.out.println("^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*");

    }

    private static double remainingDue = 0;

     static void creditCardPayment(double balance)
    {


        if (remainingDue <= 0) {
            Random randAmount = new Random();
            long min = 10000L;
            long max = 250000L;
            remainingDue = min + ((randAmount.nextDouble() * (max - min + 1)));
            System.out.printf("NEW CREDIT CARD BILL GENERATED: $%.2f%n", remainingDue);
        }

        // Show current due
        System.out.printf("YOUR CURRENT AMOUNT DUE IS: $%.2f%n", remainingDue);
        System.out.print("PAY NOW? (Y/N): ");
        char response = scan.next().toUpperCase().charAt(0);

        if (response == 'Y') {
            if (balance >= remainingDue) {
                // Full payment
                balance -= remainingDue;
                System.out.printf("PAYMENT SUCCESSFUL! Remaining Balance: $%.2f%n", balance);
                remainingDue = 0; // debt cleared
            } else {
                // Partial payment
                System.out.printf("INSUFFICIENT FUNDS. YOU HAVE $%.2f%n", balance);
                System.out.print("WOULD YOU LIKE TO PAY PARTIAL? (Y/N): ");
                char partial = scan.next().toUpperCase().charAt(0);

                if (partial == 'Y') {
                    remainingDue -= balance;
                    System.out.printf("PARTIAL PAYMENT MADE. STILL DUE: $%.2f%n", remainingDue);
                    balance = 0; // drained account
                } else {
                    System.out.println("PAYMENT CANCELLED, RETURNING TO MENU...");
                }
            }
        } else {
            System.out.printf("PAYMENT SKIPPED. STILL DUE: $%.2f%n", remainingDue);
        }
    }
    

    


}
