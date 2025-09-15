class SavingsAccount extends DataBase {
    SavingsAccount(String accountNumber, String accountHolder) {
        super(accountNumber, accountHolder);
    }

    @Override
    void deposit(double amount) {
        balance += amount;
        System.out.printf("[%s] Deposited $%.2f into Savings Account%n", accountHolder, amount);
    }

    @Override
    void withdraw(double amount) {
        balance -= amount;
        System.out.printf("[%s] Withdrew $%.2f from Savings Account%n", accountHolder, amount);
    }
}



