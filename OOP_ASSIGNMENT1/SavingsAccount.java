public class SavingsAccount extends Account {
    private final double minimumBalance;
    private final double interestRate; // e.g. 0.02 = 2% per month

    public SavingsAccount(String accountNumber, double initialBalance,
                          double minimumBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Withdrawal rejected: amount must be positive.");
            return;
        }
        if (balance - amount < minimumBalance) {
            System.out.printf("[%s] Withdrawal of %.2f REJECTED: balance would fall below the minimum of %.2f (current balance %.2f).%n",
                    accountNumber, amount, minimumBalance, balance);
            return;
        }
        balance -= amount;
        System.out.printf("[%s] Withdrew %.2f. New balance: %.2f%n", accountNumber, amount, balance);
    }

    @Override
    public void endOfMonth() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.printf("[%s] Month-end: interest of %.2f (%.1f%%) applied. New balance: %.2f%n",
                accountNumber, interest, interestRate * 100, balance);
    }
}
