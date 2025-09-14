abstract class DataBase {
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

    void showBalance() {
        System.out.printf("[%s] BALANCE: $%.2f%n", accountHolder, balance);
    }
}

class ChequingAccount extends DataBase {
    ChequingAccount(String accountNumber, String accountHolder) {
        super(accountNumber, accountHolder);
    }

    @Override
    void deposit(double amount) {
        balance += amount;
        System.out.printf("[%s] Chequing Deposit: $%.2f%n", accountHolder, amount);
    }

    @Override
    void withdraw(double amount) {
        balance -= amount;
        System.out.printf("[%s] Chequing Withdrawal: $%.2f%n", accountHolder, amount);
    }
}
