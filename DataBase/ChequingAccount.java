class ChequingAccount extends DataBase {
    ChequingAccount(String accountNumber, String accountHolder) {
        super(accountNumber, accountHolder);
    }

    /**
     * @param amount the amount to deposit
     * balance += amount adds the amount to the balance
     */
    @Override
    void deposit(double amount) {
        balance += amount;
        System.out.printf("[%s] Chequing Deposit: $%.2f%n", accountHolder, amount);
    }

    /**
     * @param amount the amount to withdraw
     * balance -= amount subtracts the amount from the balance
     */
    @Override
    void withdraw(double amount) {
        balance -= amount;
        System.out.printf("[%s] Chequing Withdrawal: $%.2f%n", accountHolder, amount);
    }

    /**
     *
     * @param toAccount the account I am transferring money to
     * @param transferAmount the amount I am transferring
     * balance -= transferAmount subtracts the balance from the account that is transferring (e.g) acc1C.(acc2S,1000) acc1C's balance is deducted by transferAmount and then toAccount receives that balance.
     */
    
    @Override
    void eTransfer(DataBase toAccount, double transferAmount) {
        balance -= transferAmount;
        toAccount.balance += transferAmount;
        System.out.printf("[%s] transferred $%.2f to [%s]%n", this.accountNumber, transferAmount, toAccount.accountNumber);
        System.out.printf("[%s] NEW BALANCE: $%.2f%n[%s] NEW BALANCE $%.2f%n", this.accountNumber, balance, toAccount.accountNumber, toAccount.balance);
        
        AccountFileManager.logTransaction(this, toAccount, transferAmount);
    }
}

