package piggyBank.services;

public class InvalidAmountException extends Exception {

    private String amount;

    public InvalidAmountException(String amount) {
        super("Invalid amount: " + amount);
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

}
