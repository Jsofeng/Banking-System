
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
            System.out.println("5: PAY OFF LOAN");
            System.out.println("6: REQUEST CREDIT CARD");
            System.out.println("7: CREDIT CARD PAYMENT");
            System.out.println("8: CREATE ACCOUNT");
            System.out.println("9: EXIT");
            System.out.println("******************");
            System.out.println("CHOOSE A NUMBER 1 - 6");

            choice = scan.nextInt();

            switch (choice)
            { // CASES ALWAYS RUNS FIRST AND THEN METHODS ARE CALLED
                case 1:
                {
                    BankingMethods.showBalance(balance);
                }
                break;
                case 2:
                {
                    balance += BankingMethods.deposit();
                    System.out.printf("NEW BALANCE: $%.2f\n", balance);
                    System.out.println();
                    System.out.print("DEPOSIT AGAIN? (Y/N)");
                    char response = scan.next().toUpperCase().charAt(0);
                    while (response == 'Y')
                    {
                        balance += BankingMethods.deposit();
                        System.out.printf("DEPOSITED: $%.2f\n", balance);
                        System.out.printf("NEW BALANCE: $%.2f\n", balance);
                        System.out.println();
                        System.out.print("DEPOSIT AGAIN? (Y/N)");
                        response = scan.next().toUpperCase().charAt(0);

                    }
                    if (response == 'N')
                    {
                        System.out.println();
                        System.out.print("WOULD YOU LIKE A RECEIPT? (Y/N)");
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
                    balance -= BankingMethods.withDraw(balance);
                    System.out.printf("NEW BALANCE: $%.2f\n", balance);
                    System.out.println();
                    System.out.print("WITHDRAW AGAIN? (Y/N)");
                    char response = scan.next().toUpperCase().charAt(0);

                    while (response == 'Y')
                    {

                        balance -= BankingMethods.withDraw(balance);
                        System.out.printf("DEPOSITED: $" + "%.2f\n", balance);
                        System.out.printf("NEW BALANCE: $%.2f\n", balance);
                        System.out.println();
                        System.out.print("DEPOSIT AGAIN? (Y/N)");
                        response = scan.next().toUpperCase().charAt(0);

                    }

                    if (response == 'N')
                    {

                        System.out.println("THANK YOU FOR BANKING WITH AMEX!");
                        System.out.println();
                        System.out.print("WOULD YOU LIKE A RECEIPT? (Y/N)");

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
                    BankingMethods.loan(balance); // balance is already a variable assigned at the top
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
                    BankingMethods.requestCreditCard(name, age, ssn); //once it gets here the method is called
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

                case 6: {
                    BankingMethods.payOffLoan(balance);
                    break;
                }

                case 7: {
                    BankingMethods.creditCardPayment(balance);
                    break;
                }

                case 8: {
                    String accountNumber = ""; // aren't assigned yet even though the method assigns then
                    String accountHolder = ""; // same here
                    BankingMethods.createAccount(accountNumber, accountHolder);
                    break;
                }
                case 9:
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

    }

