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

    @Override
    void eTransfer(DataBase toAccount, double transferAmount) {
        balance -= transferAmount;
        toAccount.balance += transferAmount;
        System.out.printf("[%s] transferred $%.2f to [%s]%n", this.accountNumber, transferAmount, toAccount.accountNumber);
        System.out.printf("[%s] NEW BALANCE: $%.2f%n[%s] NEW BALANCE $%.2f%n", this.accountNumber, balance, toAccount.accountNumber, toAccount.balance);
    }
}



