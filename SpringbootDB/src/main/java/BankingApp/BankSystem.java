package com.example.springbootdb.BankingApp;

import FileManager.FileManager;
import org.springframework.cglib.core.WeakCacheKey;

import java.sql.SQLOutput;
import java.util.Scanner;

public class BankingSystem {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isOpen = true;
        int choice;

        // Store accounts
        ChequingAccount currentAccount = null;

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
                        double balance = currentAccount.getBalance();
                	 System.out.println("[" + currentAccount.accountHolder + "] Balance: $" + String.format("%.2f", balance))
		} else {
                        System.out.println("NO ACCOUNT FOUND. CREATE ONE FIRST!");
                    }
                    break;
                }
                case 2: {
                    if (currentAccount != null) {
                        System.out.print("Enter amount to deposit: $");
                        double amount = scan.nextDouble();
                        currentAccount.deposit(amount);
                        FileManager.logDeposit(currentAccount, amount);
                    } else {
                        System.out.println("NO ACCOUNT FOUND. CREATE ONE FIRST!");
                    }
                    break;
                }

                case 3: {
                    if (currentAccount != null) {
                        System.out.print("Enter Amount To Withdraw: $");
                        double amount = scan.nextDouble();
                        currentAccount.withdraw(amount);
                        FileManager.logWithdraw(currentAccount, amount);
                    } else {
                        System.out.println("NO ACCOUNT FOUND. CREATE ONE FIRST!");
                    }
                    break;
                }

		case 4: {
                     if (currentAccount == null) {
                        System.out.println("NO ACCOUNT FOUND");
                        break;
                    }
                    if(!currentAccount.hasLoan()) {

                            double loaned = currentAccount.loan(currentAccount.getBalance());
                            FileManager.logLoans(currentAccount, loaned);
                            currentAccount.setHasLoan(true);

                    } else {
                        System.out.println("YOU CANNOT TAKE OUT ANOTHER LOAN");
                    }
                    break;
		}

		case 5: {
                    if (currentAccount == null) {
                        System.out.println("NO ACCOUNT FOUND");
                        break;
                    }

                    System.out.println("Enter Account # To Transfer To: ");
                    String accountNumber = scan.next();
                    System.out.println("Enter Account Holder Name: ");
                    String accountHolderName = scan.next();
                    System.out.println("Enter Amount To Transfer To: ");
                    double amount  = scan.nextDouble();
                    AccountType toAccount = new ChequingAccount(accountNumber, accountHolderName, amount);

                    currentAccount.eTransfer(currentAccount, toAccount, amount);
                    break;
                }

//                case 7: {
//                    System.out.println("AMOUNT SPEND: $");
//                    double amount = scan.nextDouble();
//                    System.out.println("[%s]'s Credit Card has been charged [%.2lf]", currentAccount, purchase(amount));
//                    // add new AccountFileManager for creditCardPayment
//                }

                case 8: {
                    System.out.println("CREATE ACCOUNT");
                    System.out.print("Enter Account Number: ");
                    scan.nextLine();
                    String accNum = scan.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String accHolder = scan.nextLine();

                    currentAccount = new ChequingAccount(accNum, accHolder, 10000);
                    System.out.println("ACCOUNT CREATED: " + currentAccount);
                    currentAccount.deposit(10000);
                    System.out.println("$10k sign up bonus\n");
                    FileManager.saveAccountType("AccountsDB.txt", currentAccount);
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
