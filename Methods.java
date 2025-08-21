public class Main {
  
  public static void main(String[] args) {
  }

  static void showBalance(double balance) {
    System.out.println("**************");
    System.out.println("%.2f\n", balance);
  }

  static double deposit() {
    System.out.println("ENTER AMOUNT TO DEPOSIT");
    double amount = scan.nextDouble();
    if(amount < 0) {
      System.out.println("Please try again");
      return deposit();
      
    } else {
      return amount;
  }

  static double withDraw(double balance) {
    System.out.println("***************");
    double amount = scan.nextDouble();
    if(amount > balance) {
      System.out.println("INSUFFICIENT FUNDS");
      return withDraw(balance);
    
    } else if(amount < 0) {
      System.out.println("PLEASE TRY AGAIN");
      return withDraw(balance);
    
    } else {
      System.out.println("BALANCE AFTER WITHDRAW: $" + (balance - amount));
      return amount;
    } 
  }

  
  
}


