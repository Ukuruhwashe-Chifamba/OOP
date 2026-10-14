import java.util.ArrayList;
import java.util.List;

public class BankDemo {
    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("SAV-001", 1000.00, 200.00, 0.02));
        accounts.add(new CurrentAccount("CUR-001", 300.00, 500.00, 15.00));
        accounts.add(new SavingsAccount("SAV-002", 5000.00, 1000.00, 0.015));
        accounts.add(new CurrentAccount("CUR-002", 100.00, 250.00, 10.00));

        // Deposit validation demo
        System.out.println("=== Deposit validation ===");
        accounts.get(0).deposit(-50);
        accounts.get(0).deposit(0);

        // Polymorphic withdrawals: only the Account reference is used, no casting
        System.out.println("\n=== Withdrawals (polymorphic) ===");
        double[] withdrawals = {500.00, 700.00, 800.00, 1000.00};
        // SAV-001: 500 ok (1000 -> 500); CUR-001: 700 -> overdraft (-400, within 500 limit)
        // SAV-002: 800 ok (5000 -> 4200); CUR-002: 1000 -> rejected (would be -900, limit 250)
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            a.withdraw(withdrawals[i]);
        }

        // Edge case 1: Savings withdrawal rejected (would breach minimum balance)
        System.out.println("\n=== Edge case 1: Savings withdrawal rejected ===");
        Account savings = accounts.get(0);               // balance is now 500, minimum 200
        savings.withdraw(400.00);                        // 500 - 400 = 100 < 200 -> rejected

        // Edge case 2: Current account overdraft within limit
        System.out.println("\n=== Edge case 2: Current account overdraft within limit ===");
        Account current = accounts.get(3);               // balance 100, limit 250
        current.withdraw(300.00);                        // 100 - 300 = -200, within -250 -> allowed

        // Polymorphic month-end processing
        System.out.println("\n=== End of month (polymorphic) ===");
        for (Account a : accounts) {
            a.endOfMonth();
        }

        System.out.println("\n=== Final balances ===");
        for (Account a : accounts) {
            System.out.printf("%s: %.2f%n", a.getAccountNumber(), a.getBalance());
        }
    }
}
