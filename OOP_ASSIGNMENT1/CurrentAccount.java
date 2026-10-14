public class CurrentAccount extends Account {
    private final double overdraftLimit; // positive number, e.g. 500 means balance may go down to -500
    private final double monthlyFee;

    public CurrentAccount(String accountNumber, double initialBalance,
                          double overdraftLimit, double monthlyFee) {
        super(accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[" + accountNumber + "] Withdrawal rejected: amount must be positive.");
            return;
        }
        if (balance - amount < -overdraftLimit) {
            System.out.printf("[%s] Withdrawal of %.2f REJECTED: exceeds overdraft limit of %.2f (current balance %.2f).%n",
                    accountNumber, amount, overdraftLimit, balance);
            return;
        }
        balance -= amount;
        if (balance < 0) {
            System.out.printf("[%s] Withdrew %.2f. Account is now in OVERDRAFT: balance %.2f (limit -%.2f).%n",
                    accountNumber, amount, balance, overdraftLimit);
        } else {
            System.out.printf("[%s] Withdrew %.2f. New balance: %.2f%n", accountNumber, amount, balance);
        }
    }

    @Override
    public void endOfMonth() {
        balance -= monthlyFee;
        System.out.printf("[%s] Month-end: maintenance fee of %.2f deducted. New balance: %.2f%n",
                accountNumber, monthlyFee, balance);
    }
}
