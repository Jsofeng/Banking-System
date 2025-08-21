public class Main {
  public static void main(String[] args) {
    static Scanner scan = new Scanner(System.in);
    double balance;
    boolean isOpen = true;
    int choice;
    
    System.out.println("*****************");
    System.out.println("AMERICAN EXPRESS");
    System.out.println("1: CHECK BALANCE");
    System.out.println("2: DEPOSIT");
    System.out.println("3: WITHDRAW");
    System.out.println("4: EXIT"):
    System.out.println("******************");
    System.out.println("CHOOSE A NUMBER 1 - 4");
    
    choice = scan.nextInt();

    while(isOpen) {
      switch(choice) {
        case 1 -> 
        case 2 ->
        case 3 ->
        case 4 ->
        default -> System.out.println("INVALID NUMBER CHOOSE BETWEEN 1 - 4");
      }
    }
    scan.close();
  }
}

