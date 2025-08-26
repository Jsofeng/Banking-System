
import java.util.Scanner;

public class Banking {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        double balance = 0;
        boolean isOpen = true;
        int choice;

        while (isOpen)
        {
            System.out.println("*****************");
            System.out.println("AMERICAN EXPRESS");
            System.out.println("1: CHECK BALANCE");
            System.out.println("2: DEPOSIT");
            System.out.println("3: WITHDRAW");
            System.out.println("4: REQUEST LOAN");
            System.out.println("5. REQUEST CREDIT CARD");
            System.out.println("6: EXIT");
            System.out.println("******************");
            System.out.println("CHOOSE A NUMBER 1 - 6");

            choice = scan.nextInt();

            switch (choice)
            { // CASES ALWAYS RUNS FIRST AND THEN METHODS ARE CALLED
                case 1:
                {
                    showBalance(balance);
                }
                break;
                case 2:
                {
                    balance += deposit();
                    System.out.printf("NEW BALANCE: $%.2f\n", balance);
                    System.out.println("DEPOSIT AGAIN? (Y/N)");
                    char response = scan.next().toUpperCase().charAt(0);
                    while (response == 'Y')
                    {
                        balance += deposit();
                        System.out.printf("DEPOSITED: $%.2f\n", balance);
                        System.out.printf("NEW BALANCE: $%.2f\n", balance);
                        System.out.println("DEPOSIT AGAIN? (Y/N)");
                        response = scan.next().toUpperCase().charAt(0);

                    }
                    if (response == 'N')
                    {
                        System.out.println("WOULD YOU LIKE A RECEIPT? (Y/N)");
                        response = scan.next().toUpperCase().charAt(0);

                        if (response == 'Y')
                        {
                            System.out.printf("PRINTING RECEIPT... NEW BALANCE: $%.2f\n", balance);

                        }
                        else if (response == 'N')
                        {
                            System.out.println("HAVE A NICE DAY");
                        }

                    }
                    break;
                }


                case 3:
                {
                    balance -= withDraw(balance);
                    System.out.printf("NEW BALANCE: $%.2f\n", balance);
                    System.out.println("WITHDRAW AGAIN? (Y/N)");
                    char response = scan.next().toUpperCase().charAt(0);

                    while (response == 'Y')
                    {

                        balance -= withDraw(balance);
                        System.out.printf("DEPOSITED: $" + "%.2f\n", balance);
                        System.out.printf("NEW BALANCE: $%.2f\n", balance);
                        System.out.println("DEPOSIT AGAIN? (Y/N)");
                        response = scan.next().toUpperCase().charAt(0);
                    }

                    if (response == 'N')
                    {

                        System.out.println("THANK YOU FOR BANKING WITH AMEX!");
                        System.out.println("WOULD YOU LIKE A RECEIPT? (Y/N)");

                        response = scan.next().toUpperCase().charAt(0);
                        if (response == 'Y')
                        {
                            System.out.printf("PRINTING RECEIPT... NEW BALANCE: $%.2f\n", balance);

                        }
                        else if (response == 'N')
                        {
                            System.out.println("HAVE A NICE DAY!");

                        }

                    }
                    break;
                }
                case 4: {
                    loan(balance);
                    break;
                }
                case 5:
                { //this line of code runs first
                    String name;
                    int age;
                    String ssn;
                    System.out.println("REQUEST CREDIT CARD");
                    System.out.println("PLEASE ENTER YOUR NAME: ");
                    scan.nextLine();
                    name = scan.nextLine();

                    while (!name.matches("[a-zA-Z ]+"))
                    {
                        System.out.println("LETTERS ONLY, PLEASE TRY AGAIN");
                        System.out.println("PLEASE ENTER YOUR NAME: ");
                        name = scan.nextLine();
                    }

                    System.out.println("PLEASE ENTER AGE: ");
                    age = scan.nextInt();

                    if (age < 18)
                    {
                        System.out.println("YOU MUST BE 18 OR OLDER TO REQUEST A CREDIT CARD, PLEASE TRY AGAIN");
                        System.out.println("PLEASE ENTER AGE: ");
                        age = scan.nextInt();
                    }
                    System.out.println("PLEASE ENTER SSN (9 DIGIT): ");
                    scan.nextLine();
                    ssn = scan.nextLine();
                    while (!ssn.matches("\\d{9}"))
                    {
                        System.out.println("INVALID SSN, MUST BE 9 DIGITS, PLEASE TRY AGAIN");
                        ssn = scan.nextLine();

                    }
                    requestCreditCard(name, age, ssn); //once it gets here the method is called
                    System.out.println("IS THAT ALL FOR TODAY? (Y/N)");
                    char response = scan.next().toUpperCase().charAt(0);
                    if (response == 'Y') {
                        System.out.println("THANK YOU FOR BANKING WITH AMEX");
                        isOpen = false;
                    } else if (response == 'N') {
                        System.out.println("REDIRECTING TO MAIN MENU...");
                    }
                    break;
                }


                case 6:
                {
                    System.out.println("THANK YOU FOR BANKING WITH AMEX");
                    isOpen = false;
                }



                default:
                    System.out.println("INVALID NUMBER, CHOOSE BETWEEN 1 - 5");
                    continue;


            }

        }
        scan.close();
    }
    static void showBalance(double balance) {
        System.out.printf("BALANCE: $%.2f\n", balance);
    }

    static double deposit() {
        System.out.println("ENTER AMOUNT TO DEPOSIT: $");
        double amount = scan.nextDouble();
        if (amount < 0) {
            System.out.println("INVALID AMOUNT, TRY AGAIN");
            return deposit();
        } else {
            return amount;
        }
    }

    static double withDraw(double balance) {
        System.out.println("ENTER AMOUNT TO WITHDRAW: $");
        double amount = scan.nextDouble();
        if (amount > balance) {
            System.out.println("INSUFFICIENT FUNDS, TRY AGAIN");
            return withDraw(balance);
        } else if (amount < 0) {
            System.out.println("INVALID AMOUNT, TRY AGAIN");
            return withDraw(balance);
        } else {
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

                while(loanAmount >= balance) {
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


