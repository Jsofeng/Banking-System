public class TestCases
{
    public static void main(String[] args)
    {
        
        BankingMethods account = new BankingMethods("", "");
        double johnBalance = 0;
        account.setAccountInfo("123456789101112", "John Doe");
        double johndeposit = BankingMethods.deposit();
        account.balance += johndeposit;
        System.out.println(account.accountDetails());
        account.recordTransaction("Deposited: $" + johndeposit);

        BankingMethods newAccount = new BankingMethods("987654321000000", "Jane Smith");
        double janeBalance = 0;
        newAccount.setAccountInfo("987654321000000", "Jane Smith");
        double janedeposit = BankingMethods.deposit();
        newAccount.balance += janedeposit;
        System.out.println(newAccount.accountDetails());
        account.recordTransaction("Deposited: $" + janedeposit);
        account.transferFunds(newAccount, janedeposit);
        account.recordTransaction("Transferred: $" + janedeposit + " to account " + newAccount.accountNumber);


    }
}
