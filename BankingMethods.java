import java.util.Scanner;
class BankingMethods
{
    static Scanner scan = new Scanner(System.in);


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
        System.out.println("PLEASE CHECK IF INFORMATION IS CORRECT " + "[ NAME: " + name + ", AGE: " + age + ", SSN: " + ssn + " ]");
        System.out.println("IF CORRECT, ENTER Y, IF NOT, ENTER N");
        char response = scan.next().toUpperCase().charAt(0);
        if (response == 'Y')
        {
            System.out.println("APPROVED! CREDIT CARD WILL BE SENT WITHIN 1-5 BUSINESS DAYS");
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
}
