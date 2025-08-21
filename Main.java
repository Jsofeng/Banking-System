import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        double balance = 0;
        boolean isOpen = true;
        int choice;

        while (isOpen) {
            System.out.println("*****************");
            System.out.println("AMERICAN EXPRESS");
            System.out.println("1: CHECK BALANCE");
            System.out.println("2: DEPOSIT");
            System.out.println("3: WITHDRAW");
            System.out.println("4: EXIT");
            System.out.println("******************");
            System.out.println("CHOOSE A NUMBER 1 - 4");

            choice = scan.nextInt();

            switch (choice) {
                case 1:  {
                    showBalance(balance);
                }
                break;
                case 2: {
                    balance += deposit();
                    System.out.printf("NEW BALANCE: $%.2f\n", balance);
                }
                break;

                case 3: {
                    balance -= withDraw(balance);
                    System.out.printf("NEW BALANCE: $%.2f\n", balance);
                }
                break;

                case 4: {
                    System.out.println("THANK YOU FOR BANKING WITH AMEX");
                    isOpen = false;
                } 
                
                default: System.out.println("INVALID NUMBER, CHOOSE BETWEEN 1 - 4");
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
}
