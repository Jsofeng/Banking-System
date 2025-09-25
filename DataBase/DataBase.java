public abstract class DataBase {
    String accountNumber;
    String accountHolder;
    double balance;

    DataBase(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0;
    }

    abstract void deposit(double amount);
    abstract void withdraw(double amount);
    abstract void eTransfer(DataBase toAccount, double transferAmount);

    void showBalance() {
        System.out.printf("[%s] BALANCE: $%.2f%n", accountHolder, balance);
    }

    /**
     * @return a string representation of the account details
     * Any class that extends DataBase will inherit this method
     */
    @Override
    public String toString() {
        return "AccountNumber: " + accountNumber +
                ", AccountHolder: " + accountHolder +
                ", Balance: $" + String.format("%.2f", balance);
    }

}

